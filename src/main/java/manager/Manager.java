package manager;

import db.DBConnectionProvider;
import model.ManagerModel;
import model.UserType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    private Connection connection;

    public Manager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public boolean register(ManagerModel user) {
        String sql = "INSERT INTO manager(name,surname,email,password) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, String.valueOf(user.getUserType()));
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public User getById(long id) {
//        String sql = "SELECT * FROM manager WHERE id = " + id;
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            if (resultSet.next()) {
//                return getUserFromResultSet(resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public ManagerModel getByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM manager WHERE email = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getManagerFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public ManagerModel getByEmail(String email) {
//        String sql = "SELECT * FROM manager WHERE email = ?";
//        try {
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, email);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return getUserFromResultSet(resultSet);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private ManagerModel getManagerFromResultSet(ResultSet resultSet) {
        try {
            return ManagerModel.builder()
                    .id(resultSet.getLong(1))
                    .name(resultSet.getString(2))
                    .surname(resultSet.getString(3))
                    .email(resultSet.getString(4))
                    .password(resultSet.getString(5))
                    .userType(UserType.MANAGER)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ManagerModel> getAllManagers() {
        List<ManagerModel> managers = new ArrayList<>();
        String sql = "SELECT * FROM manager";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                managers.add(getManagerFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }

//    public boolean removeManagerById(long id) {
//        String sql = "DELETE FROM manager WHERE id = " + id;
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(sql);
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
