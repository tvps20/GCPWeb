package web.santiago.gcp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.AmigoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.services.AmigoService;

import java.util.List;
import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade Amigo
 */
@Controller
@RequestMapping("/amigo")
public class AmigoController {

    /**
     * Servico responsavel por interagir com a base de dados da entidade Amigo
     */
    @Autowired
    private AmigoService amigoService;

    /**
     * Renderiza a view de listagem da entidade Amigo
     *
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'amigo-index'
     */
    @GetMapping
    public String index(Model model) {

        List<Amigo> amigos = this.amigoService.getAll();
        model.addAttribute("amigos", amigos);

        return "amigo-index";
    }

    /**
     * Renderiza a view de criar a entidade Amigo
     *
     * @return View 'amigo-save'
     */
    @GetMapping("/create")
    public String create() {
        return "amigo-save";
    }

    /**
     * Renderiza a view de atualizar a entidade Amigo
     *
     * @param id    Identificar da entidade Amigo a ser modificada
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'amigo-save'
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {

        Optional<Amigo> entity = this.amigoService.getById(id);

        if (!entity.isPresent()) {
            return "not-found";
        }

        model.addAttribute("amigo", entity.get());
        return "amigo-save";
    }

    /**
     * Salva ou atualiza um Amigo a base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'amigo-index'
     */
    @PostMapping("/save")
    public String save(@ModelAttribute AmigoDto dto) {

        Amigo entity = this.amigoService.save(dto);
        return "amigo-index";
    }

    /**
     * Deleta um Amigo da base de dados
     *
     * @param id Identificar da entidade Amigo a ser deletado
     * @return View 'amigo-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        this.amigoService.delete(id);
        return "amigo-index";
    }
}
