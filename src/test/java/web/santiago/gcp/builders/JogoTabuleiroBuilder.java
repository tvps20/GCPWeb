package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.JogoTabuleiroDto;
import web.santiago.gcp.entities.JogoTabuleiro;
import web.santiago.gcp.enuns.EstadoItem;
import web.santiago.gcp.enuns.TipoColecao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class JogoTabuleiroBuilder {

    private JogoTabuleiro jogoTabuleiro;
    private Collection<JogoTabuleiro> jogosTabuleiros;
    private JogoTabuleiroDto jogoTabuleiroDto;

    public static JogoTabuleiroBuilder mockJogoTabuleiroBuilder() {
        JogoTabuleiroBuilder builder = new JogoTabuleiroBuilder();
        builder.jogoTabuleiro = new JogoTabuleiro("xadrez");
        builder.jogoTabuleiro.setId(1L);

        return builder;
    }

    public static JogoTabuleiroBuilder mockCollectionJogoTabuleiroBuilder() {
        JogoTabuleiroBuilder builder = new JogoTabuleiroBuilder();
        builder.jogosTabuleiros = new ArrayList<JogoTabuleiro>();

        for (int i = 0; i < 10; i++) {
            JogoTabuleiro novaJogoTabuleiro = new JogoTabuleiro("xadrez");

            builder.jogosTabuleiros.add(novaJogoTabuleiro);
        }

        return builder;
    }

    public static JogoTabuleiroBuilder mockJogoTabuleiroDtoBuilder() {
        JogoTabuleiroBuilder builder = new JogoTabuleiroBuilder();
        builder.jogoTabuleiroDto = new JogoTabuleiroDto();
        builder.jogoTabuleiroDto.setMarca("xadrez");
        builder.jogoTabuleiroDto.setTitulo("");
        builder.jogoTabuleiroDto.setEstado(EstadoItem.NOVO.getEstado());
        builder.jogoTabuleiroDto.setObservacoes("Item novo");
        builder.jogoTabuleiroDto.setEmprestado(false);
        builder.jogoTabuleiroDto.setQuantidade(1);
        builder.jogoTabuleiroDto.setQtdEmprestimos(1);
        builder.jogoTabuleiroDto.setImportancia(new Float(1.0));
        builder.jogoTabuleiroDto.setWishlist(true);
        builder.jogoTabuleiroDto.setDisponibilidade(new Date());
        builder.jogoTabuleiroDto.setUrl("jogo.com.br");
        builder.jogoTabuleiroDto.setItemId(1);
        builder.jogoTabuleiroDto.setTipo(TipoColecao.JOGOTABULEIRO.getValor());
        builder.jogoTabuleiroDto.setSagaId(1);

        return builder;
    }

    // Methods
    public JogoTabuleiro getJogoTabuleiro() {
        return this.jogoTabuleiro;
    }

    public JogoTabuleiroDto getJogoTabuleiroDto() {
        return this.jogoTabuleiroDto;
    }

    public Optional<JogoTabuleiro> getJogoTabuleiroOptional() {
        Optional<JogoTabuleiro> empty = Optional.of(this.jogoTabuleiro);
        return empty;
    }

    public Optional<JogoTabuleiro> getJogoTabuleiroEmptyOptional() {
        return Optional.empty();
    }

    public Collection<JogoTabuleiro> getJogosTabuleiros() {
        return this.jogosTabuleiros;
    }
}
