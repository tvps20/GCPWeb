package web.santiago.gcp.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.HqBuilder;
import web.santiago.gcp.entities.Hq;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HqRepositoryTest {

    @Autowired
    private HqRepository hqRepository;

    private Hq hq;

    @Before
    public void setUp() {
        this.hq = HqBuilder.mockHqBuilder().getHq();
    }

    @After
    public void after() {
        this.hqRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.hqRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.hqRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.hq.setId(0L);
        this.hq = this.hqRepository.save(this.hq);
        Assert.assertNotEquals(this.hq.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.hq.setId(0L);
        this.hq = this.hqRepository.save(this.hq);
        this.hq.setSaga("Nova saga");
        Hq hqAtualizado = this.hqRepository.save(this.hq);
        Assert.assertEquals(hqAtualizado.getSaga(), this.hq.getSaga());
    }

    @Test
    public void deleteById() {
        this.hq = this.hqRepository.save(this.hq);
        this.hqRepository.deleteById(this.hq.getId());

        Assert.assertEquals(this.hqRepository.findAll().size(), 0);
    }
}
