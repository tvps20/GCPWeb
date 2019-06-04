package web.santiago.gcp.services;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import web.santiago.gcp.builders.AmigoBuilder;
import web.santiago.gcp.dtos.AmigoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.repositories.AmigoRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AmigoServiceTest {

    @InjectMocks
    private AmigoService amigoService;

    @Mock
    private AmigoRepository amigoRepository;

    // Mocks
    private Optional<Amigo> amigoOptional;
    private List<Amigo> amigos;
    private AmigoDto amigoDto;
    private Amigo amigo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.amigoOptional = AmigoBuilder.mockAmigoBuilder().getAmigoOptional();
        this.amigos = (List) AmigoBuilder.mockCollectionAmigosBuilder().getAmigos();
        this.amigoDto = AmigoBuilder.mockAmigoDtoBuilder().getAmigoDto();
        this.amigo = AmigoBuilder.mockAmigoBuilder().getAmigo();
    }

    @Test
    public void getAll() {
        Mockito.when(this.amigoRepository.findAll()).thenReturn(this.amigos);
        Assert.assertFalse(this.amigoService.getAll().isEmpty());
        Assert.assertEquals(this.amigoService.getAll().size(), 10);
    }

    @Test
    public void getById() {
        Mockito.when(this.amigoRepository.findById(1L)).thenReturn(this.amigoOptional);
        Assert.assertEquals(this.amigoService.getById(1L), this.amigoOptional);
    }

    @Test
    public void save() {
        Mockito.when(this.amigoRepository.save(this.amigo)).thenReturn(this.amigo);

        Assert.assertEquals(this.amigoService.save(this.amigoDto), this.amigo);
    }

    @Test
    public void delete() {
        this.amigoService.delete(1L);
        Mockito.verify(amigoRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void mapper() {
        Assert.assertEquals(this.amigoService.mapper(this.amigoDto), this.amigo);
    }

    @Test
    public void mapperIdDiferenteZero(){
        this.amigoDto.setId(1L);
        Assert.assertEquals(this.amigoService.mapper(this.amigoDto), this.amigo);
    }
}
