package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.AmigoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.services.AmigoService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade Amigo
 *
 * @author Santiago Brothers
 */
@RestController
@RequestMapping("v1/amigos")
public class AmigoController {

	private static final Logger logger = LoggerFactory.getLogger(AmigoController.class);

	/**
	 * Servico responsavel por interagir com a base de dados da entidade Amigo
	 */
	@Autowired
	private AmigoService amigoService;

    /**
     * Busca todos os Amigos na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<Amigo> amigos = this.amigoService.listAll(pageable);
        amigos.getContent().forEach(amigo -> amigo.getEmprestimos().forEach(emprestimo -> {
            emprestimo.setAmigo(null);
            emprestimo.setItem(null);
        }));
        logger.info("Get all 'Amigo' from data source");
        return new ResponseEntity<>(amigos, HttpStatus.OK);
    }

    /**
     * Busca um Amigo da base de dados
     *
     * @param id Identificar da entidade Amigo a ser deletado
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        logger.info("Find 'Amigo' Id: {} on data source", id);
        this.amigoService.verifyIfExists(id);
    	logger.info("Searching for a 'Amigo' in the data source");
        Optional<Amigo> optionalAmigo = this.amigoService.getById(id);
        optionalAmigo.get().getEmprestimos().forEach(emprestimo -> {
            emprestimo.setAmigo(null);
            emprestimo.setItem(null);
        });
        return new ResponseEntity<Optional>(optionalAmigo, HttpStatus.OK);
    }

    /**
     * Salva um Amigo a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody AmigoDto dto) {
        if (dto.getId() != 0)
            logger.info("Updating 'Amigo' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Amigo' on data source");

        Amigo entity = this.amigoService.save(dto);
        entity.getEmprestimos().forEach(emprestimo -> {
            emprestimo.setItem(null);
            emprestimo.setAmigo(null);
        });
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    /**
     * Atualiza um Amigo a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody AmigoDto dto) {
        logger.info("Find 'Amigo' Id: {} on data source", dto.getId());
        this.amigoService.verifyIfExists(dto.getId());
        return this.save(dto);
    }

    /**
     * Deleta um Amigo da base de dados
     *
     * @param id Identificar da entidade Amigo a ser deletado
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
    	logger.info("Deleting 'Amigo' Id:{} from data source", id);
        logger.info("Find 'Amigo' Id: {} on data source", id);
        this.amigoService.verifyIfExists(id);
    	this.amigoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
