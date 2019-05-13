package web.santiago.gcp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Define as informações enviadas pela view para o controller da entidade JogoTabuleiro
 *
 * @author Santiago Brothers
 */
public class JogoTabuleiroDto extends ItemDto {

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String marca;
}
