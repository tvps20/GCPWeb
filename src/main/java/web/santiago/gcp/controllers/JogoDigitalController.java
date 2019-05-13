package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.JogoDigitalService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade JogoDigital
 *
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/jogodigital")
public class JogoDigitalController {

    private static final Logger logger = LoggerFactory.getLogger(JogoDigitalController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade JogoDigital
     */
    @Autowired
    private JogoDigitalService jogoDigitalService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Renderiza a view de criar a entidade JogoDigital
     *
     * @return View 'jogodigital-save'
     */
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(TipoColecao.JOGODIGITAL.getValor(), new JogoDigitalDto());
        return "jogodigital/jogodigital-save";
    }

    /**
     * Renderiza a view de atualizar a entidade JogoDigital
     *
     * @param id    Identificar da entidade JogoDigital a ser modificada
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'jogodigital-save'
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {

        logger.info("Find 'JogoDigital' Id: {} on data source", id);
        Optional<JogoDigital> jogoDigital = this.jogoDigitalService.getById(id);
        if (!jogoDigital.isPresent()) {
            logger.error("'JogoDigital' Id: {} not found", id);
            return "not-found";
        }

        logger.info("Find 'Item' related with 'JogoDigital' on data source");
        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.JOGODIGITAL.getValor());
        if (!item.isPresent()) {
            logger.error("'Item'not found");
            return "not-found";
        }

        JogoDigitalDto dto = this.jogoDigitalService.createDtoFromItemJogoDigital(item.get(), jogoDigital.get());
        model.addAttribute(TipoColecao.JOGODIGITAL.getValor(), dto);

        return "jogodigital/jogodigital-save";
    }

    /**
     * Salva ou atualiza um JogoDigital e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("jogodigital") JogoDigitalDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "jogodigital/jogodigital-save";
        }

        if (dto.getItemId() != 0)
            logger.info("Updating 'Jogo Digital' Id: {} on data source", dto.getItemId());
        else
            logger.info("Creating new 'Jogo Digital' on data source");

        JogoDigital jogoDigitalEntity = this.jogoDigitalService.save(dto);

        dto.setItemId(jogoDigitalEntity.getId());
        dto.setTipo("jogodigital");

        if (dto.getId() != 0)
            logger.info("Updating 'Item' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Item' on data source");

        this.itemService.save(dto);

        return "redirect:/item";
    }

    /**
     * Deleta uma JogoDigital e um Item da base de dados
     *
     * @param id Identificar da entidade JogoDigital a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        logger.info("Deleting 'JogoDigital' Id:{} from data source", id);
        this.jogoDigitalService.delete(id);

        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return "redirect:/item";
    }
}
