package servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import localEJBs.UserEJBBean;

@WebServlet("/users")
public class AllUsersServlet extends HttpServlet {
    
    @EJB
    private UserEJBBean userBean;
    
    private static final long serialVersionUID = 1L;

    public AllUsersServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> usernames = userBean.getAllUsers(); //Arrays.asList(new User("1", "2", "3"), new User("3", "4", "5"));
        request.setAttribute("usernames", usernames);
        getServletContext().getRequestDispatcher("/Views/auth/listOfUsers.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("del") != null) {
            String email = request.getParameter("del");
            System.out.println(email);
            userBean = new UserEJBBean();
            if (userBean.deleteUser(email) == true) {
                System.out.println("User with email: " + email + " was deleted.");
            } else {
                System.out.println("User with email: " + email + " wasn't deleted.");
            }
        }
        doGet(request, response);
    }

}
