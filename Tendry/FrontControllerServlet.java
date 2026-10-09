package Tendry;
import java.io.*;
import java.rmi.ServerException;
import java.rmi.server.ServerCloneException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;

import com.fasterxml.jackson.databind.util.JSONPObject;

import Tendry.Annotation.AnnotationController;
import Tendry.Annotation.UrlMapping;
import Tendry.Annotation.Webapi;
import Tendry.Utils.*;
import Tendry.Exception.*;
import Tendry.Web.*;
import jakarta.servlet.*;
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
        List<Class<?>> Controller = (List<Class<?>>) this.getServletContext().getAttribute("Controller");
        String suffixe = getServletContext().getInitParameter("Suffixe");
        String prefixe = getServletContext().getInitParameter("Prefixe");
        String URL = req.getPathInfo();
        if(URL == null || URL.isEmpty()){
            URL = req.getServletPath();
        }
        if(URL == null || URL.isEmpty()){
            URL = "/";
        }
        if(URL.startsWith("/WEB-INF")){
            req.getServletContext().getNamedDispatcher("jsp").forward(req, res);
            return;
        }
        // PrintWriter out = res.getWriter();      
            try  {
                Map<UrlMethod , Mapping> m2 = new HashMap<>();
                Utils.UrlMapping(URL  , Controller , UrlMapping.class , m2);
                // out.println("<!DOCTYPE html>");
                // out.println("<html>");
                // out.println("<head>");
                // out.println("<title>Servlet WebController</title>");
                // out.println("</head>");
                // out.println("<body>");
                // out.println("<h1>" + URL + "</h1>");
                // out.println("<div class = 'List'> ");
                // for(Class controller : Controller) {
                //     out.println("<li>" + controller.getSimpleName() + "</li>");
                //     out.println("<br>");
                // }
                // out.println("</div>");
                // out.println("<div class = 'Method'>");
                // for(Map.Entry<UrlMethod , Mapping> entry : m2.entrySet() ) {
                //     out.println("method :" + entry.getKey().getMethod()+ " " + entry.getKey().getUrl()+ " : " + entry.getValue().getController().getSimpleName() + "->" + entry.getValue().getMethod().getName());
                // }
                // out.println("<br>");
                // out.println("<h2>Invocation</h2>");

                UrlMethod urlMethod  = new UrlMethod();
                urlMethod.setUrl(URL);
                urlMethod.setMethod(req.getMethod());
                Mapping map = m2.get(urlMethod);
                if(map == null){
                    res.sendError(HttpServletResponse.SC_NOT_FOUND , "Aucune route pour " + URL + " (" + req.getMethod() + ")");
                    return;
                }
                try {
                    Object o = Utils.invokeFunction(map, req);
                    if(o instanceof ModelAndView) {
                        System.out.println("Je suis dans le ModelAndView");
                        ModelAndView modelAndView = (ModelAndView) o;
                        String path = prefixe + modelAndView.getUrl() + suffixe;


                        RequestDispatcher dispat = req.getRequestDispatcher(path);
                        for(Map.Entry<String,Object> entry : modelAndView.getObject().entrySet()) {
                            req.setAttribute(entry.getKey() , entry.getValue());
                        }
                        if (dispat == null) {
                            System.out.println("Dispatcher NULL");
                        } else {
                            dispat.forward(req, res);
                            return;
                        }
                    }
                    try(PrintWriter out = res.getWriter()) {
                       if(map.getMethod().isAnnotationPresent(Webapi.class)) {
                            res.setHeader("Content-Type", "application/json");
                            String json = Utils.toJson(o);
                            out.print(json);
                            return;
                       }
                       out.println(o);
                    }catch(Exception e) {
                        System.out.println(e.getMessage());
                        throw new ServletException(e);
                    }
                    
                }catch(Exception e) {
                    PrintWriter out = res.getWriter();
                    out.println(e.getMessage());
                    throw new ServletException(e);
                }
                // out.println("</div>");
                // out.println("</body>");
                // out.println("</html>");
            }
            catch (URLException e){
                // out.println("<!DOCTYPE html>");
                // out.println("<html>");
                // out.println("<head>");
                // out.println("<title>Servlet WebController</title>");
                // out.println("</head>");
                // out.println("<body>");
                // out.println(e.getMessage());
                // out.println("</body>");
                // out.println("</html>");
                throw new ServletException(e);
            }
            // out.close();
    }
}
