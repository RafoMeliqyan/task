package manager;

import db.DBConnectionProvider;
import model.Status;
import model.Task;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private Connection connection;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public TaskManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(getTaskFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getTasksByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE user_id = " + userId;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(getTaskFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


    private Task getTaskFromResultSet(ResultSet resultSet) {
        try {
            return Task.builder()
                    .id(resultSet.getLong(1))
                    .name(resultSet.getString(2))
                    .desc(resultSet.getString(3))
                    .deadline(resultSet.getString(4) == null ? null : sdf.parse(resultSet.getString(4)))
                    .status(Status.valueOf(resultSet.getString(5)))
                    .user_id(resultSet.getInt(6))
                    .build();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addTask(Task task) {
        String sql = "INSERT INTO task(`name`,`desc`,`deadline`,`status`) VALUES(?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDesc());
            if (task.getDeadline() != null) {
                statement.setString(3, sdf.format(task.getDeadline()));
            } else {
                statement.setString(3, null);
            }
            statement.setString(4, task.getStatus().name());
//            statement.setInt(5,task.getUser_id());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                task.setId(rs.getLong(1));
                task.setUser_id(rs.getInt(5));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeTask(long id) {
        String sql = "DELETE FROM task WHERE id = " + id;
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
