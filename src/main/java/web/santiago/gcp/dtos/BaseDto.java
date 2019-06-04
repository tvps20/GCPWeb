package web.santiago.gcp.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe base para todas as Dtos
 *
 * @author Santiago Brothers
 */
public abstract class BaseDto {

    /**
     * Utilizado para atualizar entidades
     */
    @Getter
    @Setter
    private long id;
}
