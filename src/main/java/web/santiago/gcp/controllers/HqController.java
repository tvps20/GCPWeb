package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.HqDto;
import web.santiago.gcp.entities.Hq;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.HqService;
import web.santiago.gcp.services.ItemService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade Hq
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/hq")
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
     * Renderiza a view de criar a entidade Hq
     *
     * @return View 'hq-save'
     */
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(TipoColecao.HQ.getValor(), new HqDto());
        return "hq-save";
    }

    /**
     * Renderiza a view de atualizar a entidade Hq
     *
     * @param id    Identificar da entidade Hq a ser modificada
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'hq-save'
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {

    	logger.info("Find 'Hq' Id: {} on data source", id);
        Optional<Hq> hq = this.hqService.getById(id);
        if (!hq.isPresent()) {
        	logger.error("'Hq' Id: {} not found", id);
            return "not-found";
        }

        logger.info("Find 'Item' related with 'Hq' on data source");
        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.HQ.getValor());
        if (!item.isPresent()) {
        	logger.error("'Item'not found");
            return "not-found";
        }

        HqDto dto = this.hqService.createDtoFromItemHq(item.get(), hq.get());
        model.addAttribute(TipoColecao.HQ.getValor(), dto);

        return "hq-save";
    }

    /**
     * Salva ou atualiza uma Hq e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("hq") HqDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "hq-save";
        }

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
        

        this.itemService.save(dto);

        return "redirect:/item";
    }

    /**
     * Deleta uma Hq e um Item da base de dados
     *
     * @param id Identificar da entidade Hq a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

    	logger.info("Deleting 'Hq' Id:{} from data source", id);
        this.hqService.delete(id);
        
        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return "redirect:/item";
    }
}
