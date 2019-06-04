package web.santiago.gcp.dtos;

import lombok.Data;

/**
 * Classe base para todas as Dtos
 */
@Data
public abstract class BaseDto {

    /**
     * Utilizado para atualizar entidades
     */
    private long id;
}
