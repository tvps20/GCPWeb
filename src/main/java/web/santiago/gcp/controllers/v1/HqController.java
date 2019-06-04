package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.HqDto;
import web.santiago.gcp.entities.Hq;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.services.HqService;
import web.santiago.gcp.services.ItemService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v1/hqs")
public class HqController {


    private static final Logger logger = LoggerFactory.getLogger(HqController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade Hq
     */
    @Autowired
    private HqService hqService;

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
        logger.info("Get all 'Hq' from data source");
        return new ResponseEntity<>(this.hqService.listAll(pageable), HttpStatus.OK);
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
        this.hqService.verifyIfExists(id);
        logger.info("Searching for a 'Hq' in the data source");
        return new ResponseEntity<Optional>(this.hqService.getById(id), HttpStatus.OK);
    }

    /**
     * Salva ou atualiza um Hq e um Item na base de dados
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody HqDto dto) {
        if (dto.getItemId() != 0)
            logger.info("Updating 'Hq' Id: {} on data source", dto.getItemId());
        else
            logger.info("Creating new 'Hq' on data source");

        Hq hqEntity = this.hqService.save(dto);

        dto.setItemId(hqEntity.getId());
        dto.setTipo("hq");

        if (dto.getId() != 0)
            logger.info("Updating 'Item' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Item' on data source");

        Item item =this.itemService.save(dto);

        // Transformando o item salvo em itemDto
        HqDto hqDto = this.hqService.createDtoFromItemHq(item, hqEntity);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    /**
     * Atualiza um Hq a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody HqDto dto) {
        logger.info("Find 'Hq' Id: {} on data source", dto.getItemId());
        this.hqService.verifyIfExists(dto.getItemId());
        logger.info("Find 'Item' Id: {} on data source", dto.getId());
        this.itemService.verifyIfExists(dto.getId());

        return this.save(dto);
    }

    /**
     * Deleta um Hq da base de dados
     *
     * @param id Identificar da entidade Hq a ser deletado
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("Find 'Hq' Id: {} on data source", id);
        this.hqService.verifyIfExists(id);
        logger.info("Deleting 'Hq' Id:{} from data source", id);
        this.hqService.delete(id);
        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
