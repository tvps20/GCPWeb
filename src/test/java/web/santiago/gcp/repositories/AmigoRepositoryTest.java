package web.santiago.gcp.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.AmigoBuilder;
import web.santiago.gcp.entities.Amigo;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmigoRepositoryTest {

    @Autowired
    private AmigoRepository amigoRepository;

    private Amigo amigo;

    @Before
    public void setUp() {
        this.amigo = AmigoBuilder.mockAmigoBuilder().getAmigo();
    }

    @After
    public void after() {
        this.amigoRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.amigoRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.amigoRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.amigo.setId(0L);
        this.amigo = this.amigoRepository.save(this.amigo);
        Assert.assertNotEquals(this.amigo.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.amigo.setId(0L);
        this.amigo = this.amigoRepository.save(this.amigo);
        this.amigo.setNome("Filipe");
        Amigo amigoAtualizado = this.amigoRepository.save(this.amigo);
        Assert.assertEquals(amigoAtualizado.getNome(), this.amigo.getNome());
    }

    @Test
    public void deleteById() {
        this.amigo = this.amigoRepository.save(this.amigo);
        this.amigoRepository.deleteById(this.amigo.getId());

        Assert.assertEquals(this.amigoRepository.findAll().size(), 0);
    }
}
