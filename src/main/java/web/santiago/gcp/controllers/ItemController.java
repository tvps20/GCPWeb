package web.santiago.gcp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.ItemService;

import java.util.List;

/**
 * Define as rotas e ações para interagir com a entidade Item
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Renderiza a view de listagem da entidade Item
     *
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'item-index'
     */
    @GetMapping
    public String index(Model model) {

        List<Item> items = this.itemService.getAll();
        model.addAttribute(TipoColecao.ITEM.getValor(), items);

        return "item-index";
    }
}
