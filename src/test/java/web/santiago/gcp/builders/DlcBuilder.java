package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.DlcDto;
import web.santiago.gcp.entities.Dlc;
import web.santiago.gcp.enuns.EstadoItem;
import web.santiago.gcp.enuns.TipoColecao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class DlcBuilder {

    private Dlc dlc;
    private Collection<Dlc> dlcs;
    private DlcDto dlcDto;

    public static DlcBuilder mockDlcBuilder() {
        DlcBuilder builder = new DlcBuilder();
        builder.dlc = new Dlc("Console");
        builder.dlc.setId(1L);

        return builder;
    }

    public static DlcBuilder mockCollectionDlcsBuilder() {
        DlcBuilder builder = new DlcBuilder();
        builder.dlcs = new ArrayList<Dlc>();

        for (int i = 0; i < 10; i++) {
            Dlc novaDlc = new Dlc("Amigo " + i);

            builder.dlcs.add(novaDlc);
        }

        return builder;
    }

    public static DlcBuilder mockDlcDtoBuilder() {
        DlcBuilder builder = new DlcBuilder();
        builder.dlcDto = new DlcDto();
        builder.dlcDto.setTipo("dlc");
        builder.dlcDto.setItemId(1L);
        builder.dlcDto.setJogo(1L);
        builder.dlcDto.setLocalizacao("Console");
        builder.dlcDto.setTitulo("");
        builder.dlcDto.setEstado(EstadoItem.NOVO.getEstado());
        builder.dlcDto.setObservacoes("Item novo");
        builder.dlcDto.setEmprestado(false);
        builder.dlcDto.setQuantidade(1);
        builder.dlcDto.setQtdEmprestimos(1);
        builder.dlcDto.setImportancia(new Float(1.0));
        builder.dlcDto.setWishlist(true);
        builder.dlcDto.setDisponibilidade(new Date());
        builder.dlcDto.setUrl("jogo.com.br");
        builder.dlcDto.setItemId(1);
        builder.dlcDto.setTipo(TipoColecao.DLC.getValor());
        builder.dlcDto.setSagaId(1);

        return builder;
    }

    // Methods
    public Dlc getDlc() {
        return this.dlc;
    }

    public DlcDto getDlcDto() {
        return this.dlcDto;
    }

    public Optional<Dlc> getDlcOptional() {
        Optional<Dlc> empty = Optional.of(this.dlc);
        return empty;
    }

    public Optional<Dlc> getDlcEmptyOptional() {
        return Optional.empty();
    }

    public Collection<Dlc> getDlcs() {
        return this.dlcs;
    }

}
