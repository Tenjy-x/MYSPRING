package Tendry;
import java.io.*;
import java.rmi.server.ServerCloneException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;

import Tendry.Annotation.AnnotationController;
import Tendry.Annotation.UrlMapping;
import Tendry.Utils.*;
import Tendry.Exception.*;

public class FrontControllerServlet extends HttpServlet{
    List<Class<?>> Controller;
    Map<String , Mapping> mps;
    public void init() throws ServletException {
        String packageName = this.getInitParameter("PackageName");
        try {
            List<Class<?>> classes = Utils.chargerClasses(packageName);
            Controller = Utils.getAnnotationClasses(classes ,AnnotationController.class);
            
        }catch(Exception e){
            System.err.println("Erreur lors du chargement : " + e.getMessage());
            e.printStackTrace();
        } 

    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException {
        processRequest(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException {
        processRequest(req, res);
    }
    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException  {
        String URL = req.getRequestURI();
        URL = req.getPathInfo();
        PrintWriter out = res.getWriter();      
            try  {
                Map<UrlMethod , Mapping> m2 = Utils.UrlMapping(URL  , Controller , UrlMapping.class);
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet WebController</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>" + URL + "</h1>");
                out.println("<div class = 'List'> ");
                for(Class controller : Controller) {
                    out.println("<li>" + controller.getSimpleName() + "</li>");
                    out.println("<br>");
                }
                out.println("</div>");
                out.println("<div class = 'Method'>");
                for(Map.Entry<UrlMethod , Mapping> entry : m2.entrySet() ) {
                    out.println("method :" + entry.getKey().getMethod()+ " " + entry.getKey().getUrl()+ " : " + entry.getValue().getController().getSimpleName() + "->" + entry.getValue().getMethod().getName());
                }
                out.println("<br>");
                out.println("<h2>Invocation</h2>");

                UrlMethod urlMethod  = new UrlMethod();
                urlMethod.setUrl(URL);
                urlMethod.setMethod(req.getMethod());
                Mapping map = m2.get(urlMethod);
                try {
                    Object o = Utils.invokeFunction(map);
                    out.print(o);
                }catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
            catch (URLException e){
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet WebController</title>");
                out.println("</head>");
                out.println("<body>");
                out.println(e.getMessage());
                out.println("</body>");
                out.println("</html>");
            }
            out.close();
    }
}
