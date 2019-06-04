package web.santiago.gcp.entities;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Representa uma DlcDto que faz parte de algum JogoDigital
 *
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private JogoDigital jogo;
}
