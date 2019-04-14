package web.santiago.gcp.entities;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * Representa uma DlcDto que faz parte de algum JogoDigital
 * @author Santiago Brothers
 */
@Data
@javax.persistence.Entity
public class Dlc extends Entity {

    public Dlc(String localizacao) {
        this.localizacao = localizacao;
    }

    public Dlc() {
    }

    private String localizacao;

    @ManyToOne
    @JoinColumn
    private JogoDigital jogo;
}
