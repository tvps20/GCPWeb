package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.services.ItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Busca todas as Item na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        Page<Item> items = this.itemService.listAll(pageable);
        items.getContent().forEach(item -> item.setSaga(null));
        logger.info("Get all 'Item' from data source");
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Busca todos os itens da lista de desejo
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping("/wishlist")
    public ResponseEntity<?> listWishlist(Pageable pageable) {
        logger.info("Get all 'wishlist' from data source");
        List<Item> itens =  this.itemService.getWishListItems();
        Page<Item> pages = new PageImpl<>(itens, pageable, itens.size());
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    /**
     * Busca um Item da base de dados
     *
     * @param id Identificar da entidade Item a ser deletado
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        logger.info("Searching for a 'Item' in the data source");
        logger.info("Find 'Item' Id: {} on data source", id);
        this.itemService.verifyIfExists(id);

        return new ResponseEntity<Optional>(this.itemService.getById(id), HttpStatus.OK);
    }
}
