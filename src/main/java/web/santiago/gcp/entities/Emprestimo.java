package web.santiago.gcp.entities;

import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Representa um Item emprestado a um Amigo
 */
@Data
@javax.persistence.Entity
public class Emprestimo extends Entity {

    @ManyToOne
    @JoinColumn
    private Item item;

    @ManyToOne
    @JoinColumn
    private Amigo amigo;

    private Date devolucao;
    private boolean devolvido;

    public Emprestimo(Date devolucao, boolean devolvido) {
        this.devolucao = devolucao;
        this.devolvido = devolvido;
    }

    public Emprestimo() {
    }
}
