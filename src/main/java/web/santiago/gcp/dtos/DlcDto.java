package web.santiago.gcp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Define as informações enviadas pela view para o controller da entidade Dlc
 *
 * @author Santiago Brothers
 */
public class DlcDto extends ItemDto {

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String localizacao;

    @NotNull
    @Min(1)
    @Getter
    @Setter
    private long jogo;
}
