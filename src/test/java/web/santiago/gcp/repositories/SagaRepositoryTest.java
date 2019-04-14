package web.santiago.gcp.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.SagaBuilder;
import web.santiago.gcp.entities.Saga;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SagaRepositoryTest {

    @Autowired
    private SagaRepository sagaRepository;

    private Saga saga;

    @Before
    public void setUp() {
        this.saga = SagaBuilder.mockSagaBuilder().getSaga();
    }

    @After
    public void after() {
        this.sagaRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.sagaRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.sagaRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.saga.setId(0L);
        this.saga = this.sagaRepository.save(this.saga);
        Assert.assertNotEquals(this.saga.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.saga.setId(0L);
        this.saga = this.sagaRepository.save(this.saga);
        this.saga.setTitulo("Nova Saga");
        Saga sagaAtualizado = this.sagaRepository.save(this.saga);
        Assert.assertEquals(sagaAtualizado.getTitulo(), this.saga.getTitulo());
    }

    @Test
    public void deleteById() {
        this.saga = this.sagaRepository.save(this.saga);
        this.sagaRepository.deleteById(this.saga.getId());

        Assert.assertEquals(this.sagaRepository.findAll().size(), 0);
    }
}
