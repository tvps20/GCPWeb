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
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.builders.JogoTabuleiroBuilder;
import web.santiago.gcp.dtos.JogoTabuleiroDto;
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

    private Model model;

    private Optional<JogoTabuleiro> jogoTabuleiroOptional;
    private List<JogoTabuleiro> jogosTabuleiros;
    private JogoTabuleiroDto jogoTabuleiroDto;
    private JogoTabuleiro jogoTabuleiro;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.jogoTabuleiroOptional = JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiroOptional();
        this.jogosTabuleiros = (List) JogoTabuleiroBuilder.mockCollectionJogoTabuleiroBuilder().getJogosTabuleiros();
        this.jogoTabuleiroDto = JogoTabuleiroBuilder.mockJogoTabuleiroDtoBuilder().getJogoTabuleiroDto();
        this.jogoTabuleiro = JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiro();

        this.model = new ConcurrentModel();
    }

    @Test
    public void create() {
        Assert.assertEquals(this.jogoTabuleiroController.create(), "jogotabuleiro-save");
    }

    @Test
    public void update() {

        Mockito.when(this.jogoTabuleiroService.getById(1L)).thenReturn(this.jogoTabuleiroOptional);
        Mockito.when(this.itemService.getByItemIdAndTipo(1L, TipoColecao.JOGOTABULEIRO.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemOptional());

        Assert.assertEquals(this.jogoTabuleiroController.update(1L, this.model), "jogotabuleiro-save");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGOTABULEIRO.getValor()), true);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(this.jogoTabuleiroService.getById(0L)).thenReturn(JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiroEmptyOptional());

        Assert.assertEquals(this.jogoTabuleiroController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGOTABULEIRO.getValor()), false);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), false);
    }

    @Test
    public void updateNotFoundItem() {
        Mockito.when(this.jogoTabuleiroService.getById(0L)).thenReturn(JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiroOptional());
        Mockito.when(this.itemService.getByItemIdAndTipo(0L, TipoColecao.JOGOTABULEIRO.getValor())).thenReturn(ItemBuilder.mockItemBuilder().getItemEmptyOptional());

        Assert.assertEquals(this.jogoTabuleiroController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.JOGOTABULEIRO.getValor()), true);
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), false);
    }

    @Test
    public void save() {

        Mockito.when(this.jogoTabuleiroService.save(this.jogoTabuleiroDto)).thenReturn(this.jogoTabuleiro);
        Assert.assertEquals(this.jogoTabuleiroController.save(this.jogoTabuleiroDto), "item-index");
    }

    @Test
    public void delete() {

        Assert.assertEquals(this.jogoTabuleiroController.delete(1L), "item-index");
        Mockito.verify(this.jogoTabuleiroService, Mockito.times(1)).delete(1L);
    }
}
