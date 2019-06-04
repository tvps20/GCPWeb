package web.santiago.gcp.interfaces.services;

import web.santiago.gcp.dtos.BaseDto;
import web.santiago.gcp.entities.Entity;
import web.santiago.gcp.interfaces.IMapperToEntity;

import java.util.List;
import java.util.Optional;

/**
 * Define como deve ser um Serviço para interagir com a base de dados e com as Entidades
 * Representa a camada entre o controller e o repositorio
 * @author Santiago Brothers
 *
 * @param <T> Entidade relacionada ao repositorio
 * @param <K> Dto de comunicação entre a view e o repositorio
 */
public interface IService<T extends Entity, K extends BaseDto> extends IMapperToEntity<K, T> {

    /**
     * Recupera todas as entidade da base de dados
     *
     * @return Uma lista com todas as entidades
     */
    List<T> getAll();

    /**
     * Recupera uma entidade da base de dados
     *
     * @param id Idenficador da entidade a ser recuperada
     * @return Container que encapsula a entidade
     */
    Optional<T> getById(Long id);

    /**
     * Cria ou atualiza uma entidade
     *
     * @param dto Dto com as informações a serem transformadas em entidade
     * @return Nova ou atualizada entidade
     */
    T save(K dto);

    /**
     * Exclue uma entidade da base de dados
     *
     * @param id Idenficador da entidade a ser excluida
     */
    void delete(Long id);
}
