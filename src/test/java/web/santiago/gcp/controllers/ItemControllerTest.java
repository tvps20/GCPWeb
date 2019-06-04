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
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.ItemService;

import java.util.List;

@SpringBootTest
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    private Model model;

    private List<Item> itens;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.itens = (List) ItemBuilder.mockCollectionItemBuilder().getItens();

        this.model = new ConcurrentModel();
    }

    @Test
    public void index() {

        Mockito.when(this.itemService.getAll()).thenReturn(this.itens);

        Assert.assertEquals(this.itemController.index(this.model), "item-index");
        Assert.assertEquals(this.model.containsAttribute(TipoColecao.ITEM.getValor()), true);
    }

}
