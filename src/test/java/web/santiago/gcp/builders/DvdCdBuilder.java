package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.enuns.EstadoItem;
import web.santiago.gcp.enuns.TipoColecao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class DvdCdBuilder {

    private DvdCd dvdCd;
    private Collection<DvdCd> dvdsCds;
    private DvdCdDto dvdCdDto;

    public static DvdCdBuilder mockDvdCdBuilder() {
        DvdCdBuilder builder = new DvdCdBuilder();
        builder.dvdCd = new DvdCd("console", "Filme", true);
        builder.dvdCd.setId(1L);

        return builder;
    }

    public static DvdCdBuilder mockCollectionDvdsCdsBuilder() {
        DvdCdBuilder builder = new DvdCdBuilder();
        builder.dvdsCds = new ArrayList<DvdCd>();

        for (int i = 0; i < 10; i++) {
            DvdCd novoDvdCd = new DvdCd("console " + i, "Filme " + i, true);

            builder.dvdsCds.add(novoDvdCd);
        }

        return builder;
    }

    public static DvdCdBuilder mockDvdCdDtoBuilder() {
        DvdCdBuilder builder = new DvdCdBuilder();
        builder.dvdCdDto = new DvdCdDto();
        builder.dvdCdDto.setMarca("console");
        builder.dvdCdDto.setConteudo("Filme");
        builder.dvdCdDto.setAssistido(true);
        builder.dvdCdDto.setTitulo("");
        builder.dvdCdDto.setEstado(EstadoItem.NOVO.getEstado());
        builder.dvdCdDto.setObservacoes("Item novo");
        builder.dvdCdDto.setEmprestado(false);
        builder.dvdCdDto.setQuantidade(1);
        builder.dvdCdDto.setQtdEmprestimos(1);
        builder.dvdCdDto.setImportancia(new Float(1.0));
        builder.dvdCdDto.setWishlist(true);
        builder.dvdCdDto.setDisponibilidade(new Date());
        builder.dvdCdDto.setUrl("jogo.com.br");
        builder.dvdCdDto.setItemId(1);
        builder.dvdCdDto.setTipo(TipoColecao.DVDCD.getValor());
        builder.dvdCdDto.setSagaId(1);

        return builder;
    }

    // Methods
    public DvdCd getDvdCd() {
        return this.dvdCd;
    }

    public DvdCdDto getDvdCdDto() {
        return this.dvdCdDto;
    }

    public Optional<DvdCd> getDvdCdOptional() {
        Optional<DvdCd> empty = Optional.of(this.dvdCd);
        return empty;
    }

    public Optional<DvdCd> getDvdCdEmptyOptional() {
        return Optional.empty();
    }

    public Collection<DvdCd> getDvdsCds() {
        return this.dvdsCds;
    }
}
