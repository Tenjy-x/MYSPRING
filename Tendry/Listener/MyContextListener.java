package Tendry.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Tendry.Utils.*;
import Tendry.Annotation.*;
import Tendry.Exception.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        ServletContextListener.super.contextDestroyed(sce);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        ServletContextListener.super.contextInitialized(sce);
        ServletContext context = sce.getServletContext();
        String packageName = context.getInitParameter("PackageName");
        // System.out.println("--------------------------------");
        // System.out.println(packageName);
        // System.out.println("--------------------------------");
        try {
            try {
                List<Class<?>> classes = Utils.chargerClasses(packageName);
                Map<UrlMethod, Mapping> urlMapping = new HashMap<>();
                List<Class<?>> listes = Utils.getAnnotationClasses(classes, AnnotationController.class);
                context.setAttribute("Controller", listes);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } catch (Exception e) {
            System.out.println("--------------------------------");
            System.out.println(e.getMessage());
            System.out.println("--------------------------------");
            throw new RuntimeException(e);
        }
    }

}
