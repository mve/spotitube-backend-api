package oose.dea.mikevanegmond.spotitube_backend_api.service.dto;

public class EditPlaylistDTO {
    public int id;
    public String name;
    public int owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
