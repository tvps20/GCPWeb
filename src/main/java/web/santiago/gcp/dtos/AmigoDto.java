package web.santiago.gcp.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Define as informações enviadas pela view para o controller da entidade Amigo
 *
 * @author Santiago Brothers
 */
@Data
public class AmigoDto extends BaseDto {

    @NotNull
    @NotEmpty
    private String nome;

    private String sexo;
    private String parentesco;
    private String endereco;
    private String telefone;
}
