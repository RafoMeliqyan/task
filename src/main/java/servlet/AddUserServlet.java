package servlet;

import manager.UserManager;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/addUser")
public class AddUserServlet extends HttpServlet {

    UserManager userManager = new UserManager();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = null;
        try {
            user = User.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password)
                    .userType(UserType.USER)
                    .build();
            userManager.register(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/managerHome");
    }
}
