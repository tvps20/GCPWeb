package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.dtos.JogoTabuleiroDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoTabuleiro;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.JogoTabuleiroService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade JogoTabuleiro
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/jogotabuleiro")
public class JogoTabuleiroController {
	
	private static final Logger logger = LoggerFactory.getLogger(JogoTabuleiroController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade JogoTabuleiro
     */
    @Autowired
    private JogoTabuleiroService jogoTabuleiroService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Renderiza a view de criar a entidade JogoTabuleiro
     *
     * @return View 'jogotabuleiro-save'
     */
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(TipoColecao.JOGOTABULEIRO.getValor(), new JogoTabuleiroDto());
        return "jogotabuleiro-save";
    }

    /**
     * Renderiza a view de atualizar a entidade JogoTabuleiro
     *
     * @param id    Identificar da entidade JogoTabuleiro a ser modificada
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'jogotabuleiro-save'
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {

    	logger.info("Find 'JogoTabuleiro' Id: {} on data source", id);
        Optional<JogoTabuleiro> jogoTabuleiro = this.jogoTabuleiroService.getById(id);
        if (!jogoTabuleiro.isPresent()) {
        	logger.error("'JogoTabuleiro' Id: {} not found", id);
            return "not-found";
        }

        logger.info("Find 'Item' related with 'JogoTabuleiro' on data source");
        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.JOGOTABULEIRO.getValor());
        if (!item.isPresent()) {
        	logger.error("'Item'not found");
            return "not-found";
        }

        JogoTabuleiroDto dto = this.jogoTabuleiroService.createDtoFromItemJogoTabuleiro(item.get(), jogoTabuleiro.get());
        model.addAttribute(TipoColecao.JOGOTABULEIRO.getValor(), dto);

        return "jogotabuleiro-save";
    }

    /**
     * Salva ou atualiza um JogoTabuleiro e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("jogotabuleiro") JogoTabuleiroDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "jogotabuleiro-save";
        }

    	if (dto.getItemId() != 0)
    		logger.info("Updating 'JogoTabuleiro' Id: {} on data source", dto.getItemId());
    	else 
    		logger.info("Creating new 'JogoTabuleiro' on data source");

        JogoTabuleiro jogoTabuleiroEntity = this.jogoTabuleiroService.save(dto);

        dto.setItemId(jogoTabuleiroEntity.getId());
        dto.setTipo("jogotabuleiro");
        
        if (dto.getId() != 0)
    		logger.info("Updating 'Item' Id: {} on data source", dto.getId());
    	else 
    		logger.info("Creating new 'Item' on data source");

        this.itemService.save(dto);

        return "redirect:/item";
    }

    /**
     * Deleta uma JogoTabuleiro e um Item da base de dados
     *
     * @param id Identificar da entidade JogoTabuleiro a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

    	logger.info("Deleting 'JogoTabuleiro' Id:{} from data source", id);
        this.jogoTabuleiroService.delete(id);
        
        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return "item-index";
    }
}
