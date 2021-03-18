import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.LoginResource;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.LoginDTO;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUser("username");
        loginDTO.setPassword("password");

        IUserDAO userDAOMock = mock(IUserDAO.class);

        User user = new User(0);
        user.setUsername("username");
        user.setPassword("password");

        when(userDAOMock.getUserByUsername(loginDTO.getUser())).thenReturn(user);
        loginResource.setIUserDAO(userDAOMock);

        // Act
        Response response = loginResource.login(loginDTO);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void loginIncorrectPassword() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUser("username");
        loginDTO.setPassword("passwrd");

        IUserDAO userDAOMock = mock(IUserDAO.class);

        User user = new User(0);
        user.setUsername("username");
        user.setPassword("password");

        when(userDAOMock.getUserByUsername(loginDTO.getUser())).thenReturn(user);
        loginResource.setIUserDAO(userDAOMock);

        // Act
        Response response = loginResource.login(loginDTO);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals("Password & Username combination is incorrect.", response.getEntity());
    }

    @Test
    public void loginIncorrectUsername() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUser("usernme");
        loginDTO.setPassword("password");

        IUserDAO userDAOMock = mock(IUserDAO.class);

        when(userDAOMock.getUserByUsername(loginDTO.getUser())).thenReturn(null);
        loginResource.setIUserDAO(userDAOMock);

        // Act
        Response response = loginResource.login(loginDTO);

        // Assert
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals("Password & Username combination is incorrect.", response.getEntity());
    }
}

