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
import web.santiago.gcp.builders.AmigoBuilder;
import web.santiago.gcp.controllers.v1.AmigoController;
import web.santiago.gcp.dtos.AmigoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.services.AmigoService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AmigoControllerTest {

    @InjectMocks
    private AmigoController amigoController;

    @Mock
    private AmigoService amigoService;
    @Mock
    private BindingResult bindingResult;

    private Model model;

    private Optional<Amigo> amigoOptional;
    private List<Amigo> amigos;
    private AmigoDto amigoDto;
    private Amigo amigo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.amigoOptional = AmigoBuilder.mockAmigoBuilder().getAmigoOptional();
        this.amigos = (List<Amigo>) AmigoBuilder.mockCollectionAmigosBuilder().getAmigos();
        this.amigoDto = AmigoBuilder.mockAmigoDtoBuilder().getAmigoDto();
        this.amigo = AmigoBuilder.mockAmigoBuilder().getAmigo();

        this.model = new ConcurrentModel();
    }

    @Test
    public void index() {
        Mockito.when(this.amigoService.getAll()).thenReturn(this.amigos);
//
//        Assert.assertEquals(this.amigoController.index(this.model), "amigo/amigo-index");
//        Assert.assertEquals(this.model.containsAttribute("amigos"), true);
    }

    @Test
    public void create() {
//        Assert.assertEquals(this.amigoController.create(this.model), "amigo/amigo-save");
    }

    @Test
    public void update() {
        Mockito.when(this.amigoService.getById(1L)).thenReturn(this.amigoOptional);
//
//        Assert.assertEquals(this.amigoController.update(1L, this.model), "amigo/amigo-save");
//        Assert.assertEquals(this.model.containsAttribute("amigo"), true);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(this.amigoService.getById(0L)).thenReturn(AmigoBuilder.mockAmigoBuilder().getAmigoEmptyOptional());
//
//        Assert.assertEquals(this.amigoController.update(0L, this.model), "not-found");
//        Assert.assertEquals(this.model.containsAttribute("amigo"), false);
    }

    @Test
    public void save() {
        Mockito.when(this.amigoService.save(this.amigoDto)).thenReturn(this.amigo);
//        Assert.assertEquals(this.amigoController.save(this.amigoDto, this.bindingResult), "redirect:/amigo");
    }

    @Test
    public void saveError() {
        Mockito.when(this.amigoService.save(this.amigoDto)).thenReturn(this.amigo);
        Mockito.when(this.bindingResult.hasErrors()).thenReturn(true);
//        Assert.assertEquals(this.amigoController.save(this.amigoDto, this.bindingResult), "amigo/amigo-save");
    }

    @Test
    public void saveIdDiferenteZero() {
        Mockito.when(this.amigoService.save(this.amigoDto)).thenReturn(this.amigo);
        this.amigoDto.setId(1L);
//        Assert.assertEquals(this.amigoController.save(this.amigoDto, this.bindingResult), "redirect:/amigo");
    }

    @Test
    public void delete() {
//        Assert.assertEquals(this.amigoController.delete(1L), "redirect:/amigo");
//        Mockito.verify(this.amigoService, Mockito.times(1)).delete(1L);
    }
}
