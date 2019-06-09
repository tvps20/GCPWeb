package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.SagaDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.Saga;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.SagaService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/saga")
public class SagaController {

    private static final Logger logger = LoggerFactory.getLogger(SagaController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade Saga
     */
    @Autowired
    private SagaService sagaService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Busca todas as Sagas na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<Saga> sagas = this.sagaService.listAll(pageable);
        sagas.getContent().forEach(saga -> saga.getItems().forEach(item -> {
            item.setSaga(null);
            item.setEmprestimos(null);
        }));
        logger.info("Get all 'Saga' from data source");
        return new ResponseEntity<>(sagas, HttpStatus.OK);
    }

    /**
     * Busca um Saga da base de dados
     *
     * @param id Identificar da entidade Saga a ser deletado
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        logger.info("Find 'Saga' Id: {} on data source", id);
        this.sagaService.verifyIfExists(id);
        logger.info("Searching for a 'Saga' in the data source");
        Optional<Saga> optionalSaga = this.sagaService.getById(id);
        optionalSaga.get().getItems().forEach(item -> {
            item.setSaga(null);
            item.setEmprestimos(null);
        });
        return new ResponseEntity<Optional>(optionalSaga, HttpStatus.OK);
    }

    /**
     * Salva ou atualiza um Saga e um Item na base de dados
     * 
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody SagaDto dto) {
        if (dto.getId() != 0)
            logger.info("Updating 'Saga' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Saga' on data source");

        Saga entity = this.sagaService.save(dto);

        // remove old items from saga
        List<Item> items = this.itemService.getAllItemsPorSaga(entity.getId());
        if (items.size() != dto.getItems().size()) {
            // pegar os diferentes
            List<Item> diferentes = items.stream()
                    .filter(item -> dto.getItems().stream().anyMatch(id -> item.getId() != id))
                    .collect(Collectors.toList());
            items.forEach(item -> item.setSaga(null));
            this.itemService.saveAll(items);
        }

        entity.getItems().forEach(item -> {
            item.setSaga(null);
            item.setEmprestimos(null);
        });
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    /**
     * Atualiza um Saga a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody SagaDto dto) {
        logger.info("Find 'Itens' Id: {} on data source", dto.getId());
        this.sagaService.verifyIfExists(dto.getId());
        dto.getItems().forEach(item -> this.itemService.verifyIfExists(item));
        return this.save(dto);
    }

    /**
     * Deleta um Saga da base de dados
     *
     * @param id Identificar da entidade Saga a ser deletado
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("Find 'Saga' Id: {} on data source", id);
        this.sagaService.verifyIfExists(id);

        List<Item> itens = this.itemService.getAllItemsPorSaga(id);
        itens.forEach(item -> item.setSaga(null));
        this.itemService.saveAll(itens);

        logger.info("Deleting 'Saga' Id:{} from data source");
        this.sagaService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}