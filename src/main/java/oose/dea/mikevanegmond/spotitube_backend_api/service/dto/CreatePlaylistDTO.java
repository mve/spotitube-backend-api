package oose.dea.mikevanegmond.spotitube_backend_api.service.dto;

public class CreatePlaylistDTO {
    public int id;
    public String name;
    public boolean owner;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
