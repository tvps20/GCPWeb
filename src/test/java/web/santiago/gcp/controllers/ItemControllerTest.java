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
import web.santiago.gcp.controllers.v1.ItemController;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;
    @Mock
    private DlcService dlcService;
    @Mock
    private DvdCdService dvdCdService;
    @Mock
    private HqService hqService;
    @Mock
    private JogoDigitalService jogoDigitalService;
    @Mock
    private JogoTabuleiroService jogoTabuleiroService;

    private Model model;

    private List<Item> itens;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.itens = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();

        this.model = new ConcurrentModel();
    }

    @Test
    public void index() {
        Mockito.when(this.itemService.getAll()).thenReturn(this.itens);

//        Assert.assertEquals(this.itemController.index(this.model, null, null, null, false, false, null, false, null, null, null, null), "item-index");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void indexDLC() {
        Mockito.when(this.dlcService.getAllByLocalizacao(null)).thenReturn(new ArrayList());

//        Assert.assertEquals(this.itemController.index(this.model, null, TipoColecao.DLC.getValor(), null, false, false, null, false, null, null, null, null), "item-index");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void indexDVDCD() {
        Mockito.when(this.dvdCdService.getAllByAssistidos(true)).thenReturn(new ArrayList());

//        Assert.assertEquals(this.itemController.index(this.model, null, TipoColecao.DVDCD.getValor(), null, false, false, null, false, null, null, null, null), "item-index");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void indexHQ() {
        Mockito.when(this.hqService.getAllByEditoraAndUniverso(null, null)).thenReturn(new ArrayList());

//        Assert.assertEquals(this.itemController.index(this.model, null, TipoColecao.HQ.getValor(), null, false, false, null, false, null, null, null, null), "item-index");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void indexJogoDigital() {
        Mockito.when(this.jogoDigitalService.getAllByConsole(null)).thenReturn(new ArrayList());

//        Assert.assertEquals(this.itemController.index(this.model, null, TipoColecao.JOGODIGITAL.getValor(), null, false, false, null, false, null, null, null, null), "item-index");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

    @Test
    public void indexJogoTabuleiro() {
        Mockito.when(this.jogoTabuleiroService.getAllByMarca(null)).thenReturn(new ArrayList());

//        Assert.assertEquals(this.itemController.index(this.model, null, TipoColecao.JOGOTABULEIRO.getValor(), null, false, false, null, false, null, null, null, null), "item-index");
//        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }
}
