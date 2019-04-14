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
import web.santiago.gcp.dtos.ItemDto;
import web.santiago.gcp.entities.Emprestimo;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.exceptions.EntityNotFoundException;
import web.santiago.gcp.repositories.ItemRepository;

import java.util.*;

@SpringBootTest
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    private ItemService spyItemService;

    // Mocks
    private Optional<Item> itemOptional;
    private List<Item> itens;
    private ItemDto itemDto;
    private Item item;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.itemOptional = ItemBuilder.mockItemBuilder().getItemOptional();
        this.itens = (List) ItemBuilder.mockCollectionItemBuilder().getItens();
        this.itemDto = ItemBuilder.mockItemDtoBuilder().getItemDto();
        this.item = ItemBuilder.mockItemBuilder().getItem();
        this.spyItemService = Mockito.spy(this.itemService);
    }

    @Test
    public void getAll() {
        Mockito.when(this.itemRepository.findAll()).thenReturn(this.itens);
        Assert.assertFalse(this.itemService.getAll().isEmpty());
        Assert.assertEquals(this.itemService.getAll().size(), 10);
    }

    @Test
    public void getById() {
        Mockito.when(this.itemRepository.findById(1L)).thenReturn(this.itemOptional);
        Assert.assertEquals(this.itemService.getById(1L), this.itemOptional);
    }

    @Test
    public void save() {
        Mockito.when(this.itemRepository.save(this.item)).thenReturn(this.item);

        Assert.assertEquals(this.itemService.save(this.itemDto), this.item);
    }

    @Test
    public void delete() {
        this.itemService.delete(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void getAllByTituloAndTipoAndEstadoAndEmprestadoAndSemIds(){
        List<Long> ids = new ArrayList<Long>();
        this.item.setEmprestado(true);

        this.spyItemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(this.item.getTitulo(), this.item.getTipo(), this.item.getEstado(), this.item.isEmprestado(), ids);

        Mockito.verify(this.spyItemService).getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(this.item.getTitulo(), this.item.getTipo(), this.item.getEstado(), this.item.isEmprestado(), ids);
    }

    @Test
    public void getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(){
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);

        this.spyItemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(this.item.getTitulo(), this.item.getTipo(), this.item.getEstado(), this.item.isEmprestado(), ids);

        Mockito.verify(this.spyItemService).getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(this.item.getTitulo(), this.item.getTipo(), this.item.getEstado(), this.item.isEmprestado(), ids);
    }

    @Test
    public void mapper() {
        Assert.assertEquals(this.itemService.mapper(this.itemDto), this.item);
    }

    @Test
    public void mapperEmprestado(){
        this.itemDto.setEmprestado(true);
        this.itemDto.setId(1L);
        this.item.setEmprestado(true);
        this.item.setId(1L);
        this.item.setEmprestimos(new HashSet<>());
        this.item.setQtdEmprestimos(2);
        Assert.assertEquals(this.itemService.mapper(this.itemDto), this.item);
    }

    @Test
    public void getByItemIdAndTipo() {
        Mockito.when(this.itemRepository.findByItemIdAndTipo(1L, TipoColecao.DLC.getValor())).thenReturn(this.itemOptional);

        Assert.assertEquals(this.itemService.getByItemIdAndTipo(1L, TipoColecao.DLC.getValor()), this.itemOptional);
    }

    @Test
    public void getAllByItemTipo(){
        List<Item> itens = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();
        Mockito.when(this.itemService.getAllByItemTipo(TipoColecao.DLC)).thenReturn(itens);

        Assert.assertEquals(this.itemService.getAllByItemTipo(TipoColecao.DLC), itens);
    }

    @Test
    public void deleteByItemId() {
        this.itemService.deleteByItemId(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).deleteByItemId(1L);
    }

    @Test
    public void getTop10ByImportancia(){
        List<Item> itens = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();

        for (Item item : itens) {
            Random random = new Random();
            item.setImportancia(random.nextFloat() * 5);
        }

        Mockito.when(itemRepository.findTop10ByOrderByImportanciaDesc()).thenReturn(itens);

        Assert.assertEquals(this.itemService.getTop10ByImportancia().size(), 10);
        Assert.assertEquals(this.itemService.getTop10ByImportancia(), itens);
    }

    @Test
    public void getTop10ByEmprestimos(){
        List<Item> itens = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();

        for (Item item : itens) {
            Random random = new Random();
            item.setQtdEmprestimos(random.nextInt(10));
        }

        Mockito.when(itemRepository.findTop10ByOrderByQtdEmprestimosDesc()).thenReturn(itens);

        Assert.assertEquals(this.itemService.getTop10ByEmprestimos().size(), 10);
        Assert.assertEquals(this.itemService.getTop10ByEmprestimos(), itens);
    }

    @Test
    public void emprestarItem(){
        Mockito.when(this.itemRepository.findById(this.item.getItemId())).thenReturn(this.itemOptional);

        Assert.assertEquals(this.itemService.emprestarItem(this.item.getItemId()), this.itemOptional.get());
    }

    @Test(expected = EntityNotFoundException.class)
    public void emprestarItemException(){
          Assert.assertEquals(this.itemService.emprestarItem(this.item.getItemId()), this.itemOptional.get());
    }
}
