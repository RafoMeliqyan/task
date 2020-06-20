package servlet;

import manager.TaskManager;
import model.TaskStatus;
import model.Task;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/addTask")
public class AddTaskServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String deadline = req.getParameter("deadline");
        String status = req.getParameter("status");
        int userId = Integer.parseInt(req.getParameter("user_id"));
        Task task = null;
        try {
            task = Task.builder()
                    .name(name)
                    .description(description)
                    .deadline(sdf.parse(deadline))
                    .taskStatus(TaskStatus.valueOf(status))
                    .user_id(userId)
                    .build();
            taskManager.addTask(task);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/managerHome");
    }
}
