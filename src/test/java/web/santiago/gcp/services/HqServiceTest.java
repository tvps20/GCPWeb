package web.santiago.gcp.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import web.santiago.gcp.builders.HqBuilder;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.dtos.HqDto;
import web.santiago.gcp.entities.Hq;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.repositories.HqRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class HqServiceTest {

    @InjectMocks
    private HqService hqService;

    @Mock
    private HqRepository hqRepository;

    private HqService spyHqService;

    // Mocks
    private Optional<Hq> hqOptional;
    private List<Hq> hqs;
    private HqDto hqDto;
    private Hq hq;
    private Item item;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.hqOptional = HqBuilder.mockHqBuilder().getHqOptional();
        this.hqs = (List) HqBuilder.mockCollectionHqBuilder().getHqs();
        this.hqDto = HqBuilder.mockHqDtoBuilder().getHqDto();
        this.hq = HqBuilder.mockHqBuilder().getHq();
        this.item = ItemBuilder.mockItemBuilder().getItem();
        this.spyHqService = Mockito.spy(this.hqService);
    }

    @Test
    public void getAll() {
        Mockito.when(this.hqRepository.findAll()).thenReturn(this.hqs);
        Assert.assertFalse(this.hqService.getAll().isEmpty());
        Assert.assertEquals(this.hqService.getAll().size(), 10);
    }

    @Test
    public void getById() {
        Mockito.when(this.hqRepository.findById(1L)).thenReturn(this.hqOptional);
        Assert.assertEquals(this.hqService.getById(1L), this.hqOptional);
    }

    @Test
    public void save() {
        Mockito.when(this.hqRepository.save(this.hq)).thenReturn(this.hq);

        Assert.assertEquals(this.hqService.save(this.hqDto), this.hq);
    }

    @Test
    public void delete() {
        this.hqService.delete(1L);
        Mockito.verify(hqRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void getAllByEditoraAndUniverso(){
        Mockito.when(this.hqRepository.findAll()).thenReturn(this.hqs);

        this.spyHqService.getAllByEditoraAndUniverso(this.hq.getEditora(), this.hq.getUniverso());

        Mockito.verify(this.spyHqService).getAllByEditoraAndUniverso(this.hq.getEditora(), this.hq.getUniverso());
    }

    @Test
    public void mapper() {
        Assert.assertEquals(this.hqService.mapper(this.hqDto), this.hq);
    }

    @Test
    public void createDtoFromItemHq(){
        this.spyHqService.createDtoFromItemHq(this.item, this.hq);

        Mockito.verify(this.spyHqService).createDtoFromItemHq(this.item, this.hq);
    }
}
