package web.santiago.gcp.entities;

import lombok.Data;

/**
 * Reprenseta uma graphic novel, HQ, historia em quadrinho ou revistas
 *
 * @author Santiago Brothers
 */
@Data
@javax.persistence.Entity
public class Hq extends Entity {

    private int numero;
    private String editora;
    private String universo;
    private String saga;

    public Hq(int numero, String editora, String universo, String saga) {
        this.numero = numero;
        this.editora = editora;
        this.universo = universo;
        this.saga = saga;
    }

    public Hq() {
    }
}
