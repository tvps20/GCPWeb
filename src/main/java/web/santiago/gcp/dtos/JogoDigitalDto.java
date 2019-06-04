package web.santiago.gcp.dtos;

import lombok.Data;

/**
 * Define as informações enviadas pela view para o controller da entidade JogoDigital
 */
@Data
public class JogoDigitalDto extends ItemDto {

    private String console;
    private boolean finalizado;
}
