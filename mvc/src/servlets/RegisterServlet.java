package servlets;

import java.io.IOException;
import java.net.URLEncoder;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import localEJBs.UserEJBBean;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    @EJB UserEJBBean userBean;
    
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/Views/auth/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        String email = request.getParameter("email");
        
        System.out.println("name: " + name);
        System.out.println("pass: " + pass);
        System.out.println("email: " + email);
        
        User user = new User(name, pass, email);
        
        userBean = new UserEJBBean();
        if (userBean.findUserByEmail(email) == null) {
            User userTestCreation = userBean.createUser(user);
            if (userTestCreation != null) {
                System.out.println("User was created successfully!");
            } else {
                System.out.println("User wasn't created due to bad given params.");
            }
            
            // Guess it's not safe
            String url = request.getContextPath() + "/j_security_check";
            response.sendRedirect(url + "?j_username="
                    + URLEncoder.encode(email, "UTF-8")
                    + "&j_password="
                    + URLEncoder.encode(pass, "UTF-8"));
        } else {
            System.out.println("email was already taken.");
            response.addCookie(new Cookie("emailTaken", email));
            doGet(request, response);
        }
    }

}
