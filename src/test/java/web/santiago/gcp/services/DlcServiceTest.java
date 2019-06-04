package web.santiago.gcp.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import web.santiago.gcp.builders.DlcBuilder;
import web.santiago.gcp.builders.JogoDigitalBuilder;
import web.santiago.gcp.dtos.DlcDto;
import web.santiago.gcp.entities.Dlc;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.exceptions.EntityNotFoundException;
import web.santiago.gcp.repositories.DlcRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DlcServiceTest {

    @InjectMocks
    private DlcService dlcService;

    @Mock
    private DlcRepository dlcRepository;

    @Mock
    private JogoDigitalService jogoDigitalService;

    // Mocks
    private Optional<Dlc> dlcOptional;
    private List<Dlc> dlcs;
    private DlcDto dclDto;
    private Dlc dlc;
    private Optional<JogoDigital> jogoDigitalOptional;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.dlcOptional = DlcBuilder.mockDlcBuilder().getDlcOptional();
        this.dlcs = (List) DlcBuilder.mockCollectionDlcsBuilder().getDlcs();
        this.dclDto = DlcBuilder.mockDlcDtoBuilder().getDlcDto();
        this.dlc = DlcBuilder.mockDlcBuilder().getDlc();
        this.jogoDigitalOptional = JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigitalOptional();
        this.dlc.setJogo(JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigital());
    }

    @Test
    public void getAll() {
        Mockito.when(this.dlcRepository.findAll()).thenReturn(this.dlcs);
        Assert.assertFalse(this.dlcService.getAll().isEmpty());
        Assert.assertEquals(this.dlcService.getAll().size(), 10);
    }

    @Test
    public void getById() {
        Mockito.when(this.dlcRepository.findById(1L)).thenReturn(this.dlcOptional);
        Assert.assertEquals(this.dlcService.getById(1L), this.dlcOptional);
    }

    @Test
    public void save() {
        Mockito.when(this.jogoDigitalService.getById(this.dclDto.getJogo())).thenReturn(this.jogoDigitalOptional);
        Mockito.when(this.dlcRepository.save(this.dlc)).thenReturn(this.dlc);

        Assert.assertEquals(this.dlcService.save(this.dclDto), this.dlc);
    }

    @Test
    public void delete() {
        this.dlcService.delete(1L);
        Mockito.verify(dlcRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void mapper() {
        Mockito.when(this.jogoDigitalService.getById(this.dclDto.getJogo())).thenReturn(this.jogoDigitalOptional);
        Assert.assertEquals(this.dlcService.mapper(this.dclDto), this.dlc);
    }

    @Test(expected = EntityNotFoundException.class)
    public void mapperException() {
        Mockito.when(this.jogoDigitalService.getById(this.dclDto.getJogo())).thenReturn(JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigitalEmptyOptional());
        Assert.assertEquals(this.dlcService.mapper(this.dclDto), this.dlc);
    }
}
