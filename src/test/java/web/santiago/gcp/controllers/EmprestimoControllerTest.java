package web.santiago.gcp.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import web.santiago.gcp.builders.AmigoBuilder;
import web.santiago.gcp.builders.EmprestimoBuilder;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.controllers.v1.EmprestimoController;
import web.santiago.gcp.dtos.EmprestimoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.entities.Emprestimo;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.services.AmigoService;
import web.santiago.gcp.services.EmprestimoService;
import web.santiago.gcp.services.ItemService;

import java.util.List;
import java.util.Optional;

public class EmprestimoControllerTest {

    @InjectMocks
    private EmprestimoController emprestimoController;
    @Mock
    private EmprestimoService emprestimoService;
    @Mock
    private AmigoService amigoService;
    @Mock
    private ItemService itemService;
    @Mock
    private BindingResult bindingResult;

    private Model model;

    private EmprestimoDto emprestimoDto;
    private Emprestimo emprestimo;
    private Optional<Emprestimo> emprestimoOptional;
    private Optional<Item> item;
    private List<Amigo> amigos;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.emprestimo = EmprestimoBuilder.mockEmprestimoBuilder().getEmprestimo();
        this.emprestimoDto = EmprestimoBuilder.mockEmprestimoDtoBuilder().getEmpretimoDto();
        this.item = ItemBuilder.mockItemBuilder().getItemOptional();
        this.amigos = (List<Amigo>) AmigoBuilder.mockCollectionAmigosBuilder().getAmigos();
        this.emprestimoOptional = EmprestimoBuilder.mockEmprestimoBuilder().getEmprestimoOptional();

        this.emprestimo.setItem(this.item.get());
        this.model = new ConcurrentModel();
    }

    @Test
    public void index() {
        Mockito.when(this.amigoService.getAll()).thenReturn(this.amigos);
        Mockito.when((this.itemService.getById(Mockito.anyLong()))).thenReturn(this.item);
//
//        Assert.assertEquals(this.emprestimoController.index(this.item.get().getId(), this.model), "emprestimo/emprestimo-index");
//        Assert.assertEquals(this.model.containsAttribute("amigos"), true);
//        Assert.assertEquals(this.model.containsAttribute("item"), true);
//        Assert.assertEquals(this.model.containsAttribute("emprestimo"), true);
    }

    @Test
    public void indexSemGetById() {
        Mockito.when(this.amigoService.getAll()).thenReturn(this.amigos);
//
//        Assert.assertEquals(this.emprestimoController.index(this.item.get().getId(), this.model), "not-found");
//        Assert.assertEquals(this.model.containsAttribute("amigos"), true);
//        Assert.assertEquals(this.model.containsAttribute("item"), false);
//        Assert.assertEquals(this.model.containsAttribute("emprestimo"), false);
    }

    @Test
    public void save() {
        Mockito.when(this.emprestimoService.save(this.emprestimoDto)).thenReturn(this.emprestimo);
        this.emprestimoDto.setId(0L);
//        Assert.assertEquals(this.emprestimoController.save(this.emprestimoDto, this.bindingResult, this.model), "redirect:/item");
    }

    @Test
    public void saveError() {
        Mockito.when((this.itemService.getById(Mockito.anyLong()))).thenReturn(this.item);
        Mockito.when(this.emprestimoService.save(this.emprestimoDto)).thenReturn(this.emprestimo);
        Mockito.when((this.bindingResult.hasErrors())).thenReturn(true);
//        Assert.assertEquals(this.emprestimoController.save(this.emprestimoDto, this.bindingResult, this.model), "emprestimo/emprestimo-index");
    }

    @Test
    public void devolver() {
        Mockito.when(this.emprestimoService.getById(this.emprestimo.getId())).thenReturn(this.emprestimoOptional);

//        Assert.assertEquals(this.emprestimoController.devolver(this.emprestimo.getId(), this.emprestimo.getItem().getTipo(), this.emprestimo.getItem().getUrl()), "redirect:/" + this.emprestimo.getItem().getUrl());
    }

    @Test
    public void devolverNotFound() {
//        Assert.assertEquals(this.emprestimoController.devolver(this.emprestimo.getId(), this.emprestimo.getItem().getTipo(), this.emprestimo.getItem().getUrl()), "not-found");
    }

    @Test
    public void devolverItem() {
        Mockito.when(this.emprestimoService.getEmprestimoNaoDevolvidoByItemId(this.emprestimo.getId())).thenReturn(this.emprestimoOptional);

//        Assert.assertEquals(this.emprestimoController.devolver(this.emprestimo.getId(), "item", null), "redirect:/");
    }
}
