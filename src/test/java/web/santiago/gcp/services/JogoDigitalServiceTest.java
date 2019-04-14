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
import web.santiago.gcp.builders.JogoDigitalBuilder;
import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.repositories.JogoDigitalRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JogoDigitalServiceTest {

    @InjectMocks
    private JogoDigitalService jogoDigitalService;

    @Mock
    private JogoDigitalRepository jogoDigitalRepository;

    // Mocks
    private Optional<JogoDigital> jogoDigitalOptional;
    private List<JogoDigital> jogosDigitais;
    private JogoDigitalDto jogoDigitalDto;
    private JogoDigital jogoDigital;
    private Item item;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.jogoDigitalOptional = JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigitalOptional();
        this.jogosDigitais = (List) JogoDigitalBuilder.mockCollectionJogoDigitalBuilder().getJogosDigitais();
        this.jogoDigitalDto = JogoDigitalBuilder.mockJogoDigitalDtoBuilder().getJogoDigitalDto();
        this.jogoDigital = JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigital();
        this.item = ItemBuilder.mockItemBuilder().getItem();
    }

    @Test
    public void getAll() {
        Mockito.when(this.jogoDigitalRepository.findAll()).thenReturn(this.jogosDigitais);
        Assert.assertFalse(this.jogoDigitalService.getAll().isEmpty());
        Assert.assertEquals(this.jogoDigitalService.getAll().size(), 10);
    }

    @Test
    public void getById() {
        Mockito.when(this.jogoDigitalRepository.findById(1L)).thenReturn(this.jogoDigitalOptional);
        Assert.assertEquals(this.jogoDigitalService.getById(1L), this.jogoDigitalOptional);
    }

    @Test
    public void save() {
        Mockito.when(this.jogoDigitalRepository.save(this.jogoDigital)).thenReturn(this.jogoDigital);

        Assert.assertEquals(this.jogoDigitalService.save(this.jogoDigitalDto), this.jogoDigital);
    }

    @Test
    public void delete() {
        this.jogoDigitalService.delete(1L);
        Mockito.verify(jogoDigitalRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void getAllByConsole(){
        Mockito.when(this.jogoDigitalRepository.findAllByConsole(this.jogoDigital.getConsole())).thenReturn(this.jogosDigitais);
        Assert.assertEquals(this.jogoDigitalService.getAllByConsole(this.jogoDigital.getConsole()), this.jogosDigitais);
    }

    @Test
    public void getAllByConsoleNull(){
        Mockito.when(this.jogoDigitalRepository.findAll()).thenReturn(this.jogosDigitais);
        Assert.assertEquals(this.jogoDigitalService.getAllByConsole(null), this.jogosDigitais);
    }

    @Test
    public void mapper() {
        Assert.assertEquals(this.jogoDigitalService.mapper(this.jogoDigitalDto), this.jogoDigital);
    }

    @Test
    public void createDtoFromItemJogoDigital(){
        JogoDigitalService spyJogoDigitalService = Mockito.spy(this.jogoDigitalService);

        spyJogoDigitalService.createDtoFromItemJogoDigital(this.item, this.jogoDigital);

        Mockito.verify(spyJogoDigitalService).createDtoFromItemJogoDigital(this.item, this.jogoDigital);
    }
}
