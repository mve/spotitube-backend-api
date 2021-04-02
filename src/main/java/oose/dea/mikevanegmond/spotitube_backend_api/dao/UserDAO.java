package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.exceptions.InvalidTokenException;

import javax.annotation.Resource;
import javax.enterprise.inject.Alternative;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Alternative
public class UserDAO implements IUserDAO {

    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    final String SQL_GET_USER_BY_USERNAME = "select * from user where username = ?";
    final String SQL_GET_USER_BY_TOKEN = "select * from user where token = ?";
    final String SQL_UPDATE_USER = "UPDATE user SET username = ?, password = ?, token = ? WHERE id = ?";

    /**
     * Get a user by username.
     * @param username
     * @return User
     */
    public User getUserByUsername(String username) {
        User user = new User();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_BY_USERNAME);
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
    public User getUserByToken(String token) throws InvalidTokenException {
        User user = new User();

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_BY_TOKEN);
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setToken(resultSet.getString("token"));
            } else {
                throw new InvalidTokenException();
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
        boolean success = false;

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);
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
