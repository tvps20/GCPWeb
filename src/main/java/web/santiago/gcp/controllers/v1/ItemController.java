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
import web.santiago.gcp.entities.*;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;
    @Autowired
    private DlcService dlcService;
    @Autowired
    private DvdCdService dvdCdService;
    @Autowired
    private HqService hqService;
    @Autowired
    private JogoDigitalService jogoDigitalService;
    @Autowired
    private JogoTabuleiroService jogoTabuleiroService;

    /**
     * Busca todas as Item na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable,
            // item
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "tipo", required = false) String tipo,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "emprestados", required = false) boolean emprestados,
            @RequestParam(value = "repetidos", required = false) boolean repetidos,

            // dlc
            @RequestParam(value = "localizacao", required = false) String localizacao,

            // dvdcd
            @RequestParam(value = "assistidos", required = false) boolean assistidos,

            // hq
            @RequestParam(value = "editora", required = false) String editora,
            @RequestParam(value = "universo", required = false) String universo,

            // jogodigital
            @RequestParam(value = "console", required = false) String console,

            // jogotabuleiro
            @RequestParam(value = "marca", required = false) String marca) {

        List<Item> items;

        if (tipo != null && tipo != "" && tipo.equals(TipoColecao.DLC.getValor())) {

            logger.info("Get all 'Dlc' from data source");
            List<Dlc> dlcs = this.dlcService.getAllByLocalizacao(localizacao);

            List<Long> ids = new ArrayList<>();
            dlcs.forEach(dlc -> ids.add(dlc.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado,
                    emprestados, ids);

        } else if (tipo != null && tipo != "" && tipo.equals(TipoColecao.DVDCD.getValor())) {

            logger.info("Get all 'DvdCd' from data source");
            List<DvdCd> dvdCds = this.dvdCdService.getAllByAssistidos(assistidos);

            List<Long> ids = new ArrayList<>();
            dvdCds.forEach(dvdcd -> ids.add(dvdcd.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado,
                    emprestados, ids);

        } else if (tipo != null && tipo != "" && tipo.equals(TipoColecao.HQ.getValor())) {

            logger.info("Get all 'Hq' from data source");
            List<Hq> hqs = this.hqService.getAllByEditoraAndUniverso(editora, universo);

            List<Long> ids = new ArrayList<>();
            hqs.forEach(hq -> ids.add(hq.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado,
                    emprestados, ids);

        } else if (tipo != null && tipo != "" && tipo.equals(TipoColecao.JOGODIGITAL.getValor())) {

            logger.info("Get all 'JogoDigital' from data source");
            List<JogoDigital> jogoDigitals = this.jogoDigitalService.getAllByConsole(console);

            List<Long> ids = new ArrayList<>();
            jogoDigitals.forEach(jogoDigital -> ids.add(jogoDigital.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado,
                    emprestados, ids);

        } else if (tipo != null && tipo != "" && tipo.equals(TipoColecao.JOGOTABULEIRO.getValor())) {

            logger.info("Get all 'JogoTabuleiro' from data source");
            List<JogoTabuleiro> jogoTabuleiros = this.jogoTabuleiroService.getAllByMarca(marca);

            List<Long> ids = new ArrayList<>();
            jogoTabuleiros.forEach(jogoTabuleiro -> ids.add(jogoTabuleiro.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado,
                    emprestados, ids);

        } else {

            logger.info("Get all 'Item' from data source");
            items = this.itemService.getAll();
        }

        if (repetidos) {
            items = items.stream().filter(e -> e.getQuantidade() > 1).collect(Collectors.toList());
        }

        items.forEach(item -> item.setSaga(null));
        Page<Item> pages = new PageImpl<>(items, pageable, items.size());
        logger.info("Get all 'Item' from data source");
        return new ResponseEntity<>(pages, HttpStatus.OK);
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
        List<Item> itens = this.itemService.getWishListItems();
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
        logger.info("Find 'Item' Id: {} on data source", id);
        this.itemService.verifyIfExists(id);

        Optional<Item> item = this.itemService.getById(id);
        item.get().getSaga().setItems(null);
        logger.info("Searching for a 'Item' in the data source");

        return new ResponseEntity<Optional>(item, HttpStatus.OK);
    }
}
