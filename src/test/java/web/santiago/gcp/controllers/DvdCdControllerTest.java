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
import web.santiago.gcp.builders.DvdCdBuilder;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.DvdCdService;
import web.santiago.gcp.services.ItemService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DvdCdControllerTest {

    @InjectMocks
    private DvdCdController dvdCdController;

    @Mock
    private DvdCdService dvdCdService;
    @Mock
    private ItemService itemService;

    private Model model;

    private Optional<DvdCd> DvdCdOptional;
    private List<DvdCd> DvdsCds;
    private DvdCdDto DvdCdDto;
    private DvdCd DvdCd;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.DvdCdOptional = DvdCdBuilder.mockDvdCdBuilder().getDvdCdOptional();
        this.DvdsCds = (List) DvdCdBuilder.mockCollectionDvdsCdsBuilder().getDvdsCds();
        this.DvdCdDto = DvdCdBuilder.mockDvdCdDtoBuilder().getDvdCdDto();
        this.DvdCd = DvdCdBuilder.mockDvdCdBuilder().getDvdCd();

        this.model = new ConcurrentModel();
    }

    @Test
    public void create() {
        Assert.assertEquals(this.dvdCdController.create(), "dvdcd-save");
    }

    @Test
    public void update() {

        Mockito.when(this.dvdCdService.getById(1L)).thenReturn(this.DvdCdOptional);
        Mockito.when(this.itemService.getByItemIdAndTipo(1L, TipoColecao.DVDCD.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemOptional());

        Assert.assertEquals(this.dvdCdController.update(1L, this.model), "dvdcd-save");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DVDCD.getValor()), true);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(this.dvdCdService.getById(0L)).thenReturn(DvdCdBuilder.mockDvdCdBuilder().getDvdCdEmptyOptional());

        Assert.assertEquals(this.dvdCdController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DLC.getValor()), false);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), false);
    }

    @Test
    public void updateNotFoundItem() {
        Mockito.when(this.dvdCdService.getById(0L)).thenReturn(DvdCdBuilder.mockDvdCdBuilder().getDvdCdOptional());
        Mockito.when(this.itemService.getByItemIdAndTipo(0L, TipoColecao.DVDCD.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemEmptyOptional());

        Assert.assertEquals(this.dvdCdController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DVDCD.getValor()), true);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), false);
    }

    @Test
    public void save() {

        Mockito.when(this.dvdCdService.save(this.DvdCdDto)).thenReturn(this.DvdCd);
        Assert.assertEquals(this.dvdCdController.save(this.DvdCdDto), "item-index");
    }

    @Test
    public void delete() {

        Assert.assertEquals(this.dvdCdController.delete(1L), "item-index");
        Mockito.verify(this.dvdCdService, Mockito.times(1)).delete(1L);
    }
}
