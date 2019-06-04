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
import web.santiago.gcp.builders.JogoTabuleiroBuilder;
import web.santiago.gcp.controllers.v1.JogoTabuleiroController;
import web.santiago.gcp.dtos.JogoTabuleiroDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoTabuleiro;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.JogoTabuleiroService;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JogoTabuleiroControllerTest {

    @InjectMocks
    private JogoTabuleiroController jogoTabuleiroController;

    @Mock
    private JogoTabuleiroService jogoTabuleiroService;
    @Mock
    private ItemService itemService;
    @Mock
    private BindingResult bindingResult;

    private Model model;

    private Optional<JogoTabuleiro> jogoTabuleiroOptional;
    private List<JogoTabuleiro> jogosTabuleiros;
    private JogoTabuleiroDto jogoTabuleiroDto;
    private JogoTabuleiro jogoTabuleiro;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.jogoTabuleiroOptional = JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiroOptional();
        this.jogosTabuleiros = (List<JogoTabuleiro>) JogoTabuleiroBuilder.mockCollectionJogoTabuleiroBuilder().getJogosTabuleiros();
        this.jogoTabuleiroDto = JogoTabuleiroBuilder.mockJogoTabuleiroDtoBuilder().getJogoTabuleiroDto();
        this.jogoTabuleiro = JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiro();

        this.model = new ConcurrentModel();
    }

//    @Test
//    public void create() {
//        Assert.assertEquals(this.jogoTabuleiroController.create(this.model), "jogotabuleiro/jogotabuleiro-save");
//    }
//
//    @Test
//    public void update() {
//        Optional<Item> item = ItemBuilder.mockItemBuilder().getItemOptional();
//        Mockito.when(this.jogoTabuleiroService.getById(1L)).thenReturn(this.jogoTabuleiroOptional);
//        Mockito.when(this.itemService.getByItemIdAndTipo(1L, TipoColecao.JOGOTABULEIRO.getValor())).thenReturn(item);
//        Mockito.when(this.jogoTabuleiroService.createDtoFromItemJogoTabuleiro(item.get(), this.jogoTabuleiroOptional.get())).thenReturn(this.jogoTabuleiroDto);
//
//        Assert.assertEquals(this.jogoTabuleiroController.update(1L, this.model), "jogotabuleiro/jogotabuleiro-save");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGOTABULEIRO.getValor()), true);
//    }
//
//    @Test
//    public void updateNotFound() {
//        Mockito.when(this.jogoTabuleiroService.getById(0L)).thenReturn(JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiroEmptyOptional());
//
//        Assert.assertEquals(this.jogoTabuleiroController.update(0L, this.model), "not-found");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGOTABULEIRO.getValor()), false);
//    }
//
//    @Test
//    public void updateNotFoundItem() {
//        Mockito.when(this.jogoTabuleiroService.getById(0L)).thenReturn(JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiroOptional());
//        Mockito.when(this.itemService.getByItemIdAndTipo(0L, TipoColecao.JOGOTABULEIRO.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemEmptyOptional());
//
//        Assert.assertEquals(this.jogoTabuleiroController.update(0L, this.model), "not-found");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGOTABULEIRO.getValor()), false);
//    }
//
//    @Test
//    public void save() {
//        Mockito.when(this.jogoTabuleiroService.save(this.jogoTabuleiroDto)).thenReturn(this.jogoTabuleiro);
//        this.jogoTabuleiroDto.setItemId(0L);
//        Assert.assertEquals(this.jogoTabuleiroController.save(this.jogoTabuleiroDto, this.bindingResult), "redirect:/item");
//    }
//
//    @Test
//    public void saveError() {
//        Mockito.when(this.jogoTabuleiroService.save(this.jogoTabuleiroDto)).thenReturn(this.jogoTabuleiro);
//        Mockito.when((this.bindingResult.hasErrors())).thenReturn(true);
//        Assert.assertEquals(this.jogoTabuleiroController.save(this.jogoTabuleiroDto, this.bindingResult), "jogotabuleiro/jogotabuleiro-save");
//    }
//
//    @Test
//    public void saveIdDiferenteZero() {
//        Mockito.when(this.jogoTabuleiroService.save(this.jogoTabuleiroDto)).thenReturn(this.jogoTabuleiro);
//        this.jogoTabuleiroDto.setId(1L);
//        this.jogoTabuleiroDto.setItemId(1L);
//        Assert.assertEquals(this.jogoTabuleiroController.save(this.jogoTabuleiroDto, this.bindingResult), "redirect:/item");
//    }

    @Test
    public void delete() {
        Assert.assertEquals(this.jogoTabuleiroController.delete(1L), "redirect:/item");
        Mockito.verify(this.jogoTabuleiroService, Mockito.times(1)).delete(1L);
    }
}
