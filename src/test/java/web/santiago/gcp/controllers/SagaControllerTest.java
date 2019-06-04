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
import web.santiago.gcp.builders.SagaBuilder;
import web.santiago.gcp.dtos.SagaDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.Saga;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.SagaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SagaControllerTest {

    @InjectMocks
    private SagaController sagaController;
    @Mock
    private SagaService sagaService;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private ItemService itemService;


    private Model model;
    private List<Saga> sagas;
    private List<Item> items;
    private List<Long> ids;
    private Optional<Saga> sagaOptional;
    private SagaDto sagaDto;
    private Saga saga;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.sagas = (List<Saga>) SagaBuilder.mockCollectionSagaBuilder().getSagas();
        this.items = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();
        this.sagaOptional = SagaBuilder.mockSagaBuilder().getSagaOptional();
        this.sagaDto = SagaBuilder.mockSagaDtoBuilder().getSagaDto();
        this.saga = SagaBuilder.mockSagaBuilder().getSaga();
        this.model = new ConcurrentModel();
        this.ids = new ArrayList<>();
        this.ids.add(1L);
        this.sagaDto.setItems(this.ids);
    }

    @Test
    public void index() {
        Mockito.when(this.sagaService.getAll()).thenReturn(this.sagas);

        Assert.assertEquals(this.sagaController.index(this.model), "saga/saga-index");
        Assert.assertEquals(this.model.containsAttribute("sagas"), true);
    }

    @Test
    public void create() {
        Assert.assertEquals(this.sagaController.create(this.model), "saga/saga-save");
    }

    @Test
    public void update() {
        Mockito.when(this.sagaService.getById(1L)).thenReturn(this.sagaOptional);

        Assert.assertEquals(this.sagaController.update(1L, this.model), "saga/saga-save");
        Assert.assertEquals(this.model.containsAttribute("saga"), true);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(this.sagaService.getById(0L)).thenReturn(SagaBuilder.mockSagaBuilder().getSagaEmptyOptional());

        Assert.assertEquals(this.sagaController.update(0L, this.model), "not-found");
        Assert.assertEquals(this.model.containsAttribute("saga"), false);
    }

    @Test
    public void save() {
        Mockito.when(this.sagaService.save(this.sagaDto)).thenReturn(this.saga);
        Mockito.when(this.itemService.getAllItemsPorSaga(Mockito.anyLong())).thenReturn(this.items);
        Assert.assertEquals(this.sagaController.save(this.sagaDto, this.bindingResult, this.model), "redirect:/saga");
    }

    @Test
    public void saveError() {
        Mockito.when(this.sagaService.save(this.sagaDto)).thenReturn(this.saga);
        Mockito.when(this.itemService.getAllItemsPorSaga(Mockito.anyLong())).thenReturn(this.items);
        Mockito.when(this.bindingResult.hasErrors()).thenReturn(true);
        Assert.assertEquals(this.sagaController.save(this.sagaDto, this.bindingResult, this.model), "saga/saga-save");
    }

    @Test
    public void saveIdDiferenteZero() {
        Mockito.when(this.sagaService.save(this.sagaDto)).thenReturn(this.saga);
        Mockito.when(this.itemService.getAllItemsPorSaga(Mockito.anyLong())).thenReturn(this.items);
        this.sagaDto.setId(1L);
        Assert.assertEquals(this.sagaController.save(this.sagaDto, this.bindingResult, this.model), "redirect:/saga");
    }

    @Test
    public void delete() {
        Assert.assertEquals(this.sagaController.delete(1L), "redirect:/saga");
        Mockito.verify(this.sagaService, Mockito.times(1)).delete(1L);
    }
}
