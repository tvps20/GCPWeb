package web.santiago.gcp.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.entities.Item;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    private Item item;

    @Before
    public void setUp() {
        this.item = ItemBuilder.mockItemBuilder().getItem();
    }

    @After
    public void after() {
        this.itemRepository.deleteAll();
    }

    @Test
    public void findAll() {
        Assert.assertThat(this.itemRepository.findAll(), instanceOf(List.class));
    }

    @Test
    public void findById() {
        Assert.assertThat(this.itemRepository.findById(1L), instanceOf(Optional.class));
    }

    @Test
    public void saveCreate() {

        this.item.setId(0L);
        //this.item.setEmprestimos((Set<Emprestimo>) EmprestimoBuilder.mockCollectionEmprestimoBuilder().getEmprestimos());
        this.item = this.itemRepository.save(this.item);
        Assert.assertNotEquals(this.item.getId(), 0L);
    }

    @Test
    public void saveUpdate() {

        this.item.setId(0L);
        this.item = this.itemRepository.save(this.item);
        this.item.setTitulo("Novo Item");
        Item jogoDigitalAtualizado = this.itemRepository.save(this.item);
        Assert.assertEquals(jogoDigitalAtualizado.getTitulo(), this.item.getTitulo());
    }

    @Test
    public void deleteById() {
        this.item = this.itemRepository.save(this.item);
        this.itemRepository.deleteById(this.item.getId());

        Assert.assertEquals(this.itemRepository.findAll().size(), 0);
    }

    @Test
    public void findTop10ByImportanciaDesc() {
        List<Item> itens = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();

        for (Item item : itens) {
            Random random = new Random();
            item.setImportancia(random.nextFloat() * 5);
            this.itemRepository.save(item);
        }

        final List<Item> top10ByOrderByImportanciaAsc = this.itemRepository.findTop10ByOrderByImportanciaDesc();

        Assert.assertEquals(this.itemRepository.findTop10ByOrderByImportanciaDesc().size(), 10);
        Assert.assertTrue(top10ByOrderByImportanciaAsc.get(0).getImportancia() >= top10ByOrderByImportanciaAsc.get(1).getImportancia());
    }

    @Test
    public void findTop10ByOrderByQtdEmprestimosDesc(){
        List<Item> itens = (List<Item>) ItemBuilder.mockCollectionItemBuilder().getItens();

        for (Item item : itens) {
            Random random = new Random();
            item.setQtdEmprestimos(random.nextInt(10));
            this.itemRepository.save(item);
        }

        final List<Item> top10ByOrderByQtdEmprestimosDesc = this.itemRepository.findTop10ByOrderByQtdEmprestimosDesc();

        Assert.assertEquals(this.itemRepository.findTop10ByOrderByQtdEmprestimosDesc().size(), 10);
        Assert.assertTrue(top10ByOrderByQtdEmprestimosDesc.get(0).getQtdEmprestimos() >= top10ByOrderByQtdEmprestimosDesc.get(1).getQtdEmprestimos());
    }
}
