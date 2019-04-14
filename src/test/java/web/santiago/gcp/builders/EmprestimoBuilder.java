package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.EmprestimoDto;
import web.santiago.gcp.entities.Emprestimo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class EmprestimoBuilder {

    private Emprestimo emprestimo;
    private Collection<Emprestimo> emprestimos;
    private EmprestimoDto emprestimoDto;

    public static EmprestimoBuilder mockEmprestimoBuilder() {
        EmprestimoBuilder builder = new EmprestimoBuilder();
        builder.emprestimo = new Emprestimo(new Date(), false);
        builder.emprestimo.setId(1L);

        return builder;
    }

    public static EmprestimoBuilder mockCollectionEmprestimoBuilder() {
        EmprestimoBuilder builder = new EmprestimoBuilder();
        builder.emprestimos = new ArrayList<Emprestimo>();

        for (int i = 0; i < 10; i++) {
            Emprestimo novoEmprestimo = new Emprestimo(new Date(), false);

            builder.emprestimos.add(novoEmprestimo);
        }

        return builder;
    }

    public static EmprestimoBuilder mockEmprestimoDtoBuilder() {
        EmprestimoBuilder builder = new EmprestimoBuilder();
        builder.emprestimoDto = new EmprestimoDto();
        builder.emprestimoDto.setAmigo(1L);
        builder.emprestimoDto.setDevolucao(new Date());
        builder.emprestimoDto.setDevolvido(false);

        return builder;
    }

    // Methods
    public Emprestimo getEmprestimo() {
        return this.emprestimo;
    }

    public EmprestimoDto getEmpretimoDto() {
        return this.emprestimoDto;
    }

    public Optional<Emprestimo> getEmprestimoOptional() {
        Optional<Emprestimo> empty = Optional.of(this.emprestimo);
        return empty;
    }

    public Optional<Emprestimo> getEmprestimoEmptyOptional() {
        return Optional.empty();
    }

    public Collection<Emprestimo> getEmprestimos() {
        return this.emprestimos;
    }
}
