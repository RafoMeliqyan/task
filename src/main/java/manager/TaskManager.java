package manager;

import db.DBConnectionProvider;
import model.TaskStatus;
import model.Task;
import model.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskManager {

    private Connection connection;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public TaskManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    private UserManager userManager = new UserManager();

    public List<Task> getAllTasks() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `task`");
            List<Task> tasks = new LinkedList<>();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setDeadline(sdf.parse(resultSet.getString("deadline")));
                task.setTaskStatus(TaskStatus.valueOf(resultSet.getString("status")));
                task.setUser_id(resultSet.getInt("user_id"));
                task.setPictureUrl(resultSet.getString("picture_url"));
                task.setUser(userManager.getById(task.getUser_id()));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Task> getTasksByUserId(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `task` WHERE `user_id` = ?");
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();

            List<Task> tasks = new LinkedList<>();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setDeadline(sdf.parse(resultSet.getString("deadline")));
                task.setTaskStatus(TaskStatus.valueOf(resultSet.getString("status")));
                task.setUser_id(resultSet.getInt("user_id"));
                task.setPictureUrl(resultSet.getString("picture_url"));
                task.setUser(userManager.getById(task.getUser_id()));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task getByUserId(int userId) {
        String sql = "SELECT * FROM task WHERE user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getTaskFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Task getTaskFromResultSet(ResultSet resultSet) {
        try {
            return Task.builder()
                    .id(resultSet.getInt(1))
                    .name(resultSet.getString(2))
                    .description(resultSet.getString(3))
                    .deadline(resultSet.getString(4) == null ? null : sdf.parse(resultSet.getString(4)))
                    .taskStatus(TaskStatus.valueOf(resultSet.getString(5)))
                    .user_id(resultSet.getInt(6))
                    .pictureUrl(resultSet.getString(7))
                    .build();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addTask(Task task) {
        String sql = "INSERT INTO task(`name`,`description`,`deadline`,`status`,`user_id`,`picture_url`) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            if (task.getDeadline() != null) {
                statement.setString(3, sdf.format(task.getDeadline()));
            } else {
                statement.setString(3, null);
            }
            statement.setString(4, task.getTaskStatus().name());
            statement.setInt(5, task.getUser_id());
            statement.setString(6, task.getPictureUrl());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                task.setId(rs.getInt(1));
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

    public void updateTaskStatus(int taskId, String newStatus) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `task` SET `status` = ? WHERE `id` = ?");
            preparedStatement.setString(1,newStatus);
            preparedStatement.setInt(2,taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
