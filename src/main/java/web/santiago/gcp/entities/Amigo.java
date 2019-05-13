package web.santiago.gcp.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representa um Amigo
 *
 * @author Santiago Brothers
 */
@Data
@EqualsAndHashCode(exclude = "emprestimos")
@javax.persistence.Entity
public class Amigo extends Entity {

    private String nome;
    private String sexo;
    private String parentesco;
    private String endereco;
    private String telefone;

    @OneToMany(mappedBy = "amigo", cascade = CascadeType.ALL)
    private Set<Emprestimo> emprestimos;

    public Amigo(
            String nome,
            String sexo,
            String parentesco,
            String endereco,
            String telefone,
            Emprestimo... emprestimos) {
        this.emprestimos = Stream.of(emprestimos).collect(Collectors.toSet());
        this.emprestimos.forEach(x -> x.setAmigo(this));

        this.nome = nome;
        this.sexo = sexo;
        this.parentesco = parentesco;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Amigo() {
    }
}
