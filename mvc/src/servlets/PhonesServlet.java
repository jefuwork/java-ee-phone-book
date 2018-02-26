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

@WebServlet("/phones")
public class PhonesServlet extends HttpServlet {
	
    @EJB
    private UserEJBBean userBean;
    
    private static final long serialVersionUID = 1L;
    
    public PhonesServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.isUserInRole("admin_role")) {
            List<User> usernames = userBean.getAllUsers();
            request.setAttribute("usernames", usernames);
        }
        getServletContext().getRequestDispatcher("/Views/phones.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
