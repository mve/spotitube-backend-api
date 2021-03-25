package oose.dea.mikevanegmond.spotitube_backend_api.dto;

public class UserDTO {
    public String token;
    private String user;
    private String password;

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
