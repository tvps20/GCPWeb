package web.santiago.gcp.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.dtos.SagaDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.Saga;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SagaServiceTest {

    @InjectMocks
    private SagaService sagaService;
    @Mock
    private ItemService itemService;

    private Saga saga;
    private SagaDto sagaDto;
    private List<Item> itens;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.saga = new Saga("novos 52");
        this.sagaDto = new SagaDto();
        this.itens = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();
    }

    @Test
    public void mapper() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        Mockito.when(this.itemService.getAllItemsIn(ids)).thenReturn(this.itens);
        this.sagaDto.setTitulo("novos 52");
        Assert.assertEquals(this.sagaService.mapper(this.sagaDto).getTitulo(), this.saga.getTitulo());
    }

    @Test
    public void mapperIdZero() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        Mockito.when(this.itemService.getAllItemsIn(ids)).thenReturn(this.itens);
        this.sagaDto.setTitulo("novos 52");
        this.sagaDto.setId(0);
        Assert.assertEquals(this.sagaService.mapper(this.sagaDto).getTitulo(), this.saga.getTitulo());
    }
}
