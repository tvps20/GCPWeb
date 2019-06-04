package web.santiago.gcp.entities;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Representa um Amigo
 *
 * @author Santiago Brothers
 */
@Data
@javax.persistence.Entity
public class User extends Entity {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
    private boolean admin;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.admin = false;
    }

    public User(){
        this.admin = false;
    }
}
