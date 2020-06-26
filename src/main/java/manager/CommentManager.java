package manager;

import db.DBConnectionProvider;
import model.Comment;
import model.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CommentManager {
    private Connection connection;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public CommentManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    UserManager userManager = new UserManager();
    TaskManager taskManager = new TaskManager();
    public List<Comment> getAllComments() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `comment`");
            List<Comment> comments = new LinkedList<>();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setTaskId(resultSet.getInt("taskId"));
                comment.setUserId(resultSet.getInt("userId"));
                comment.setComment(resultSet.getString("comment"));
                comment.setDate(new Date());
                comment.setUser(userManager.getById(comment.getUserId()));
                comment.setTask(taskManager.getByUserId(comment.getUserId()));
                comments.add(comment);
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Comment> getCommentsByUserId(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `comment` WHERE `userId` = ?");
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();

            List<Comment> comments = new LinkedList<>();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setTaskId(resultSet.getInt("taskId"));
                comment.setUserId(resultSet.getInt("userId"));
                comment.setComment(resultSet.getString("comment"));
                comment.setDate(sdf.parse(resultSet.getString("date")));
                comments.add(comment);
            }
            return comments;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Comment getCommentFromResultSet(ResultSet resultSet) {
        try {
            return Comment.builder()
                    .id(resultSet.getInt(1))
                    .taskId(resultSet.getInt(2))
                    .userId(resultSet.getInt(3))
                    .comment(resultSet.getString(4))
                    .date(resultSet.getString(5) == null ? null : sdf.parse(resultSet.getString(5)))
                    .build();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO comment(`taskId`,`userId`,`comment`) VALUES(?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, comment.getTaskId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getComment());
//            if (comment.getDate() != null) {
//                statement.setString(4, sdf.format(comment.getDate()));
//            } else {
//                statement.setString(4, null);
//            }
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                comment.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeCommentManager(int id) {
        String sql = "DELETE FROM comment WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeCommentUser(int id) {
        String sql = "DELETE FROM comment WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
