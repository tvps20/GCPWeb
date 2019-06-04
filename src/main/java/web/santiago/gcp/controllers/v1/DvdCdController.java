package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.services.DvdCdService;
import web.santiago.gcp.services.ItemService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v1/dvdsCds")
public class DvdCdController {

    private static final Logger logger = LoggerFactory.getLogger(DvdCdController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade DvdCd
     */
    @Autowired
    private DvdCdService dvdCdService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Busca todas as Dvd Cd na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        logger.info("Get all 'Dvd Cd' from data source");
        return new ResponseEntity<>(this.dvdCdService.listAll(pageable), HttpStatus.OK);
    }

    /**
     * Busca um Dvd Cd da base de dados
     *
     * @param id Identificar da entidade Dvd Cd a ser deletado
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        logger.info("Find 'Dvd Cd' Id: {} on data source", id);
        this.dvdCdService.verifyIfExists(id);
        logger.info("Searching for a 'Dvd Cd' in the data source");
        return new ResponseEntity<Optional>(this.dvdCdService.getById(id), HttpStatus.OK);
    }

    /**
     * Salva ou atualiza um Dvd Cd e um Item na base de dados
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody DvdCdDto dto) {
        if (dto.getItemId() != 0)
            logger.info("Updating 'DvdCd' Id: {} on data source", dto.getItemId());
        else
            logger.info("Creating new 'DvdCd' on data source");

        DvdCd dvdCdEntity = this.dvdCdService.save(dto);

        dto.setItemId(dvdCdEntity.getId());
        dto.setTipo("dvdcd");

        if (dto.getId() != 0)
            logger.info("Updating 'Item' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Item' on data source");

        Item item = this.itemService.save(dto);

        // Transformando o item salvo em itemDto
        DvdCdDto dvdCdDto = this.dvdCdService.createDtoFromItemDvdCd(item, dvdCdEntity);

        return new ResponseEntity<>(dvdCdDto, HttpStatus.CREATED);
    }

    /**
     * Atualiza um Dvd Cd a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody DvdCdDto dto) {
        logger.info("Find 'Dvd Cd' Id: {} on data source", dto.getItemId());
        this.dvdCdService.verifyIfExists(dto.getItemId());
        logger.info("Find 'Item' Id: {} on data source", dto.getId());
        this.itemService.verifyIfExists(dto.getId());

        return this.save(dto);
    }

    /**
     * Deleta um Dvd Cd da base de dados
     *
     * @param id Identificar da entidade Dvd Cd a ser deletado
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("Find 'Dvd Cd' Id: {} on data source", id);
        this.dvdCdService.verifyIfExists(id);
        logger.info("Deleting 'DvdCd' Id:{} from data source", id);
        this.dvdCdService.delete(id);
        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
