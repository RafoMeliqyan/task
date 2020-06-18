package servlet;

import manager.Manager;
import manager.TaskManager;
import manager.UserManager;
import model.ManagerModel;
import model.Task;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    UserManager userManager = new UserManager();
    Manager manager = new Manager();
    TaskManager taskManager = new TaskManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");


        StringBuilder msg = new StringBuilder();
        if (email == null || email.length() == 0) {
            msg.append("email field is required<br>");
        }
        if (password == null || password.length() == 0) {
            msg.append("password field is required<br>");
        }

        if (msg.toString().equals("")) {
            User user = userManager.getByEmailAndPassword(email, password);
            ManagerModel managerModel = manager.getByEmailAndPassword(email, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                if (user.getUserType() == UserType.USER) {
                    resp.sendRedirect("/userHome");
                } else {
                    msg.append("User does not exist");
                    req.getSession().setAttribute("msg", msg.toString());
                    resp.sendRedirect("/");
                }
            }

            if (managerModel != null) {
                HttpSession session = req.getSession();
                session.setAttribute("manager", managerModel);
                if (managerModel.getUserType() == UserType.MANAGER) {
                    req.setAttribute("manager", managerModel);
                    resp.sendRedirect("/managerHome");
                } else {
                    msg.append("Manager does not exist");
                    req.getSession().setAttribute("msg", msg.toString());
                    resp.sendRedirect("/");
                }
            }
        } else {
            req.getSession().setAttribute("msg", msg.toString());
            resp.sendRedirect("/");
        }
    }

}
