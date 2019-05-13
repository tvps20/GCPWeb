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
import org.springframework.validation.BindingResult;
import web.santiago.gcp.builders.DvdCdBuilder;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.entities.Item;
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
    @Mock
    private BindingResult bindingResult;

    private Model model;

    private Optional<DvdCd> DvdCdOptional;
    private List<DvdCd> DvdsCds;
    private DvdCdDto DvdCdDto;
    private DvdCd DvdCd;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.DvdCdOptional = DvdCdBuilder.mockDvdCdBuilder().getDvdCdOptional();
        this.DvdsCds = (List<DvdCd>) DvdCdBuilder.mockCollectionDvdsCdsBuilder().getDvdsCds();
        this.DvdCdDto = DvdCdBuilder.mockDvdCdDtoBuilder().getDvdCdDto();
        this.DvdCd = DvdCdBuilder.mockDvdCdBuilder().getDvdCd();

        this.model = new ConcurrentModel();
    }

    @Test
    public void create() {
        Assert.assertEquals(this.dvdCdController.create(this.model), "dvdcd/dvdcd-save");
    }

    @Test
    public void update() {
        Optional<Item> item = ItemBuilder.mockItemBuilder().getItemOptional();
        Mockito.when(this.dvdCdService.getById(1L)).thenReturn(this.DvdCdOptional);
        Mockito.when(this.itemService.getByItemIdAndTipo(1L, TipoColecao.DVDCD.getValor())).thenReturn(item);
        Mockito.when(this.dvdCdService.createDtoFromItemDvdCd(item.get(), this.DvdCdOptional.get())).thenReturn(this.DvdCdDto);

        Assert.assertEquals(this.dvdCdController.update(1L, this.model), "dvdcd/dvdcd-save");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DVDCD.getValor()), true);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(this.dvdCdService.getById(0L)).thenReturn(DvdCdBuilder.mockDvdCdBuilder().getDvdCdEmptyOptional());

        Assert.assertEquals(this.dvdCdController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DLC.getValor()), false);
    }

    @Test
    public void updateNotFoundItem() {
        Mockito.when(this.dvdCdService.getById(0L)).thenReturn(DvdCdBuilder.mockDvdCdBuilder().getDvdCdOptional());
        Mockito.when(this.itemService.getByItemIdAndTipo(0L, TipoColecao.DVDCD.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemEmptyOptional());

        Assert.assertEquals(this.dvdCdController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.DVDCD.getValor()), false);
    }

    @Test
    public void save() {
        Mockito.when(this.dvdCdService.save(this.DvdCdDto)).thenReturn(this.DvdCd);
        this.DvdCdDto.setItemId(0L);
        Assert.assertEquals(this.dvdCdController.save(this.DvdCdDto, this.bindingResult), "redirect:/item");
    }

    @Test
    public void saveError() {
        Mockito.when(this.dvdCdService.save(this.DvdCdDto)).thenReturn(this.DvdCd);
        Mockito.when((this.bindingResult.hasErrors())).thenReturn(true);
        Assert.assertEquals(this.dvdCdController.save(this.DvdCdDto, this.bindingResult), "dvdcd/dvdcd-save");
    }

    @Test
    public void saveIdDiferenteZero() {
        Mockito.when(this.dvdCdService.save(this.DvdCdDto)).thenReturn(this.DvdCd);
        this.DvdCdDto.setId(1L);
        this.DvdCdDto.setItemId(1L);
        Assert.assertEquals(this.dvdCdController.save(this.DvdCdDto, this.bindingResult), "redirect:/item");
    }

    @Test
    public void delete() {
        Assert.assertEquals(this.dvdCdController.delete(1L), "redirect:/item");
        Mockito.verify(this.dvdCdService, Mockito.times(1)).delete(1L);
    }
}
