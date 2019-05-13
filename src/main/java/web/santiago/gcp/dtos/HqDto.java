package web.santiago.gcp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Define as informações enviadas pela view para o controller da entidade Hq
 *
 * @author Santiago Brothers
 */

public class HqDto extends ItemDto {

    @Getter
    @Setter
    private int numero;

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String editora;

    @Getter
    @Setter
    private String universo;

    @Getter
    @Setter
    private String saga;
}
