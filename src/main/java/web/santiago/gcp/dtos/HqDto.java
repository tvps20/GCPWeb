package web.santiago.gcp.dtos;

import lombok.Data;

/**
 * Define as informações enviadas pela view para o controller da entidade Hq
 */
@Data
public class HqDto extends ItemDto {
    private int numero;
    private String editora;
    private String universo;
    private String saga;
}
