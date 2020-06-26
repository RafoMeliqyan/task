package servlet;

import manager.CommentManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/removeCommentUser")
public class RemoveCommentUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int commentId = Integer.parseInt(id);
        CommentManager commentManager = new CommentManager();
        commentManager.removeCommentUser(commentId);
        resp.sendRedirect("/userTasks");
    }
}
