package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.DlcDto;
import web.santiago.gcp.entities.Dlc;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.DlcService;
import web.santiago.gcp.services.ItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade Dlc
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/dlc")
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
     * Renderiza a view de criar a entidade Dlc
     *
     * @return View 'dlc-save'
     */
    @GetMapping("/create")
    public String create(Model model) {

        List<Item> jogos = this.itemService.getAllByItemTipo(TipoColecao.JOGODIGITAL);

        model.addAttribute("jogos", jogos);
        model.addAttribute(TipoColecao.DLC.getValor(), new DlcDto());

        return "dlc-save";
    }

    /**
     * Renderiza a view de atualizar a entidade Dlc
     *
     * @param id    Identificar da entidade Dlc a ser modificada
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'dlc-save'
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {

    	logger.info("Find 'Dlc' Id: {} on data source", id);
        Optional<Dlc> dlc = this.dlcService.getById(id);
        if (!dlc.isPresent()) {
        	logger.error("'Dlc' Id: {} not found", id);
            return "not-found";
        }

        logger.info("Find 'Item' related with 'Dlc' on data source");
        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.DLC.getValor());
        if (!item.isPresent()) {
        	logger.error("'Item'not found");
            return "not-found";
        }

        DlcDto dto = this.dlcService.createDtoFromItemDlc(item.get(), dlc.get());
        model.addAttribute(TipoColecao.DLC.getValor(), dto);

        List<Item> jogos = this.itemService.getAllByItemTipo(TipoColecao.JOGODIGITAL);
        model.addAttribute("jogos", jogos);

        return "dlc-save";
    }

    /**
     * Salva ou atualiza uma Dlc e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dlc") DlcDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "dlc-save";
        }

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
        
        this.itemService.save(dto);

        return "redirect:/item";
    }

    /**
     * Deleta uma Dlc e um Item da base de dados
     *
     * @param id Identificar da entidade Dlc a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

    	logger.info("Deleting 'Dlc' Id:{} from data source", id);
        this.dlcService.delete(id);
        
        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return "item-index";
    }
}
