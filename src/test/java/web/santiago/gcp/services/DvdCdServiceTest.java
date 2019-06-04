package web.santiago.gcp.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import web.santiago.gcp.builders.DvdCdBuilder;
import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.repositories.DvdCdRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DvdCdServiceTest {

    @InjectMocks
    private DvdCdService dvdCdService;

    @Mock
    private DvdCdRepository dvdCdRepository;

    // Mocks
    private Optional<DvdCd> dvdCdOptional;
    private List<DvdCd> dvdsCds;
    private DvdCdDto dvdCdDto;
    private DvdCd dvdCd;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.dvdCdOptional = DvdCdBuilder.mockDvdCdBuilder().getDvdCdOptional();
        this.dvdsCds = (List) DvdCdBuilder.mockCollectionDvdsCdsBuilder().getDvdsCds();
        this.dvdCdDto = DvdCdBuilder.mockDvdCdDtoBuilder().getDvdCdDto();
        this.dvdCd = DvdCdBuilder.mockDvdCdBuilder().getDvdCd();
    }

    @Test
    public void getAll() {
        Mockito.when(this.dvdCdRepository.findAll()).thenReturn(this.dvdsCds);
        Assert.assertFalse(this.dvdCdService.getAll().isEmpty());
        Assert.assertEquals(this.dvdCdService.getAll().size(), 10);
    }

    @Test
    public void getById() {
        Mockito.when(this.dvdCdRepository.findById(1L)).thenReturn(this.dvdCdOptional);
        Assert.assertEquals(this.dvdCdService.getById(1L), this.dvdCdOptional);
    }

    @Test
    public void save() {
        Mockito.when(this.dvdCdRepository.save(this.dvdCd)).thenReturn(this.dvdCd);

        Assert.assertEquals(this.dvdCdService.save(this.dvdCdDto), this.dvdCd);
    }

    @Test
    public void delete() {
        this.dvdCdService.delete(1L);
        Mockito.verify(dvdCdRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void mapper() {
        Assert.assertEquals(this.dvdCdService.mapper(this.dvdCdDto), this.dvdCd);
    }
}
