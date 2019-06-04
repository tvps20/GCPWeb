package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.JogoTabuleiroDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoTabuleiro;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.JogoTabuleiroService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v1/jogosTabuleiros")
public class JogoTabuleiroController {

    private static final Logger logger = LoggerFactory.getLogger(JogoTabuleiroController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade JogoTabuleiro
     */
    @Autowired
    private JogoTabuleiroService jogoTabuleiroService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Busca todas os Jogos de Tabuleiro na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        logger.info("Get all 'Jogo Tabuleiro' from data source");
        return new ResponseEntity<>(this.jogoTabuleiroService.listAll(pageable), HttpStatus.OK);
    }

    /**
     * Busca um Jogo Tabuleiro da base de dados
     *
     * @param id Identificar da entidade Jogo Tabuleiro a ser deletado
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        logger.info("Find 'Jogo Tabuleiro' Id: {} on data source", id);
        this.jogoTabuleiroService.verifyIfExists(id);
        logger.info("Searching for a 'Jogo Tabuleiro' in the data source");
        return new ResponseEntity<Optional>(this.jogoTabuleiroService.getById(id), HttpStatus.OK);
    }

    /**
     * Salva ou atualiza um Jogo Tabuleiro e um Item na base de dados
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody JogoTabuleiroDto dto) {
        if (dto.getItemId() != 0)
            logger.info("Updating 'JogoTabuleiro' Id: {} on data source", dto.getItemId());
        else
            logger.info("Creating new 'JogoTabuleiro' on data source");

        JogoTabuleiro jogoTabuleiroEntity = this.jogoTabuleiroService.save(dto);

        dto.setItemId(jogoTabuleiroEntity.getId());
        dto.setTipo("jogotabuleiro");

        if (dto.getId() != 0)
            logger.info("Updating 'Item' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Item' on data source");

        Item item = this.itemService.save(dto);

        // Transformando o item salvo em itemDto
        JogoTabuleiroDto jogoTabuleiroDto = this.jogoTabuleiroService.createDtoFromItemJogoTabuleiro(item, jogoTabuleiroEntity);

        return new ResponseEntity<>(jogoTabuleiroDto, HttpStatus.CREATED);
    }

    /**
     * Atualiza um Jogo Tabuleiro a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody JogoTabuleiroDto dto) {
        logger.info("Find 'Jogo Tabuleiro' Id: {} on data source", dto.getItemId());
        this.jogoTabuleiroService.verifyIfExists(dto.getItemId());
        logger.info("Find 'Item' Id: {} on data source", dto.getId());
        this.itemService.verifyIfExists(dto.getId());

        return this.save(dto);
    }

    /**
     * Deleta um Jogo Tabuleiro da base de dados
     *
     * @param id Identificar da entidade Jogo Tabuleiro a ser deletado
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("Find 'Jogo Tabuleiro' Id: {} on data source", id);
        this.jogoTabuleiroService.verifyIfExists(id);
        logger.info("Deleting 'Jogo Tabuleiro' Id:{} from data source", id);
        this.jogoTabuleiroService.delete(id);
        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
