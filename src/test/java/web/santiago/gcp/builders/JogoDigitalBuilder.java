package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.enuns.EstadoItem;
import web.santiago.gcp.enuns.TipoColecao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class JogoDigitalBuilder {

    private JogoDigital jogoDigital;
    private Collection<JogoDigital> jogosDigitais;
    private JogoDigitalDto jogoDigitalDto;

    public static JogoDigitalBuilder mockJogoDigitalBuilder() {
        JogoDigitalBuilder builder = new JogoDigitalBuilder();
        builder.jogoDigital = new JogoDigital("Xbox", true);
        builder.jogoDigital.setId(1L);

        return builder;
    }

    public static JogoDigitalBuilder mockCollectionJogoDigitalBuilder() {
        JogoDigitalBuilder builder = new JogoDigitalBuilder();
        builder.jogosDigitais = new ArrayList<JogoDigital>();

        for (int i = 0; i < 10; i++) {
            JogoDigital novaJogoDigital = new JogoDigital("Xbox", true);

            builder.jogosDigitais.add(novaJogoDigital);
        }

        return builder;
    }

    public static JogoDigitalBuilder mockJogoDigitalDtoBuilder() {
        JogoDigitalBuilder builder = new JogoDigitalBuilder();
        builder.jogoDigitalDto = new JogoDigitalDto();
        builder.jogoDigitalDto.setConsole("Xbox");
        builder.jogoDigitalDto.setFinalizado(true);
        builder.jogoDigitalDto.setTitulo("");
        builder.jogoDigitalDto.setEstado(EstadoItem.NOVO.getEstado());
        builder.jogoDigitalDto.setObservacoes("Item novo");
        builder.jogoDigitalDto.setEmprestado(false);
        builder.jogoDigitalDto.setQuantidade(1);
        builder.jogoDigitalDto.setQtdEmprestimos(1);
        builder.jogoDigitalDto.setImportancia(new Float(1.0));
        builder.jogoDigitalDto.setWishlist(true);
        builder.jogoDigitalDto.setDisponibilidade(new Date());
        builder.jogoDigitalDto.setUrl("jogo.com.br");
        builder.jogoDigitalDto.setItemId(1);
        builder.jogoDigitalDto.setTipo(TipoColecao.JOGODIGITAL.getValor());
        builder.jogoDigitalDto.setSagaId(1);

        return builder;
    }

    // Methods
    public JogoDigital getJogoDigital() {
        return this.jogoDigital;
    }

    public JogoDigitalDto getJogoDigitalDto() {
        return this.jogoDigitalDto;
    }

    public Optional<JogoDigital> getJogoDigitalOptional() {
        Optional<JogoDigital> empty = Optional.of(this.jogoDigital);
        return empty;
    }

    public Optional<JogoDigital> getJogoDigitalEmptyOptional() {
        return Optional.empty();
    }

    public Collection<JogoDigital> getJogosDigitais() {
        return this.jogosDigitais;
    }
}
