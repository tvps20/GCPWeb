package web.santiago.gcp.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.DlcBuilder;
import web.santiago.gcp.entities.Dlc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DlcRepositoryTest {

    @Autowired
    private DlcRepository dlcRepository;

    private Dlc dlc;

    @Before
    public void setUp() {
        this.dlc = DlcBuilder.mockDlcBuilder().getDlc();
    }

    @After
    public void after() {
        this.dlcRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.dlcRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.dlcRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.dlc.setId(0L);
        this.dlc = this.dlcRepository.save(this.dlc);
        Assert.assertNotEquals(this.dlc.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.dlc.setId(0L);
        this.dlc = this.dlcRepository.save(this.dlc);
        this.dlc.setLocalizacao("Console");
        Dlc dlcAtualizado = this.dlcRepository.save(this.dlc);
        Assert.assertEquals(dlcAtualizado.getLocalizacao(), this.dlc.getLocalizacao());
    }

    @Test
    public void deleteById() {
        this.dlc = this.dlcRepository.save(this.dlc);
        this.dlcRepository.deleteById(this.dlc.getId());

        Assert.assertEquals(this.dlcRepository.findAll().size(), 0);
    }
}
