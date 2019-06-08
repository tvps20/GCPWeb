package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.JogoDigitalService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v1/jogodigital")
public class JogoDigitalController {

    private static final Logger logger = LoggerFactory.getLogger(JogoDigitalController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade JogoDigital
     */
    @Autowired
    private JogoDigitalService jogoDigitalService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Busca todas as Jogo Digital na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<JogoDigital> jogos = this.jogoDigitalService.listAll(pageable);
        jogos.getContent().forEach(jogoDigital -> jogoDigital.getDlcs().forEach(dlc -> dlc.setJogo(null)));
        logger.info("Get all 'Jogo Digital' from data source");
        return new ResponseEntity<>(jogos, HttpStatus.OK);
    }

    /**
     * Busca um Jogo Digital da base de dados
     *
     * @param id Identificar da entidade Jogo Digital a ser deletado
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        logger.info("Find 'Jogo Digital' Id: {} on data source", id);
        this.jogoDigitalService.verifyIfExists(id);
        logger.info("Searching for a 'Jogo Digital' in the data source");
        Optional<JogoDigital> optionalJogoDigital = this.jogoDigitalService.getById(id);
        optionalJogoDigital.get().getDlcs().forEach(dlc -> dlc.setJogo(null));
        return new ResponseEntity<Optional>(optionalJogoDigital, HttpStatus.OK);
    }

    /**
     * Salva ou atualiza um JogoDigital e um Item na base de dados
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody JogoDigitalDto dto) {
        if (dto.getItemId() != 0)
            logger.info("Updating 'Jogo Digital' Id: {} on data source", dto.getItemId());
        else
            logger.info("Creating new 'Jogo Digital' on data source");

        JogoDigital jogoDigitalEntity = this.jogoDigitalService.save(dto);

        dto.setItemId(jogoDigitalEntity.getId());
        dto.setTipo("jogodigital");

        if (dto.getId() != 0)
            logger.info("Updating 'Item' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Item' on data source");

        Item item = this.itemService.save(dto);

        // Transformando o item salvo em itemDto
        JogoDigitalDto jogoDigitalDto = this.jogoDigitalService.createDtoFromItemJogoDigital(item, jogoDigitalEntity);

        return new ResponseEntity<>(jogoDigitalDto, HttpStatus.CREATED);
    }

    /**
     * Atualiza um Jogo Digital a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody JogoDigitalDto dto) {
        logger.info("Find 'Jogo Digital' Id: {} on data source", dto.getItemId());
        this.jogoDigitalService.verifyIfExists(dto.getItemId());
        logger.info("Find 'Item' Id: {} on data source", dto.getId());
        this.itemService.verifyIfExists(dto.getId());

        return this.save(dto);
    }

    /**
     * Deleta um Jogo Digital da base de dados
     *
     * @param id Identificar da entidade Jogo Digital a ser deletado
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("Find 'Jogo Digital' Id: {} on data source", id);
        this.jogoDigitalService.verifyIfExists(id);
        logger.info("Deleting 'JogoDigital' Id:{} from data source", id);
        this.jogoDigitalService.delete(id);
        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
