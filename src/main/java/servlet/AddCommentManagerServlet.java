package servlet;

import manager.CommentManager;
import model.Comment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = "/addCommentManager")
public class AddCommentManagerServlet extends HttpServlet {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    CommentManager commentManager = new CommentManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        String comment = req.getParameter("comment");
        Comment comment1 = null;
        comment1 = Comment.builder()
                .taskId(taskId)
                .userId(userId)
                .comment(comment)
                .date(new Date())
                .build();
        commentManager.addComment(comment1);
        resp.sendRedirect("/managerHome");
    }
}
