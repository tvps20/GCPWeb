package web.santiago.gcp.dtos;

import lombok.Data;

/**
 * Define as informações enviadas pela view para o controller da entidade Amigo
 */
@Data
public class AmigoDto extends BaseDto {

    private String nome;
    private String sexo;
    private String parentesco;
    private String endereco;
    private String telefone;
}
