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
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.builders.JogoDigitalBuilder;
import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.JogoDigitalService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JogoDigitalControllerTest {

    @InjectMocks
    private JogoDigitalController jogoDigitalController;

    @Mock
    private JogoDigitalService jogoDigitalService;
    @Mock
    private ItemService itemService;
    @Mock
    private BindingResult bindingResult;

    private Model model;

    private Optional<JogoDigital> jogoDigitalOptional;
    private List<JogoDigital> jogosDigitais;
    private JogoDigitalDto jogoDigitalDto;
    private JogoDigital jogoDigital;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.jogoDigitalOptional = JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigitalOptional();
        this.jogosDigitais = (List<JogoDigital>) JogoDigitalBuilder.mockCollectionJogoDigitalBuilder().getJogoDigital();
        this.jogoDigitalDto = JogoDigitalBuilder.mockJogoDigitalDtoBuilder().getJogoDigitalDto();
        this.jogoDigital = JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigital();

        this.model = new ConcurrentModel();
    }

    @Test
    public void create() {
        Assert.assertEquals(this.jogoDigitalController.create(this.model), "jogodigital/jogodigital-save");
    }

    @Test
    public void update() {
        Optional<Item> item = ItemBuilder.mockItemBuilder().getItemOptional();
        Mockito.when(this.jogoDigitalService.getById(1L)).thenReturn(this.jogoDigitalOptional);
        Mockito.when(this.itemService.getByItemIdAndTipo(1L, TipoColecao.JOGODIGITAL.getValor())).thenReturn(item);
        Mockito.when(this.jogoDigitalService.createDtoFromItemJogoDigital(item.get(), this.jogoDigitalOptional.get())).thenReturn(this.jogoDigitalDto);

        Assert.assertEquals(this.jogoDigitalController.update(1L, this.model), "jogodigital/jogodigital-save");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGODIGITAL.getValor()), true);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(this.jogoDigitalService.getById(0L)).thenReturn(JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigitalEmptyOptional());

        Assert.assertEquals(this.jogoDigitalController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGODIGITAL.getValor()), false);
    }

    @Test
    public void updateNotFoundItem() {
        Mockito.when(this.jogoDigitalService.getById(0L)).thenReturn(JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigitalOptional());
        Mockito.when(this.itemService.getByItemIdAndTipo(0L, TipoColecao.JOGODIGITAL.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemEmptyOptional());

        Assert.assertEquals(this.jogoDigitalController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGODIGITAL.getValor()), false);
    }

    @Test
    public void save() {
        Mockito.when(this.jogoDigitalService.save(this.jogoDigitalDto)).thenReturn(this.jogoDigital);
        this.jogoDigitalDto.setItemId(0L);
        Assert.assertEquals(this.jogoDigitalController.save(this.jogoDigitalDto, this.bindingResult), "redirect:/item");
    }

    @Test
    public void saveError() {
        Mockito.when(this.jogoDigitalService.save(this.jogoDigitalDto)).thenReturn(this.jogoDigital);
        Mockito.when((this.bindingResult.hasErrors())).thenReturn(true);
        Assert.assertEquals(this.jogoDigitalController.save(this.jogoDigitalDto, this.bindingResult), "jogodigital/jogodigital-save");
    }

    @Test
    public void saveIdDiferenteZero() {
        Mockito.when(this.jogoDigitalService.save(this.jogoDigitalDto)).thenReturn(this.jogoDigital);
        this.jogoDigitalDto.setId(1L);
        this.jogoDigitalDto.setItemId(1L);
        Assert.assertEquals(this.jogoDigitalController.save(this.jogoDigitalDto, this.bindingResult), "redirect:/item");
    }

    @Test
    public void delete() {
        Assert.assertEquals(this.jogoDigitalController.delete(1L), "redirect:/item");
        Mockito.verify(this.jogoDigitalService, Mockito.times(1)).delete(1L);
    }
}
