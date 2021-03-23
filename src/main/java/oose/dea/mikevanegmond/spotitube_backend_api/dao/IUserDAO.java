package oose.dea.mikevanegmond.spotitube_backend_api.dao;

import oose.dea.mikevanegmond.spotitube_backend_api.domain.User;

public interface IUserDAO {
//    User getUserById(int id);
    User getUserByUsername(String username);
    User getUserByToken(String token);
    void update(User user);
}
