package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.HqDto;
import web.santiago.gcp.entities.Hq;
import web.santiago.gcp.enuns.EstadoItem;
import web.santiago.gcp.enuns.TipoColecao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class HqBuilder {

    private Hq hq;
    private Collection<Hq> hqs;
    private HqDto hqDto;

    public static HqBuilder mockHqBuilder() {
        HqBuilder builder = new HqBuilder();
        builder.hq = new Hq(1, "DC", "Novos 52", "Das infinitas eras");
        builder.hq.setId(1L);

        return builder;
    }

    public static HqBuilder mockCollectionHqBuilder() {
        HqBuilder builder = new HqBuilder();
        builder.hqs = new ArrayList<Hq>();

        for (int i = 0; i < 10; i++) {
            Hq novaHq = new Hq(i, "DC", "Novos 52", "Das infinitas eras");

            builder.hqs.add(novaHq);
        }

        return builder;
    }

    public static HqBuilder mockHqDtoBuilder() {
        HqBuilder builder = new HqBuilder();
        builder.hqDto = new HqDto();
        builder.hqDto.setNumero(1);
        builder.hqDto.setEditora("DC");
        builder.hqDto.setSaga("Das infinitas eras");
        builder.hqDto.setUniverso("Novos 52");
        builder.hqDto.setTitulo("");
        builder.hqDto.setEstado(EstadoItem.NOVO.getEstado());
        builder.hqDto.setObservacoes("Item novo");
        builder.hqDto.setEmprestado(false);
        builder.hqDto.setQuantidade(1);
        builder.hqDto.setQtdEmprestimos(1);
        builder.hqDto.setImportancia(new Float(1.0));
        builder.hqDto.setWishlist(true);
        builder.hqDto.setDisponibilidade(new Date());
        builder.hqDto.setUrl("jogo.com.br");
        builder.hqDto.setItemId(1);
        builder.hqDto.setTipo(TipoColecao.HQ.getValor());
        builder.hqDto.setSagaId(1);

        return builder;
    }

    // Methods
    public Hq getHq() {
        return this.hq;
    }

    public HqDto getHqDto() {
        return this.hqDto;
    }

    public Optional<Hq> getHqOptional() {
        Optional<Hq> empty = Optional.of(this.hq);
        return empty;
    }

    public Optional<Hq> getHqEmptyOptional() {
        return Optional.empty();
    }

    public Collection<Hq> getHqs() {
        return this.hqs;
    }
}
