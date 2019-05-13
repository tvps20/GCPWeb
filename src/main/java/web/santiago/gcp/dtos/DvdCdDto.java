package web.santiago.gcp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Define as informações enviadas pela view para o controller da entidade DvdCd
 *
 * @author Santiago Brothers
 */
public class DvdCdDto extends ItemDto {

    @Getter
    @Setter
    private String marca;

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String conteudo;

    @Getter
    @Setter
    private boolean assistido;
}
