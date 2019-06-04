package web.santiago.gcp.dtos;

import lombok.Data;

/**
 * Define as informações enviadas pela view para o controller da entidade JogoTabuleiro
 */
@Data
public class JogoTabuleiroDto extends ItemDto {

    private String marca;
}
