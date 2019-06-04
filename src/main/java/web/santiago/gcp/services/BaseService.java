package web.santiago.gcp.services;

import org.springframework.data.jpa.repository.JpaRepository;
import web.santiago.gcp.dtos.BaseDto;
import web.santiago.gcp.entities.Entity;
import web.santiago.gcp.interfaces.services.IService;

import java.util.List;
import java.util.Optional;

/**
 * Classe base para servicos que interagem com repositorio e precisam de metodos basicos para CRUD
 *
 * @param <T> Entity relacionada ao banco de dados
 * @param <K> Dto relacionada a Entity
 */
public abstract class BaseService<T extends Entity, K extends BaseDto> implements IService<T, K> {

    /**
     * Repositorio interno
     */
    protected JpaRepository<T, Long> repository;

    public BaseService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    /**
     * Recupera todas as entidades tipo T da base de dados
     *
     * @return Lista com todos os elementos da base de dados
     */
    // CRUD
    @Override
    public List<T> getAll() {
        return this.repository.findAll();
    }

    /**
     * Recupera uma entidade atravez do seu identificador
     *
     * @param id Idenficador da entidade a ser recuperada
     * @return Container da entidade T
     */
    @Override
    public Optional<T> getById(Long id) {
        return this.repository.findById(id);
    }

    /**
     * Cria ou atualiza uma Entidade
     *
     * @param dto Dto com as informações a serem transformadas em Entidade
     * @return Entidade T
     */
    @Override
    public T save(K dto) {

        T entity = this.mapper(dto);

        return this.repository.save(entity);
    }

    /**
     * Exclue uma Entidade da base de dados
     *
     * @param id Idenficador da Entidade a ser excluida
     */
    @Override
    public void delete(Long id) {

        this.repository.deleteById(id);
    }
}
