package servlet;

import manager.Manager;
import manager.TaskManager;
import manager.UserManager;
import model.ManagerModel;
import model.Task;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/managerHome")

public class ManagerHomeServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();
    UserManager userManager = new UserManager();
    Manager manager = new Manager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> allUsers = userManager.getAllUsers();

        List<Task> allTasks = taskManager.getAllTasks();

        req.setAttribute("allUsers", allUsers);
        req.setAttribute("allTasks", allTasks);
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> allUsers = userManager.getAllUsers();
        List<Task> allTasks = taskManager.getAllTasks();

        req.setAttribute("allUsers", allUsers);
        req.setAttribute("allTasks", allTasks);
    }

}
