package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Item;

import java.util.List;
import java.util.Optional;

/**
 * Interface de comunicação com a base de dados Item
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
}
