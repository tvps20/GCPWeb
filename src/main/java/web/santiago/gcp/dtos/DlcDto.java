package web.santiago.gcp.dtos;

import lombok.Data;

/**
 * Define as informações enviadas pela view para o controller da entidade Dlc
 */
@Data
public class DlcDto extends ItemDto {

    private String localizacao;

    private long jogo;
}
