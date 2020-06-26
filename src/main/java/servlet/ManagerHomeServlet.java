package servlet;

import manager.CommentManager;
import manager.TaskManager;
import manager.UserManager;
import model.Comment;
import model.Task;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/managerHome")

public class ManagerHomeServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();
    UserManager userManager = new UserManager();
    CommentManager commentManager = new CommentManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Task> allTasks = taskManager.getAllTasks();
        List<User> allUsers = userManager.getAllUsers();
        List<Comment> allComments = commentManager.getAllComments();
        req.setAttribute("allTasks", allTasks);
        req.setAttribute("allUsers", allUsers);
        req.setAttribute("allComments", allComments);
        req.getRequestDispatcher("/WEB-INF/managerHome.jsp").forward(req, resp);
    }
}
