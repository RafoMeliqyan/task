package servlet;

import manager.CommentManager;
import manager.TaskManager;
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

@WebServlet(urlPatterns = "/userTasks")
public class UserTasksServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();
    CommentManager commentManager = new CommentManager();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Task> tasksByUserId = taskManager.getTasksByUserId(user.getId());
        List<Comment> allComments = commentManager.getAllComments();
        req.setAttribute("tasks", tasksByUserId);
        req.setAttribute("allComments", allComments);
        req.getRequestDispatcher("/userTasks.jsp").forward(req, resp);
    }
}
