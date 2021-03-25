import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.LoginResource;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginResourceTest {

    private LoginResource loginResource;

    @BeforeEach
    public void setup() {
        loginResource = new LoginResource();

    }

    @Test
    public void loginSuccess() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUser("username");
        userDTO.setPassword("password");

        IUserDAO userDAOMock = mock(IUserDAO.class);

        User user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setPassword("password");

        when(userDAOMock.getUserByUsername(userDTO.getUser())).thenReturn(user);
        loginResource.setIUserDAO(userDAOMock);

        // Act
        Response response = loginResource.login(userDTO);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void loginIncorrectPassword() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUser("username");
        userDTO.setPassword("passwrd");

        IUserDAO userDAOMock = mock(IUserDAO.class);

        User user = new User();
        user.setId(0);
        user.setUsername("username");
        user.setPassword("password");

        when(userDAOMock.getUserByUsername(userDTO.getUser())).thenReturn(user);
        loginResource.setIUserDAO(userDAOMock);

        // Act
        Response response = loginResource.login(userDTO);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
        assertEquals("Password & Username combination is incorrect.", response.getEntity());
    }

    @Test
    public void loginIncorrectUsername() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUser("usernme");
        userDTO.setPassword("password");

        IUserDAO userDAOMock = mock(IUserDAO.class);

        when(userDAOMock.getUserByUsername(userDTO.getUser())).thenReturn(null);
        loginResource.setIUserDAO(userDAOMock);

        // Act
        Response response = loginResource.login(userDTO);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
        assertEquals("Password & Username combination is incorrect.", response.getEntity());
    }
}


