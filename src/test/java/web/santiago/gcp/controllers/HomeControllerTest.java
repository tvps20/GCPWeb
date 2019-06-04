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
import web.santiago.gcp.builders.EmprestimoBuilder;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.entities.Emprestimo;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.services.EmprestimoService;
import web.santiago.gcp.services.ItemService;

import java.util.List;

@SpringBootTest
public class HomeControllerTest {

//    @InjectMocks
//    private HomeController homeController;
//    @Mock
//    private EmprestimoService emprestimoService;
//    @Mock
//    private ItemService itemService;
//
//    private Model model;
//    private List<Emprestimo> emprestimos;
//    private List<Item> itens;
//
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//
//        this.model = new ConcurrentModel();
//        this.emprestimos = (List<Emprestimo>) EmprestimoBuilder.mockCollectionEmprestimoBuilder().getEmprestimos();
//        this.itens = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();
//    }
//
//    @Test
//    public void homepage() {
//        Mockito.when(this.emprestimoService.getAllEmprestimoAbertos()).thenReturn(this.emprestimos);
//        Mockito.when(this.itemService.getWishListItems()).thenReturn(this.itens);
//
//        Assert.assertEquals(this.homeController.homePage(this.model), "home/home");
//        Assert.assertEquals(this.model.containsAttribute("appName"), true);
//    }
//
//    @Test
//    public void sobre() {
//        Assert.assertEquals(this.homeController.sobrePage(), "sobre/sobre");
//    }
}
