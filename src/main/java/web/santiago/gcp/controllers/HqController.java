package web.santiago.gcp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.HqDto;
import web.santiago.gcp.entities.Hq;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.HqService;
import web.santiago.gcp.services.ItemService;

import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade Hq
 */
@Controller
@RequestMapping("/hq")
public class HqController {

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
    public String create() {
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

        Optional<Hq> hq = this.hqService.getById(id);
        if (!hq.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.HQ.getValor(), hq);

        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.HQ.getValor());
        if (!item.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.ITEM.getValor(), item);

        return "hq-save";
    }

    /**
     * Salva ou atualiza uma Hq e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@ModelAttribute HqDto dto) {

        Hq hqEntity = this.hqService.save(dto);

        dto.setItemId(hqEntity.getId());
        dto.setTipo("hq");

        Item itemEntity = this.itemService.save(dto);

        return "item-index";
    }

    /**
     * Deleta uma Hq e um Item da base de dados
     *
     * @param id Identificar da entidade Hq a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        this.hqService.delete(id);
        this.itemService.deleteByItemId(id);

        return "item-index";
    }
}
