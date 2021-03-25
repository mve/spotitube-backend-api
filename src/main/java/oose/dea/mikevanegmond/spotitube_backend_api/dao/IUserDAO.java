package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;
import oose.dea.mikevanegmond.spotitube_backend_api.exceptions.InvalidTokenException;

public interface IUserDAO {
    User getUserByUsername(String username);
    User getUserByToken(String token) throws InvalidTokenException;
    boolean update(User user);
}
