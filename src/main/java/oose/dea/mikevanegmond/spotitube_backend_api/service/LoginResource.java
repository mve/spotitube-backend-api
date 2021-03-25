package oose.dea.mikevanegmond.spotitube_backend_api.service;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.UserDTO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/login")
public class LoginResource {

    private IUserDAO userDAO;

    @Inject
    public void setIUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Checks username and password combination, if correct generates a new token and logs user in.
     * @param loginRequest
     * @return UserDTO with a token and username.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO loginRequest) {

        User user = userDAO.getUserByUsername(loginRequest.getUser());

        if (user == null)
        {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Password & Username combination is incorrect.")
                    .build();
        }

        if (!user.getPassword().equals(loginRequest.getPassword()))
        {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Password & Username combination is incorrect.")
                    .build();
        }

        String newUUID = UUID.randomUUID().toString();
        user.setToken(newUUID);
        userDAO.update(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setToken(user.getToken());
        userDTO.setUser(user.getUsername());

        return Response.status(Response.Status.OK)
                .entity(userDTO)
                .build();
    }

}
