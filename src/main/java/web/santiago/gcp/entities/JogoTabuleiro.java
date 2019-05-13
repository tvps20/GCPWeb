package web.santiago.gcp.entities;

import lombok.Data;

/**
 * Representa um jogo de tabuleiro
 *
 * @author Santiago Brothers
 */
@Data
@javax.persistence.Entity
public class JogoTabuleiro extends Entity {

    private String marca;

    public JogoTabuleiro(String marca) {
        this.marca = marca;
    }

    public JogoTabuleiro() {
    }
}
