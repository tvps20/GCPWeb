package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.EmprestimoDto;
import web.santiago.gcp.entities.Emprestimo;
import web.santiago.gcp.services.AmigoService;
import web.santiago.gcp.services.EmprestimoService;
import web.santiago.gcp.services.ItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/emprestimo")
public class EmprestimoController {

    private static final Logger logger = LoggerFactory.getLogger(AmigoController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade Emprestimo
     */
    @Autowired
    private EmprestimoService emprestimoService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Amigo
     */
    @Autowired
    private AmigoService amigoService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Busca todas as Hq na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<Emprestimo> emprestimos = this.emprestimoService.listAll(pageable);
        emprestimos.getContent().forEach(emprestimo -> {
            emprestimo.getAmigo().setEmprestimos(null);
            emprestimo.getItem().setEmprestimos(null);
            emprestimo.getItem().setSaga(null);
        });
        logger.info("Get all 'Emprestimo' from data source");
        return new ResponseEntity<>(emprestimos, HttpStatus.OK);
    }

    /**
     * Busca todas as Hq na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping("/abertos")
    public ResponseEntity<?> listAllAbertos(Pageable pageable) {
        logger.info("Get all 'Emprestimos Abertos' from data source");
        List<Emprestimo> itens = this.emprestimoService.getAllEmprestimoAbertos();
        itens.forEach(item -> {
            item.getAmigo().setEmprestimos(null);
            item.getItem().setEmprestimos(null);
            item.getItem().setSaga(null);
        });
        Page<Emprestimo> pages = new PageImpl<>(itens, pageable, itens.size());
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    /**
     * Busca um Hq da base de dados
     *
     * @param id Identificar da entidade Hq a ser deletado
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        logger.info("Find 'Hq' Id: {} on data source", id);
        this.emprestimoService.verifyIfExists(id);
        logger.info("Searching for a 'Hq' in the data source");
        Optional<Emprestimo> optionalEmprestimo = this.emprestimoService.getById(id);
        optionalEmprestimo.get().getItem().setEmprestimos(null);
        optionalEmprestimo.get().getAmigo().setEmprestimos(null);
        optionalEmprestimo.get().getItem().setSaga(null);
        return new ResponseEntity<Optional>(optionalEmprestimo, HttpStatus.OK);
    }

    /**
     * Salva ou atualiza um Hq e um Item na base de dados
     * 
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EmprestimoDto dto) {
        logger.info("Creating new 'Emprestimo' on data source");

        Emprestimo emprestimo = this.emprestimoService.save(dto);
        this.itemService.emprestarItem(dto.getItem());

        emprestimo.getItem().setEmprestimos(null);
        emprestimo.getAmigo().setEmprestimos(null);
        emprestimo.getItem().setSaga(null);

        return new ResponseEntity<>(emprestimo, HttpStatus.CREATED);
    }

    @GetMapping("/devolver/{id}")
    public ResponseEntity<?> devolver(@PathVariable long id,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "returnUrl", required = false) String returnUrl) {

        Emprestimo emprestimo;
        Optional<Emprestimo> entity;

        logger.info("Find 'Emprestimo' Id: {} on data source", id);
        this.emprestimoService.verifyIfExists(id);

        if (type != null && type.equals("item")) {
            entity = this.emprestimoService.getEmprestimoNaoDevolvidoByItemId(id);
        } else {
            entity = this.emprestimoService.getById(id);
        }

        emprestimo = entity.get();
        if (!emprestimo.isDevolvido()) {
            this.emprestimoService.devolver(emprestimo);
        }

        emprestimo.getItem().setEmprestimos(null);
        emprestimo.getAmigo().setEmprestimos(null);
        emprestimo.getItem().setSaga(null);

        return new ResponseEntity<>(emprestimo, HttpStatus.OK);
    }
}
