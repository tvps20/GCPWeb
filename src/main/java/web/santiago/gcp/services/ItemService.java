package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.ItemDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.repositories.ItemRepository;

import java.util.List;
import java.util.Optional;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Item e o repositorio da entidade Item
 */
@Service
public class ItemService extends BaseService<Item, ItemDto> {

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        super(itemRepository);
    }

    /**
     * Executa uma busca por um item baseado no seu tipo e no seu Id relacionado
     *
     * @param itemId Id associacao deste item com as demais tabelas de colecoes
     * @param tipo   Tipo especifico do item referente as demais tabelas de colecoes
     * @return Container da Entidade Item
     */
    public Optional<Item> getByItemIdAndTipo(long itemId, String tipo) {
        return getRepository().findByItemIdAndTipo(itemId, tipo);
    }

    /**
     * Exclue um Item da base de dados
     *
     * @param itemId Id associado a este item referente as demais tabelas de colecoes
     * @return Booleano
     */
    public boolean deleteByItemId(long itemId) {
        return this.getRepository().deleteByItemId(itemId);
    }

    /**
     * Executa uma busca para saber quais são os 10 itens mais importantes
     *
     * @return Uma lista contendo os 10 itens mais importantes ordenados do maior para o menor
     */
    public List<Item> getTop10ByImportancia() {
        return getRepository().findTop10ByOrderByImportanciaDesc();
    }

    /**
     * Executa uma busca para saber quais são os 10 itens mais emprestados
     *
     * @return Uma lista contendo os 10 itens mais emprestados ordenados do maior para o menor
     */
    public List<Item> getTop10ByEmprestimos() {
        return getRepository().findTop10ByOrderByQtdEmprestimosDesc();
    }

    /**
     * Executa um cast do objeto repositorio generico da super classe para o tipo especifico do servico
     *
     * @return Repositorio especifico do servico
     */
    private ItemRepository getRepository() {
        return (ItemRepository) this.repository;
    }

    /**
     * Transforma uma Dto Item em Entidade Item
     *
     * @param dto Dto a ser transformada em Item
     * @return Entity Item
     */
    @Override
    public Item mapper(ItemDto dto) {

        Item item = new Item(
                dto.getTitulo(),
                dto.getEstado(),
                dto.getPreco(),
                dto.getObservacoes(),
                dto.isEmprestado(),
                dto.getQuantidade(),
                dto.getQtdEmprestimos(),
                dto.getImportancia(),
                dto.isWishlist(),
                dto.getDisponibilidade(),
                dto.getUrl(),
                dto.getItemId(),
                dto.getTipo()
        );

        return item;
    }
}
