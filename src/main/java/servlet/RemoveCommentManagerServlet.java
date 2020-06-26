package servlet;

import manager.CommentManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/removeCommentManager")
public class RemoveCommentManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int commentId = Integer.parseInt(id);
        CommentManager commentManager = new CommentManager();
        commentManager.removeCommentManager(commentId);
        resp.sendRedirect("/managerHome");
    }
}
