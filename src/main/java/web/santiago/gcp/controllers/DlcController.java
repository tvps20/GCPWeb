package web.santiago.gcp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.DlcDto;
import web.santiago.gcp.entities.Dlc;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.DlcService;
import web.santiago.gcp.services.ItemService;

import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade Dlc
 */
@Controller
@RequestMapping("/dlc")
public class DlcController {

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
    public String create() {
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

        Optional<Dlc> dlc = this.dlcService.getById(id);
        if (!dlc.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.DLC.getValor(), dlc);

        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.DLC.getValor());
        if (!item.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.ITEM.getValor(), item);

        return "dlc-save";
    }

    /**
     * Salva ou atualiza uma Dlc e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@ModelAttribute DlcDto dto) {

        Dlc dlcEntity = this.dlcService.save(dto);

        dto.setItemId(dlcEntity.getId());
        dto.setTipo("dlc");

        Item itemEntity = this.itemService.save(dto);

        return "item-index";
    }

    /**
     * Deleta uma Dlc e um Item da base de dados
     *
     * @param id Identificar da entidade Dlc a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        this.dlcService.delete(id);
        this.itemService.deleteByItemId(id);

        return "item-index";
    }
}
