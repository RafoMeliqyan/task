package servlet;

import manager.TaskManager;
import manager.UserManager;
import model.Task;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/userHome")
public class UserHomeServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> tasks = taskManager.getAllTasks();
        req.setAttribute("allTasks", tasks);
        req.getRequestDispatcher("/userHome.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> tasks = taskManager.getAllTasks();
        req.setAttribute("allTasks", tasks);
        req.getRequestDispatcher("/userHome.jsp").forward(req, resp);
    }
}
