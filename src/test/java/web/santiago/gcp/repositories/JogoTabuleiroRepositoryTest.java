package web.santiago.gcp.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.JogoTabuleiroBuilder;
import web.santiago.gcp.entities.JogoTabuleiro;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JogoTabuleiroRepositoryTest {

    @Autowired
    private JogoTabuleiroRepository jogoTabuleiroRepository;

    private JogoTabuleiro jogoTabuleiro;

    @Before
    public void setUp() {
        this.jogoTabuleiro = JogoTabuleiroBuilder.mockJogoTabuleiroBuilder().getJogoTabuleiro();
    }

    @After
    public void after() {
        this.jogoTabuleiroRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.jogoTabuleiroRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.jogoTabuleiroRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.jogoTabuleiro.setId(0L);
        this.jogoTabuleiro = this.jogoTabuleiroRepository.save(this.jogoTabuleiro);
        Assert.assertNotEquals(this.jogoTabuleiro.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.jogoTabuleiro.setId(0L);
        this.jogoTabuleiro = this.jogoTabuleiroRepository.save(this.jogoTabuleiro);
        this.jogoTabuleiro.setMarca("Wizards of the coast");
        JogoTabuleiro jogoTabuleiroAtualizado = this.jogoTabuleiroRepository.save(this.jogoTabuleiro);
        Assert.assertEquals(jogoTabuleiroAtualizado.getMarca(), this.jogoTabuleiro.getMarca());
    }

    @Test
    public void deleteById() {
        this.jogoTabuleiro = this.jogoTabuleiroRepository.save(this.jogoTabuleiro);
        this.jogoTabuleiroRepository.deleteById(this.jogoTabuleiro.getId());

        Assert.assertEquals(this.jogoTabuleiroRepository.findAll().size(), 0);
    }
}
