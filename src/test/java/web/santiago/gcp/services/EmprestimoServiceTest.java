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

import java.util.Optional;

public class EmprestimoServiceTest {

    @InjectMocks
    private EmprestimoService emprestimoService;
    @Mock
    private ItemService itemService;
    @Mock
    private AmigoService amigoService;

    private EmprestimoService spyEmprestimoService;

    private Emprestimo emprestimo;
    private EmprestimoDto emprestimoDto;
    private Optional<Emprestimo> emprestimoOptional;
    private Optional<Item> itemOptional;
    private Optional<Amigo> amigoOptional;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.emprestimo = EmprestimoBuilder.mockEmprestimoBuilder().getEmprestimo();
        this.emprestimoDto = EmprestimoBuilder.mockEmprestimoDtoBuilder().getEmpretimoDto();
        this.emprestimoOptional = EmprestimoBuilder.mockEmprestimoBuilder().getEmprestimoOptional();
        this.itemOptional = ItemBuilder.mockItemBuilder().getItemOptional();
        this.amigoOptional = AmigoBuilder.mockAmigoBuilder().getAmigoOptional();
        this.spyEmprestimoService = Mockito.spy(this.emprestimoService);
    }

    @Test
    public void mapper() {
        Mockito.when(this.itemService.getById(this.emprestimoDto.getItem())).thenReturn(this.itemOptional);
        Mockito.when(this.amigoService.getById(this.emprestimoDto.getAmigo())).thenReturn(this.amigoOptional);

        this.spyEmprestimoService.mapper(this.emprestimoDto);

        Mockito.verify(this.spyEmprestimoService).mapper(this.emprestimoDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void mapperExceptionItem(){
        this.spyEmprestimoService.mapper(this.emprestimoDto);

        Mockito.verify(this.spyEmprestimoService).mapper(this.emprestimoDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void mapperExceptionAmigo(){
        Mockito.when(this.itemService.getById(this.emprestimoDto.getItem())).thenReturn(this.itemOptional);

        this.spyEmprestimoService.mapper(this.emprestimoDto);

        Mockito.verify(this.spyEmprestimoService).mapper(this.emprestimoDto);
    }
}
