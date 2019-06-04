package web.santiago.gcp.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import web.santiago.gcp.builders.JogoTabuleiroBuilder;
import web.santiago.gcp.dtos.JogoTabuleiroDto;
import web.santiago.gcp.entities.JogoTabuleiro;
import web.santiago.gcp.repositories.JogoTabuleiroRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JogoTabuleiroServiceTest {

    @InjectMocks
    private JogoTabuleiroService jogoTabuleiroService;

    @Mock
    private JogoTabuleiroRepository jogoTabuleiroRepository;

    // Mocks
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
    }

    @Test
    public void getAll() {
        Mockito.when(this.jogoTabuleiroRepository.findAll()).thenReturn(this.jogosTabuleiros);
        Assert.assertFalse(this.jogoTabuleiroService.getAll().isEmpty());
        Assert.assertEquals(this.jogoTabuleiroService.getAll().size(), 10);
    }

    @Test
    public void getById() {
        Mockito.when(this.jogoTabuleiroRepository.findById(1L)).thenReturn(this.jogoTabuleiroOptional);
        Assert.assertEquals(this.jogoTabuleiroService.getById(1L), this.jogoTabuleiroOptional);
    }

    @Test
    public void save() {
        Mockito.when(this.jogoTabuleiroRepository.save(this.jogoTabuleiro)).thenReturn(this.jogoTabuleiro);

        Assert.assertEquals(this.jogoTabuleiroService.save(this.jogoTabuleiroDto), this.jogoTabuleiro);
    }

    @Test
    public void delete() {
        this.jogoTabuleiroService.delete(1L);
        Mockito.verify(jogoTabuleiroRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void mapper() {
        Assert.assertEquals(this.jogoTabuleiroService.mapper(this.jogoTabuleiroDto), this.jogoTabuleiro);
    }
}
