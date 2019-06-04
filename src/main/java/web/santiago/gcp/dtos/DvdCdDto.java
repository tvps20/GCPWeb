package web.santiago.gcp.dtos;

import lombok.Data;

/**
 * Define as informações enviadas pela view para o controller da entidade DvdCd
 */
@Data
public class DvdCdDto extends ItemDto {

    private String marca;
    private String conteudo;
    private boolean assistido;
}
