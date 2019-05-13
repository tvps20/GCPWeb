package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.SagaDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.Saga;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.SagaService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Define as rotas e ações para interagir com a entidade Saga
 *
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/saga")
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
     * Renderiza a view de listagem da entidade Saga
     *
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'saga-index'
     */
    @GetMapping
    public String index(Model model) {

        logger.info("Get all 'Saga' from data source");
        List<Saga> sagas = this.sagaService.getAll();

        model.addAttribute("sagas", sagas);

        return "saga/saga-index";
    }

    /**
     * Renderiza a view de criar a entidade Saga
     *
     * @return View 'saga-save'
     */
    @GetMapping("/create")
    public String create(Model model) {

        logger.info("Get all 'Item' from data source");
        List<Item> items = this.itemService.getAll();

        model.addAttribute("saga", new SagaDto());
        model.addAttribute("listItems", items);

        return "saga/saga-save";
    }

    /**
     * Renderiza a view de atualizar a entidade Saga
     *
     * @param id    Identificar da entidade Saga a ser modificada
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'saga-save'
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {

        logger.info("Find 'Saga' Id: {} on data source", id);
        Optional<Saga> entity = this.sagaService.getById(id);

        if (!entity.isPresent()) {
            logger.error("'Saga' Id: {} nor found", id);
            return "not-found";
        }

        logger.info("Get all 'Item' from data source");
        List<Item> items = this.itemService.getAll();

        model.addAttribute("saga", entity.get());
        model.addAttribute("listItems", items);
        return "saga/saga-save";
    }

    /**
     * Salva ou atualiza um Saga a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'saga-index'
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("saga") SagaDto dto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            logger.info("Get all 'Item' from data source");
            List<Item> items = this.itemService.getAll();
            model.addAttribute("listItems", items);
            return "saga/saga-save";
        }

        if (dto.getId() != 0)
            logger.info("Updating 'Saga' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Saga' on data source");

        Saga entity = this.sagaService.save(dto);

        // remove old items from saga
        List<Item> items = this.itemService.getAllItemsPorSaga(entity.getId());
        if (items.size() != dto.getItems().size()) {
            // pegar os diferentes
            List<Item> diferentes = items.stream().filter(item -> dto.getItems().stream().anyMatch(id -> item.getId() != id)).collect(Collectors.toList());
            diferentes.forEach(item -> item.setSaga(null));
            this.itemService.saveAll(diferentes);
        }

        return "redirect:/saga";
    }

    /**
     * Deleta um Saga da base de dados
     *
     * @param id Identificar da entidade Saga a ser deletado
     * @return View 'saga-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        logger.info("Deleting 'Saga' Id:{} from data source");
        this.sagaService.delete(id);
        return "redirect:/saga";
    }
}
