package web.santiago.gcp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDto extends BaseDto {

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String username;

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String password;


    @Getter
    @Setter
    private String confirmaPassword;

    @Getter
    @Setter
    private boolean admin;

    public UserDto() {
        this.admin = false;
    }
}
