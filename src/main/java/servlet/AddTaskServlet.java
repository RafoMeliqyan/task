package servlet;

import manager.TaskManager;
import model.TaskStatus;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/addTask")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddTaskServlet extends HttpServlet {
    TaskManager taskManager = new TaskManager();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final String UPLOAD_DIR = "C:\\web\\Task\\upload";

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
            for (Part part : req.getParts()) {
                if (getFileName(part) != null) {
                    String fileName = System.currentTimeMillis() + getFileName(part);
                    String FullFileName = UPLOAD_DIR + File.separator + fileName;
                    part.write(FullFileName);
                    task.setPictureUrl(fileName);
                }
            }
            taskManager.addTask(task);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/managerHome");
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return null;
    }
}
