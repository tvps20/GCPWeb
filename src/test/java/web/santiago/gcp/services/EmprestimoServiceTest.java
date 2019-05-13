package web.santiago.gcp.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import web.santiago.gcp.builders.AmigoBuilder;
import web.santiago.gcp.builders.EmprestimoBuilder;
import web.santiago.gcp.builders.ItemBuilder;
import web.santiago.gcp.dtos.EmprestimoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.entities.Emprestimo;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.exceptions.EntityNotFoundException;
import web.santiago.gcp.repositories.EmprestimoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EmprestimoServiceTest {

    @InjectMocks
    private EmprestimoService emprestimoService;
    @Mock
    private ItemService itemService;
    @Mock
    private AmigoService amigoService;
    @Mock
    private EmprestimoRepository emprestimoRepository;
    @Mock
    private Date date;

    private EmprestimoService spyEmprestimoService;

    private Emprestimo emprestimo;
    private EmprestimoDto emprestimoDto;
    private Optional<Emprestimo> emprestimoOptional;
    private Optional<Item> itemOptional;
    private Optional<Amigo> amigoOptional;
    private List<Emprestimo> emprestimos;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.emprestimo = EmprestimoBuilder.mockEmprestimoBuilder().getEmprestimo();
        this.emprestimoDto = EmprestimoBuilder.mockEmprestimoDtoBuilder().getEmpretimoDto();
        this.emprestimoOptional = EmprestimoBuilder.mockEmprestimoBuilder().getEmprestimoOptional();
        this.itemOptional = ItemBuilder.mockItemBuilder().getItemOptional();
        this.amigoOptional = AmigoBuilder.mockAmigoBuilder().getAmigoOptional();
        this.spyEmprestimoService = Mockito.spy(this.emprestimoService);
        this.emprestimos = (List<Emprestimo>) EmprestimoBuilder.mockCollectionEmprestimoBuilder().getEmprestimos();
    }

    @Test
    public void mapper() {
        Mockito.when(this.itemService.getById(this.emprestimoDto.getItem())).thenReturn(this.itemOptional);
        Mockito.when(this.amigoService.getById(this.emprestimoDto.getAmigo())).thenReturn(this.amigoOptional);

        this.spyEmprestimoService.mapper(this.emprestimoDto);

        Mockito.verify(this.spyEmprestimoService).mapper(this.emprestimoDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void mapperExceptionItem() {
        this.spyEmprestimoService.mapper(this.emprestimoDto);

        Mockito.verify(this.spyEmprestimoService).mapper(this.emprestimoDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void mapperExceptionAmigo() {
        Mockito.when(this.itemService.getById(this.emprestimoDto.getItem())).thenReturn(this.itemOptional);

        this.spyEmprestimoService.mapper(this.emprestimoDto);

        Mockito.verify(this.spyEmprestimoService).mapper(this.emprestimoDto);
    }

    @Test
    public void getAllEmprestimoAbertos() {
        this.spyEmprestimoService.getAllEmprestimoAbertos();

        Mockito.verify(this.spyEmprestimoService).getAllEmprestimoAbertos();
    }

    @Test
    public void devolver() {
        this.emprestimo.setItem(this.itemOptional.get());
        Mockito.when(this.itemService.update(this.itemOptional.get())).thenReturn(this.itemOptional.get());
        Mockito.when(this.emprestimoRepository.save(this.emprestimo)).thenReturn(this.emprestimo);

        Assert.assertEquals(this.emprestimoService.devolver(this.emprestimo), this.emprestimo);
    }

    @Test
    public void getEmprestimoNaoDevolvidoByItemId() {
        Mockito.when(this.emprestimoRepository.findByItemIdAndDevolvidoFalse(this.itemOptional.get().getId())).thenReturn(this.emprestimoOptional);

        Assert.assertEquals(this.emprestimoService.getEmprestimoNaoDevolvidoByItemId(this.itemOptional.get().getId()), this.emprestimoOptional);
    }
}
