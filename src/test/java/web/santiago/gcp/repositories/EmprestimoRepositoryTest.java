package web.santiago.gcp.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.EmprestimoBuilder;
import web.santiago.gcp.entities.Emprestimo;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmprestimoRepositoryTest {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    private Emprestimo emprestimo;

    @Before
    public void setUp() {
        this.emprestimo = EmprestimoBuilder.mockEmprestimoBuilder().getEmprestimo();
    }

    @After
    public void after() {
        this.emprestimoRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.emprestimoRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.emprestimoRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.emprestimo.setId(0L);
        this.emprestimo = this.emprestimoRepository.save(this.emprestimo);
        Assert.assertNotEquals(this.emprestimo.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.emprestimo.setId(0L);
        this.emprestimo = this.emprestimoRepository.save(this.emprestimo);
        this.emprestimo.setDevolvido(true);
        Emprestimo emprestimoAtualizado = this.emprestimoRepository.save(this.emprestimo);
        Assert.assertEquals(emprestimoAtualizado.isDevolvido(), this.emprestimo.isDevolvido());
    }

    @Test
    public void deleteById() {
        this.emprestimo = this.emprestimoRepository.save(this.emprestimo);
        this.emprestimoRepository.deleteById(this.emprestimo.getId());

        Assert.assertEquals(this.emprestimoRepository.findAll().size(), 0);
    }
}
