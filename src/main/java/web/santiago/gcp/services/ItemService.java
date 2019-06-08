package web.santiago.gcp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.ItemDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.exceptions.EntityNotFoundException;
import web.santiago.gcp.repositories.ItemRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Item e o repositorio da entidade Item
 *
 * @author Santiago Brothers
 */
@Service
public class ItemService extends BaseService<Item, ItemDto> {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

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

    public List<Item> getAllByItemTipo(TipoColecao tipo) {
        return getRepository().findAllByTipo(tipo.getValor());
    }

    /**
     * Exclue um Item da base de dados
     *
     * @param itemId Id associado a este item referente as demais tabelas de colecoes
     * @return Booleano
     */
    public void deleteByItemId(long itemId) {
        this.getRepository().deleteByItemId(itemId);
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
     * Recupera todos os items baseado em seu titulo e/ou tipo e/ou estado e/ou emprestados e/ou ids especificos
     *
     * @param titulo      Titulo a ser buscado
     * @param tipo        Tipo a ser buscado
     * @param estado      Estado a ser buscado
     * @param emprestados Filtro booleano
     * @param ids         Ids especificos a serem buscados
     * @return Lista Item
     */
    public List<Item> getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(String titulo, String tipo, String estado, boolean emprestados, List<Long> ids) {

        List<Item> items;

        if (!ids.isEmpty())
            items = this.getRepository().findAllByItemIdIn(ids);
        else
            items = new ArrayList<>();

        Stream<Item> result = items.stream();

        if (titulo != null && titulo != "")
            result = result.filter(item -> item.getTitulo().equals(titulo));

        if (tipo != null && tipo != "")
            result = result.filter(item -> item.getTipo().equals(tipo));

        if (estado != null && estado != "")
            result = result.filter(item -> item.getEstado().equals(estado));

        if (emprestados)
            result = result.filter(item -> item.isEmprestado());

        return result.collect(Collectors.toList());
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
     * Executa uma busca pelo items que estão na wish list e filtra por até uma semana de disponibilidade
     *
     * @return Lista contendo os items disponiveis em até uma semana
     */
    public List<Item> getWishListItems() {
        List<Item> wishList = getRepository().findAllByWishlist(true);
        List<Item> result = wishList.stream().filter(item -> {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, 7);
            Date semanaQueVem = calendar.getTime();

            long diff = semanaQueVem.getTime() - item.getDisponibilidade().getTime();
            long days = diff / (1000 * 60 * 60 * 24);

            if (days < 8 && days > 0)
                return true;

            return false;
        }).collect(Collectors.toList());

        return result;
    }

    /**
     * Atualiza dados de emprestimo do Item
     *
     * @param id Item a ser emprestado
     * @return Item emprestado
     */
    public Item emprestarItem(long id) {

        Optional<Item> item = this.getRepository().findById(id);
        if (!item.isPresent()) {
            logger.error("Item not found. Id {}", id);
            throw new EntityNotFoundException("Item not found", "Item", "Emprestimo");
        }

        item.get().setEmprestado(true);
        item.get().setQtdEmprestimos(item.get().getQtdEmprestimos() + 1);

        this.repository.save(item.get());
        return item.get();
    }

    /**
     * Executa uma busca por ids
     *
     * @param ids Ids de Items
     * @return Lista de Items
     */
    public List<Item> getAllItemsIn(List<Long> ids) {
        return getRepository().findAllByIdIn(ids);
    }

    /**
     * Salva todos os items
     *
     * @param items Items a serem salvos
     * @return Lista de Items atualizados
     */
    public List<Item> saveAll(List<Item> items) {
        return this.repository.saveAll(items);
    }

    public List<Item> getAllItemsPorSaga(Long sagaId) {
        return getRepository().findAllBySagaId(sagaId);
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

        logger.info("Mapping 'ItemDto' to 'Item'");

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

        if (item.isEmprestado() && item.getId() == 0)
            item.setQtdEmprestimos(item.getQtdEmprestimos() + 1);

        if (dto.getId() != 0)
            item.setId(dto.getId());

        return item;
    }
}
