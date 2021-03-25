package oose.dea.mikevanegmond.spotitube_backend_api.dto;

public class EditPlaylistDTO {
    public int id;
    private String name;
    private boolean owner;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
