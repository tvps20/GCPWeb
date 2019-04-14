package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.santiago.gcp.entities.*;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Define as rotas e ações para interagir com a entidade Item
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    private final ItemService itemService;
    private final DlcService dlcService;
    private final DvdCdService dvdCdService;
    private final HqService hqService;
    private final JogoDigitalService jogoDigitalService;
    private final JogoTabuleiroService jogoTabuleiroService;

    @Autowired
    public ItemController(
            ItemService itemService,
            DlcService dlcService,
            DvdCdService dvdCdService,
            HqService hqService,
            JogoDigitalService jogoDigitalService,
            JogoTabuleiroService jogoTabuleiroService
    ) {
        this.itemService = itemService;
        this.dlcService = dlcService;
        this.dvdCdService = dvdCdService;
        this.hqService = hqService;
        this.jogoDigitalService = jogoDigitalService;
        this.jogoTabuleiroService = jogoTabuleiroService;
    }

    /**
     * Renderiza a view de listagem da entidade Item
     *
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'item-index'
     */
    @GetMapping
    public String index(
            Model model,

            // item
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "tipo", required = false) String tipo,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "emprestados", required = false) boolean emprestados,

            // dlc
            @RequestParam(value = "localizacao", required = false) String localizacao,

            //dvdcd
            @RequestParam(value = "assistidos", required = false) boolean assistidos,

            //hq
            @RequestParam(value = "editora", required = false) String editora,
            @RequestParam(value = "universo", required = false) String universo,

            // jogodigital
            @RequestParam(value = "console", required = false) String console,

            // jogotabuleiro
            @RequestParam(value = "marca", required = false) String marca
    ) {

        List<Item> items;

        if (tipo != null && tipo != "" && tipo.equals(TipoColecao.DLC.getValor())) {

            logger.info("Get all 'Dlc' from data source");
            List<Dlc> dlcs = this.dlcService.getAllByLocalizacao(localizacao);

            List<Long> ids = new ArrayList<>();
            dlcs.forEach(dlc -> ids.add(dlc.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado, emprestados, ids);

        } else if (tipo != null && tipo != "" && tipo.equals(TipoColecao.DVDCD.getValor())){

            logger.info("Get all 'DvdCd' from data source");
            List<DvdCd> dvdCds = this.dvdCdService.getAllByAssistidos(assistidos);

            List<Long> ids = new ArrayList<>();
            dvdCds.forEach(dvdcd -> ids.add(dvdcd.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado, emprestados, ids);

        } else if (tipo != null && tipo != "" && tipo.equals(TipoColecao.HQ.getValor())) {

            logger.info("Get all 'Hq' from data source");
            List<Hq> hqs = this.hqService.getAllByEditoraAndUniverso(editora, universo);

            List<Long> ids = new ArrayList<>();
            hqs.forEach(hq -> ids.add(hq.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado, emprestados, ids);

        } else if (tipo != null && tipo != "" && tipo.equals(TipoColecao.JOGODIGITAL.getValor())) {

            logger.info("Get all 'JogoDigital' from data source");
            List<JogoDigital> jogoDigitals = this.jogoDigitalService.getAllByConsole(console);

            List<Long> ids = new ArrayList<>();
            jogoDigitals.forEach(jogoDigital -> ids.add(jogoDigital.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado, emprestados, ids);

        } else if (tipo != null && tipo != "" && tipo.equals(TipoColecao.JOGOTABULEIRO.getValor())) {

            logger.info("Get all 'JogoTabuleiro' from data source");
            List<JogoTabuleiro> jogoTabuleiros = this.jogoTabuleiroService.getAllByMarca(marca);

            List<Long> ids = new ArrayList<>();
            jogoTabuleiros.forEach(jogoTabuleiro -> ids.add(jogoTabuleiro.getId()));
            items = this.itemService.getAllByTituloAndTipoAndEstadoAndEmprestadoAndIds(titulo, tipo, estado, emprestados, ids);

        } else {

            logger.info("Get all 'Item' from data source");
            items = this.itemService.getAll();
        }

        model.addAttribute(TipoColecao.ITEM.getValor(), items);
        return "item-index";
    }
}
