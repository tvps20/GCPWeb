package web.santiago.gcp.entities;

import lombok.Data;

/**
 * Representa Dvds, cds e outras midias
 */
@Data
@javax.persistence.Entity
public class DvdCd extends Entity {

    private String marca;
    private String conteudo;
    private boolean assistido;

    public DvdCd(String marca, String conteudo, boolean assistido) {
        this.marca = marca;
        this.conteudo = conteudo;
        this.assistido = assistido;
    }

    public DvdCd() {
    }
}
