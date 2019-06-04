package web.santiago.gcp.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Representa um agrupamento de items na coleção
 *
 * @author Santiago Brothers
 */
@Data
@EqualsAndHashCode(exclude = "items")
@javax.persistence.Entity
public class Saga extends Entity {

    private String titulo;

    @OneToMany(mappedBy = "saga", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Item> items;

    public Saga(String titulo) {
        this.titulo = titulo;
    }

    public Saga() {
    }

    public long wishListItem() {
        return this.items.stream().filter(item -> item.isWishlist()).count();
    }
}
