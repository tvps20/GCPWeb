package web.santiago.gcp.interfaces;

import web.santiago.gcp.dtos.BaseDto;
import web.santiago.gcp.entities.Entity;
import web.santiago.gcp.exceptions.EntityNotFoundException;

/**
 * Define o comportamento de transformar uma Dto em uma Entidade
 * @author Santiago Brothers
 *
 * @param <T> Dto a ser transformada
 * @param <K> Entitade resultante da transformação
 */
public interface IMapperToEntity<T extends BaseDto, K extends Entity> {

	/**
     * Executa a transformação da Dto em Entidade
     *
     * @param dto Dto a ser transformada
     * @return Entidade resultante da transformação
     * @throws EntityNotFoundException Se estiver mapeando sub-objetos
     */
    K mapper(T dto) throws EntityNotFoundException;
}
