package Tendry;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.util.*;

import Tendry.Annotation.AnnotationController;
import Tendry.Utils.*;


public class FrontControllerServlet extends HttpServlet{
    List<String> Controller;
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
    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException , IOException {
        String URL = req.getRequestURI();

        try (PrintWriter out = res.getWriter()) {      
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet WebController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + URL + "</h1>");
            out.println("<div class = 'List'> ");
            for(String controller : Controller) {
                out.println("<li>" + controller + "</li>");
                out.println("<br>");
            }
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
