package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.DlcDto;
import web.santiago.gcp.entities.Dlc;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.services.DlcService;
import web.santiago.gcp.services.ItemService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v1/dlcs")
public class DlcController {

    private static final Logger logger = LoggerFactory.getLogger(DlcController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade Dlc
     */
    @Autowired
    private DlcService dlcService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Busca todas as Dlc's na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<Dlc> dlcs = this.dlcService.listAll(pageable);
        dlcs.getContent().forEach(dlc -> dlc.getJogo().setDlcs(null));
        logger.info("Get all 'Dlc' from data source");
        return new ResponseEntity<>(dlcs, HttpStatus.OK);
    }

    /**
     * Busca uma Dlc da base de dados
     *
     * @param id Identificar da entidade Dlc a ser deletado
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        logger.info("Find 'Dlc' Id: {} on data source", id);
        this.dlcService.verifyIfExists(id);
        logger.info("Searching for a 'Dlc' in the data source");
        Optional<Dlc> optionalDlc = this.dlcService.getById(id);
        optionalDlc.get().getJogo().setDlcs(null);
        return new ResponseEntity<Optional>(optionalDlc, HttpStatus.OK);
    }

    /**
     * Salva uma Dlc a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody DlcDto dto) {
        if (dto.getItemId() != 0)
            logger.info("Updating 'Dlc' Id: {} on data source", dto.getItemId());
        else
            logger.info("Creating new 'Dlc' on data source");

        Dlc dlcEntity = this.dlcService.save(dto);

        dto.setItemId(dlcEntity.getId());
        dto.setTipo("dlc");

        if (dto.getId() != 0)
            logger.info("Updating 'Item' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Item' on data source");

        Item item = this.itemService.save(dto);

        // Transformando o item salvo em itemDto
        DlcDto dtoSalvo = this.dlcService.createDtoFromItemDlc(item, dlcEntity);

        return new ResponseEntity<>(dtoSalvo, HttpStatus.CREATED);
    }

    /**
     * Atualiza uma Dlc a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody DlcDto dto) {
        logger.info("Find 'Dlc' Id: {} on data source", dto.getItemId());
        this.dlcService.verifyIfExists(dto.getItemId());
        logger.info("Find 'Item' Id: {} on data source", dto.getId());
        this.itemService.verifyIfExists(dto.getId());

        return this.save(dto);
    }

    /**
     * Deleta uma Dlc da base de dados
     *
     * @param id Identificar da entidade Dlc a ser deletado
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("Find 'Dlc' Id: {} on data source", id);
        this.dlcService.verifyIfExists(id);
        logger.info("Deleting 'Dlc' Id:{} from data source", id);
        this.dlcService.delete(id);
        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
