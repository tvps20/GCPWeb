package web.santiago.gcp.entities;


import lombok.Data;

/**
 * Representa um Amigo
 *
 * @author Santiago Brothers
 */
@Data
@javax.persistence.Entity
public class User extends Entity {

    private String username;
    private String password;
    private String role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "USER";
    }

}
