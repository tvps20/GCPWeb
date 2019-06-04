package web.santiago.gcp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import web.santiago.gcp.dtos.BaseDto;
import web.santiago.gcp.dtos.ItemDto;
import web.santiago.gcp.entities.Entity;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.exceptions.EntityNotFoundException;
import web.santiago.gcp.exceptions.ResourceNotFoundException;
import web.santiago.gcp.interfaces.services.IService;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Classe base para servicos que interagem com repositorio e precisam de metodos basicos para CRUD
 *
 * @param <T> Entity relacionada ao banco de dados
 * @param <K> Dto relacionada a Entity
 * @author Santiago Brothers
 */
public abstract class BaseService<T extends Entity, K extends BaseDto> implements IService<T, K> {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

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
     * Recupera todas as entidades tipo T da base de dados
     *
     * @return Page com todos os elementos da base de dados
     */
    public Page listAll(Pageable pageable) {
        return this.repository.findAll(pageable);
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

        try {
            T entity = this.mapper(dto);
            return this.repository.save(entity);

        } catch (EntityNotFoundException e) {
            logger.error("Mapping Object Error", e);
            throw e;
        }
    }

    /**
     * Atualiza uma entidade
     *
     * @param entity Entitdade a ser atualizada
     * @return Entitdade Atualizada
     */
    @Override
    public T update(T entity) {
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

    /**
     * Transforma uma Entidade Item em Dto Item
     *
     * @param item Entidade a ser transformado em Dto
     * @param dto  Dto Resultado
     * @return Dto Mapeada
     */
    public ItemDto maperItemToDto(Item item, ItemDto dto) {

        dto.setDisponibilidade(item.getDisponibilidade());
        dto.setEmprestado(item.isEmprestado());
        dto.setEstado(item.getEstado());
        dto.setImportancia(item.getImportancia());
        dto.setItemId(item.getItemId());
        dto.setObservacoes(item.getObservacoes());
        dto.setPreco(item.getPreco());
        dto.setQtdEmprestimos(item.getQtdEmprestimos());
        dto.setQuantidade(item.getQuantidade());
        dto.setTipo(item.getTipo());
        dto.setTitulo(item.getTitulo());
        dto.setUrl(item.getUrl());
        dto.setWishlist(item.isWishlist());
        dto.setId(item.getId());

        return dto;
    }

    /**
     * Verificar se um Item existe na base de dados
     *
     * @param id Identificador da entidade a ser verificado
     */
    public void verifyIfExists(Long id) {
        Optional<T> item = this.getById(id);

        if (!item.isPresent()) {
            throw new ResourceNotFoundException("Não existe um item para o ID: " + id);
        }
    }
}
