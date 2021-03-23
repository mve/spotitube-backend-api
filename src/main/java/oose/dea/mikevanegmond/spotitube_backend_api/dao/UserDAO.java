package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

//    public User getUserById(int id) {
//
//        String sql = "select * from user where id = ?";
//
//        try (Connection connection = dataSource.getConnection()) {
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                User user = new User(resultSet.getInt("id"));
//                user.setUsername(resultSet.getString("username"));
//                user.setPassword(resultSet.getString("password"));
//                user.setToken(resultSet.getString("token"));
//
//                return user;
//            }
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
//
//        return null;
//    }

    public User getUserByUsername(String username) {

        String sql = "select * from user where username = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setToken(resultSet.getString("token"));

                return user;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public User getUserByToken(String token) {

        String sql = "select * from user where token = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setToken(resultSet.getString("token"));

                return user;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void update(User newUser) {

        String sql = "UPDATE user SET username = ?, password = ?, token = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());
            statement.setString(3, newUser.getToken());
            statement.setInt(4, newUser.getId());

            statement.executeUpdate();

//            while (resultSet.next()){
//                User user = new User(resultSet.getInt("id"));
//                user.setUsername(resultSet.getString("username"));
//                user.setPassword(resultSet.getString("password"));
//                user.setToken(resultSet.getString("token"));
//
//                return user;
//            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
