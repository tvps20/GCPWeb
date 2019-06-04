package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Item;

import java.util.List;
import java.util.Optional;

/**
 * Interface de comunicação com a base de dados Item
 *
 * @author Santiago Brothers
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Executa uma busca por um item baseado no seu tipo e no seu Id relacionado
     *
     * @param itemId Id associacao deste item com as demais tabelas de colecoes
     * @param tipo   Tipo especifico do item referente as demais tabelas de colecoes
     * @return Container da Entidade Item
     */
    Optional<Item> findByItemIdAndTipo(Long itemId, String tipo);

    /**
     * Executa uma busca para recuperar todos os items de um determinado tipo
     *
     * @param tipo Tipo do item a ser buscado
     * @return Lista de Item de um unico tipo
     */
    List<Item> findAllByTipo(String tipo);

    /**
     * Exclue um Item da base de dados
     *
     * @param id Id associado a este item referente as demais tabelas de colecoes
     * @return Booleano
     */
    boolean deleteByItemId(Long id);

    /**
     * Executa uma busca para saber quais são os 10 itens mais importantes
     *
     * @return Uma lista contendo os 10 itens mais importantes ordenados do maior para o menor
     */
    List<Item> findTop10ByOrderByImportanciaDesc();

    /**
     * Executa uma busca para saber quais são os 10 itens mais emprestados
     *
     * @return Uma lista contendo os 10 itens mais emprestados ordenados do maior para o menor
     */
    List<Item> findTop10ByOrderByQtdEmprestimosDesc();

    /**
     * Executa uma busca para recuperar todos os items de uma lista de ids.
     *
     * @param ids Lista de Ids
     * @return Lista de Item
     */
    List<Item> findAllByItemIdIn(List<Long> ids);

    /**
     * Executa uma busca para recuperar todos os items de uma lista de ids.
     *
     * @param ids Lista de Ids
     * @return Lista de Item
     */
    List<Item> findAllByIdIn(List<Long> ids);

    /**
     * Executa uma busca para recuperar todos os items de uma saga
     *
     * @param id Id da Saga
     * @return Lista de Items
     */
    List<Item> findAllBySagaId(Long id);

    /**
     * Executa uma busca para recuperar todos os items que estão na wishlist
     *
     * @param wishilist
     * @return
     */
    List<Item> findAllByWishlist(boolean wishilist);
}
