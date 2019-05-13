package web.santiago.gcp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Define as informações enviadas pela view para o controller da entidade JogoDigital
 *
 * @author Santiago Brothers
 */
public class JogoDigitalDto extends ItemDto {

    @NotEmpty
    @NotNull
    @Getter
    @Setter
    private String console;

    @Getter
    @Setter
    private boolean finalizado;
}
