package Tendry;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
public class FrontControllerServlet extends HttpServlet{
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
            out.println("</body>");
            out.println("</html>");
        }
    }
}
