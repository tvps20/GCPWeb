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
import web.santiago.gcp.builders.HqBuilder;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.dtos.HqDto;
import web.santiago.gcp.entities.Hq;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.HqService;
import web.santiago.gcp.services.ItemService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class HqControllerTest {

    @InjectMocks
    private HqController hqController;

    @Mock
    private HqService hqService;
    @Mock
    private ItemService itemService;

    private Model model;

    private Optional<Hq> hqOptional;
    private List<Hq> hqs;
    private HqDto hqDto;
    private Hq hq;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.hqOptional = HqBuilder.mockHqBuilder().getHqOptional();
        this.hqs = (List) HqBuilder.mockCollectionHqBuilder().getHqs();
        this.hqDto = HqBuilder.mockHqDtoBuilder().getHqDto();
        this.hq = HqBuilder.mockHqBuilder().getHq();

        this.model = new ConcurrentModel();
    }

    @Test
    public void create() {
        Assert.assertEquals(this.hqController.create(), "hq-save");
    }

    @Test
    public void update() {

        Mockito.when(this.hqService.getById(1L)).thenReturn(this.hqOptional);
        Mockito.when(this.itemService.getByItemIdAndTipo(1L, TipoColecao.HQ.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemOptional());

        Assert.assertEquals(this.hqController.update(1L, this.model), "hq-save");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.HQ.getValor()), true);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(this.hqService.getById(0L)).thenReturn(HqBuilder.mockHqBuilder().getHqEmptyOptional());

        Assert.assertEquals(this.hqController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DLC.getValor()), false);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), false);
    }

    @Test
    public void updateNotFoundItem() {
        Mockito.when(this.hqService.getById(0L)).thenReturn(HqBuilder.mockHqBuilder().getHqOptional());
        Mockito.when(this.itemService.getByItemIdAndTipo(0L, TipoColecao.HQ.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemEmptyOptional());

        Assert.assertEquals(this.hqController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.HQ.getValor()), true);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), false);
    }

    @Test
    public void save() {

        Mockito.when(this.hqService.save(this.hqDto)).thenReturn(this.hq);
        Assert.assertEquals(this.hqController.save(this.hqDto), "item-index");
    }

    @Test
    public void delete() {

        Assert.assertEquals(this.hqController.delete(1L), "item-index");
        Mockito.verify(this.hqService, Mockito.times(1)).delete(1L);
    }
}
