package web.santiago.gcp.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import web.santiago.gcp.builders.DlcBuilder;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.dtos.DlcDto;
import web.santiago.gcp.entities.Dlc;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.DlcService;
import web.santiago.gcp.services.ItemService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DlcControllerTest {

    @InjectMocks
    private DlcController dlcController;

    @Mock
    private DlcService dlcService;
    @Mock
    private ItemService itemService;

    private Model model;

    private Optional<Dlc> dlcOptional;
    private List<Dlc> dlcs;
    private DlcDto dlcDto;
    private Dlc dlc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.dlcOptional = DlcBuilder.mockDlcBuilder().getDlcOptional();
        this.dlcs = (List) DlcBuilder.mockCollectionDlcsBuilder().getDlcs();
        this.dlcDto = DlcBuilder.mockDlcDtoBuilder().getDlcDto();
        this.dlc = DlcBuilder.mockDlcBuilder().getDlc();

        this.model = new ConcurrentModel();
    }

    @Test
    public void create() {
        Assert.assertEquals(this.dlcController.create(), "dlc-save");
    }

    @Test
    public void update() {

        Mockito.when(this.dlcService.getById(1L)).thenReturn(this.dlcOptional);
        Mockito.when(this.itemService.getByItemIdAndTipo(1L, TipoColecao.DLC.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemOptional());

        Assert.assertEquals(this.dlcController.update(1L, this.model), "dlc-save");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DLC.getValor()), true);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(this.dlcService.getById(0L)).thenReturn(DlcBuilder.mockDlcBuilder().getDlcEmptyOptional());

        Assert.assertEquals(this.dlcController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DLC.getValor()), false);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), false);
    }

    @Test
    public void updateNotFoundItem() {
        Mockito.when(this.dlcService.getById(0L)).thenReturn(DlcBuilder.mockDlcBuilder().getDlcOptional());
        Mockito.when(this.itemService.getByItemIdAndTipo(0L, TipoColecao.DLC.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemEmptyOptional());

        Assert.assertEquals(this.dlcController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DLC.getValor()), true);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), false);
    }

    @Test
    public void save() {

        Mockito.when(this.dlcService.save(this.dlcDto)).thenReturn(this.dlc);
        Assert.assertEquals(this.dlcController.save(this.dlcDto), "item-index");
    }

    @Test
    public void delete() {

        Assert.assertEquals(this.dlcController.delete(1L), "item-index");
        Mockito.verify(this.dlcService, Mockito.times(1)).delete(1L);
    }
}
