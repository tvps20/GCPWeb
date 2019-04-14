package web.santiago.gcp.entities;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa um agrupamento de items na coleção
 * @author Santiago Brothers
 */
@Data
@EqualsAndHashCode(exclude = "items")
@javax.persistence.Entity
public class Saga extends Entity {

    private String titulo;

    public Saga(String titulo) {
        this.titulo = titulo;
    }

    public Saga() {
    }

    @OneToMany(mappedBy = "saga", fetch = FetchType.EAGER)
    private List<Item> items;
}
