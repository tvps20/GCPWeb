package web.santiago.gcp.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representa um jogo digital ou em midia fisica
 *
 * @author Santiago Brothers
 */
@Data
@EqualsAndHashCode(exclude = "dlcs")
@javax.persistence.Entity
public class JogoDigital extends Entity {

    private String console;

    private boolean finalizado;

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Dlc> dlcs;

    public JogoDigital(String console, boolean finalizado) {
        this.console = console;
        this.finalizado = finalizado;
    }

    public JogoDigital(Dlc... dlcs) {
        this.dlcs = Stream.of(dlcs).collect(Collectors.toSet());
        this.dlcs.forEach(x -> x.setJogo(this));
    }

    public JogoDigital() {
    }
}
