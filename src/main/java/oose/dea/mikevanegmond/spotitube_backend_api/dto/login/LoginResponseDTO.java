package oose.dea.mikevanegmond.spotitube_backend_api.dto.login;

public class LoginResponseDTO {
    public String user;
    public String token;

    public void setUser(String user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
