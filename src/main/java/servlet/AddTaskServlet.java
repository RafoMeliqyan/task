package servlet;

import manager.TaskManager;
import model.Status;
import model.Task;

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
        String desc = req.getParameter("desc");
        String deadline = req.getParameter("deadline");
        Task task = null;
        try {
            task = Task.builder()
                    .name(name)
                    .desc(desc)
                    .deadline(sdf.parse(deadline))
                    .status(Status.TASK)
                    .user_id(2)
                    .build();
            taskManager.addTask(task);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/managerHome");
    }
}
