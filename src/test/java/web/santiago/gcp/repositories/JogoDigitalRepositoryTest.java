package web.santiago.gcp.repositories;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.JogoDigitalBuilder;
import web.santiago.gcp.entities.JogoDigital;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JogoDigitalRepositoryTest {

    @Autowired
    private JogoDigitalRepository jogoDigitalRepository;

    private JogoDigital jogoDigital;

    @Before
    public void setUp() {
        this.jogoDigital = JogoDigitalBuilder.mockJogoDigitalBuilder().getJogoDigital();
    }

    @After
    public void after() {
        this.jogoDigitalRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.jogoDigitalRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.jogoDigitalRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.jogoDigital.setId(0L);
        this.jogoDigital = this.jogoDigitalRepository.save(this.jogoDigital);
        Assert.assertNotEquals(this.jogoDigital.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.jogoDigital.setId(0L);
        this.jogoDigital = this.jogoDigitalRepository.save(this.jogoDigital);
        this.jogoDigital.setConsole("Xbox");
        JogoDigital jogoDigitalAtualizado = this.jogoDigitalRepository.save(this.jogoDigital);
        Assert.assertEquals(jogoDigitalAtualizado.getConsole(), this.jogoDigital.getConsole());
    }

    @Test
    public void deleteById() {
        this.jogoDigital = this.jogoDigitalRepository.save(this.jogoDigital);
        this.jogoDigitalRepository.deleteById(this.jogoDigital.getId());

        Assert.assertEquals(this.jogoDigitalRepository.findAll().size(), 0);
    }
}
