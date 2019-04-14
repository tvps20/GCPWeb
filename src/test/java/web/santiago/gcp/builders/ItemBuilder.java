package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.ItemDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.EstadoItem;
import web.santiago.gcp.enuns.TipoColecao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class ItemBuilder {

    private Item item;
    private Collection<Item> itens;
    private ItemDto itemDto;

    public static ItemBuilder mockItemBuilder() {
        ItemBuilder builder = new ItemBuilder();
        builder.item = new Item();
        builder.item.setTitulo("Wow");
        builder.item.setEstado(EstadoItem.NOVO.getEstado());
        builder.item.setPreco(new BigDecimal(135.50));
        builder.item.setObservacoes("Imagem fisica");
        builder.item.setEmprestado(false);
        builder.item.setQuantidade(1);
        builder.item.setQtdEmprestimos(1);
        builder.item.setImportancia(new Float(1.0));
        builder.item.setWishlist(true);
        builder.item.setDisponibilidade(new Date());
        builder.item.setUrl("jogo.com.br");
        builder.item.setItemId(1);
        builder.item.setTipo(TipoColecao.JOGODIGITAL.getValor());

        builder.item.setId(1L);

        return builder;
    }

    public static ItemBuilder mockCollectionItemBuilder() {
        ItemBuilder builder = new ItemBuilder();
        builder.itens = new ArrayList<Item>();

        for (int i = 0; i < 10; i++) {
            Item novoItem = new Item();
            novoItem.setTitulo("Jogo " + i);
            novoItem.setEstado(EstadoItem.NOVO.getEstado());
            novoItem.setPreco(new BigDecimal(135.50));
            novoItem.setObservacoes("Imagem fisica " + i);
            novoItem.setEmprestado(false);
            novoItem.setQuantidade(1);
            novoItem.setQtdEmprestimos(1);
            novoItem.setImportancia(new Float(1.0));
            novoItem.setWishlist(true);
            novoItem.setDisponibilidade(new Date());
            novoItem.setUrl(i + "jogo.com.br");
            novoItem.setItemId(1);
            novoItem.setTipo(TipoColecao.JOGODIGITAL.getValor());

            builder.itens.add(novoItem);
        }

        return builder;
    }

    public static ItemBuilder mockItemDtoBuilder() {
        ItemBuilder builder = new ItemBuilder();
        builder.itemDto = new ItemDto();
        builder.itemDto.setTitulo("Wow");
        builder.itemDto.setEstado(EstadoItem.NOVO.getEstado());
        builder.itemDto.setPreco(new BigDecimal(135.50));
        builder.itemDto.setObservacoes("Imagem fisica");
        builder.itemDto.setEmprestado(false);
        builder.itemDto.setQuantidade(1);
        builder.itemDto.setQtdEmprestimos(1);
        builder.itemDto.setImportancia(new Float(1.0));
        builder.itemDto.setWishlist(true);
        builder.itemDto.setDisponibilidade(new Date());
        builder.itemDto.setUrl("jogo.com.br");
        builder.itemDto.setItemId(1);
        builder.itemDto.setTipo(TipoColecao.JOGODIGITAL.getValor());

        return builder;
    }

    // Methods
    public Item getItem() {
        return this.item;
    }

    public ItemDto getItemDto() {
        return this.itemDto;
    }

    public Optional<Item> getItemOptional() {
        Optional<Item> empty = Optional.of(this.item);
        return empty;
    }

    public Optional<Item> getItemEmptyOptional() {
        return Optional.empty();
    }

    public Collection<Item> getItens() {
        return this.itens;
    }
}
