package oose.dea.mikevanegmond.spotitube_backend_api.service;

import oose.dea.mikevanegmond.spotitube_backend_api.dao.IUserDAO;
import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.service.dto.LoginDTO;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginRequest) {

        User user = userDAO.getUserByUsername(loginRequest.getUser());

        if (user == null)
        {
            // TODO werkt raar op de live server, toont 200 melding maar niet een error melding.
            return Response.status(Response.Status.OK)
                    .entity("Password & Username combination is incorrect.")
                    .build();
        }

        if (!user.getPassword().equals(loginRequest.getPassword()))
        {
            // TODO werkt raar op de live server, toont 200 melding maar niet een error melding.
            return Response.status(Response.Status.OK)
                    .entity("Password & Username combination is incorrect.")
                    .build();
        }

        String newUUID = UUID.randomUUID().toString();
        user.setToken(newUUID);
        userDAO.update(user);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.token = user.getToken();
        loginDTO.user = user.getUsername();

        return Response.status(Response.Status.OK)
                .entity(loginDTO)
                .build();
    }

}
