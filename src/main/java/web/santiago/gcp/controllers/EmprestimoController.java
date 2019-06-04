package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.EmprestimoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.entities.Emprestimo;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.services.AmigoService;
import web.santiago.gcp.services.EmprestimoService;
import web.santiago.gcp.services.ItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade Emprestimo
 *
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/emprestimo")
public class EmprestimoController {

    private static final Logger logger = LoggerFactory.getLogger(AmigoController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade Emprestimo
     */
    private final EmprestimoService emprestimoService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Amigo
     */
    private final AmigoService amigoService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    private final ItemService itemService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService, AmigoService amigoService, ItemService itemService) {
        this.emprestimoService = emprestimoService;
        this.amigoService = amigoService;
        this.itemService = itemService;
    }

    /**
     * Renderiza a view de emprestar um item
     *
     * @param id Item a ser emprestado
     * @return
     */
    @GetMapping("/{id}")
    public String index(@PathVariable long id, Model model) {

        List<Amigo> amigos = this.amigoService.getAll();
        model.addAttribute("amigos", amigos);

        Optional<Item> item = this.itemService.getById(id);
        if (!item.isPresent()) {
            logger.error("Item not found. Id: {}", id);
            return "not-found";
        }

        model.addAttribute("item", item.get());
        model.addAttribute("emprestimo", new EmprestimoDto(item.get().getId()));

        return "emprestimo/emprestimo-index";
    }

    /**
     * Salva um novo emprestimo no banco de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("emprestimo") EmprestimoDto dto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<Amigo> amigos = this.amigoService.getAll();
            Optional<Item> item = this.itemService.getById(dto.getItem());
            model.addAttribute("amigos", amigos);
            model.addAttribute("item", item.get());

            return "emprestimo/emprestimo-index";
        }

        logger.info("Creating new 'Emprestimo' on data source");

        this.emprestimoService.save(dto);
        this.itemService.emprestarItem(dto.getItem());

        return "redirect:/item";
    }

    @GetMapping("/devolver/{id}")
    public String devolver(@PathVariable long id, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "returnUrl", required = false) String returnUrl) {

        Emprestimo emprestimo;
        Optional<Emprestimo> entity;

        if (type != null && type.equals("item")) {
            entity = this.emprestimoService.getEmprestimoNaoDevolvidoByItemId(id);
        } else {
            entity = this.emprestimoService.getById(id);
        }

        if (!entity.isPresent()) {
            logger.error("Emprestimo not found. Id: {}", id);
            return "not-found";
        }

        emprestimo = entity.get();
        if (!emprestimo.isDevolvido()) {
            this.emprestimoService.devolver(emprestimo);
        }

        if (returnUrl != null)
            return "redirect:/" + returnUrl;

        return "redirect:/";
    }
}
