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

    /**
     * Get a user by username.
     * @param username
     * @return User
     */
    public User getUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        User user = new User();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setToken(resultSet.getString("token"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    /**
     * Get a user by a token.
     * @param token
     * @return User
     */
    public User getUserByToken(String token) {
        String sql = "select * from user where token = ?";
        User user = new User();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setToken(resultSet.getString("token"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return user;
    }

    /**
     * Update an existing user.
     * @param newUser
     * @return boolean, true if successful, false if failed.
     */
    public boolean update(User newUser) {
        String sql = "UPDATE user SET username = ?, password = ?, token = ? WHERE id = ?";
        boolean success = false;

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());
            statement.setString(3, newUser.getToken());
            statement.setInt(4, newUser.getId());

            success = statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return success;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
