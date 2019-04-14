package web.santiago.gcp.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.DvdCdBuilder;
import web.santiago.gcp.entities.DvdCd;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DvdCdRepositoryTest {

    @Autowired
    private DvdCdRepository dvdCdRepository;

    private DvdCd dvdCd;

    @Before
    public void setUp() {
        this.dvdCd = DvdCdBuilder.mockDvdCdBuilder().getDvdCd();
    }

    @After
    public void after() {
        this.dvdCdRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.dvdCdRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.dvdCdRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.dvdCd.setId(0L);
        this.dvdCd = this.dvdCdRepository.save(this.dvdCd);
        Assert.assertNotEquals(this.dvdCd.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.dvdCd.setId(0L);
        this.dvdCd = this.dvdCdRepository.save(this.dvdCd);
        this.dvdCd.setConteudo("Filme");
        DvdCd dvdCdAtualizado = this.dvdCdRepository.save(this.dvdCd);
        Assert.assertEquals(dvdCdAtualizado.getConteudo(), this.dvdCd.getConteudo());
    }

    @Test
    public void deleteById() {
        this.dvdCd = this.dvdCdRepository.save(this.dvdCd);
        this.dvdCdRepository.deleteById(this.dvdCd.getId());

        Assert.assertEquals(this.dvdCdRepository.findAll().size(), 0);
    }
}
