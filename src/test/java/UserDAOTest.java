import oose.dea.mikevanegmond.spotitube_backend_api.dao.UserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.Track;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    public void setup() {
        userDAO = new UserDAO();
    }

    @Test
    public void getUserByUsernameTest() {
        try {
            // Arrange
            String expectedSQL = "select * from user where username = ?";
            final int ID = 0;
            final String USERNAME = "username";
            final String PASSWORD = "password";
            final String TOKEN = "123";

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(ID);
            when(resultSet.getString("username")).thenReturn(USERNAME);
            when(resultSet.getString("password")).thenReturn(PASSWORD);
            when(resultSet.getString("token")).thenReturn(TOKEN);


            // setup classes
            userDAO.setDataSource(dataSource);

            // Act
            User user = userDAO.getUserByUsername(USERNAME);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(ID, user.getId());
            assertEquals(USERNAME, user.getUsername());
            assertEquals(PASSWORD, user.getPassword());
            assertEquals(TOKEN, user.getToken());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getUserByTokenTest() {
        try {
            // Arrange
            String expectedSQL = "select * from user where token = ?";
            final int ID = 0;
            final String USERNAME = "username";
            final String PASSWORD = "password";
            final String TOKEN = "123";

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getInt("id")).thenReturn(ID);
            when(resultSet.getString("username")).thenReturn(USERNAME);
            when(resultSet.getString("password")).thenReturn(PASSWORD);
            when(resultSet.getString("token")).thenReturn(TOKEN);


            // setup classes
            userDAO.setDataSource(dataSource);

            // Act
            User user = userDAO.getUserByToken(TOKEN);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertEquals(ID, user.getId());
            assertEquals(USERNAME, user.getUsername());
            assertEquals(PASSWORD, user.getPassword());
            assertEquals(TOKEN, user.getToken());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateTest() {
        try {
            // Arrange
            String expectedSQL = "UPDATE user SET username = ?, password = ?, token = ? WHERE id = ?";
            final int ID = 0;
            final String USERNAME = "username";
            final String PASSWORD = "password";
            final String TOKEN = "123";

            User user = new User();
            user.setId(ID);
            user.setUsername(USERNAME);
            user.setPassword(PASSWORD);
            user.setToken(TOKEN);

            // setup Mocks
            DataSource dataSource = mock(DataSource.class);
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);

            // instruct Mocks
            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);

            // setup classes
            userDAO.setDataSource(dataSource);

            // Act
            boolean status = userDAO.update(user);

            // Assert
            verify(connection).prepareStatement(expectedSQL);

            assertTrue(status);

        } catch (Exception e) {
            fail();
        }
    }

}
