package web.santiago.gcp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.DvdCdService;
import web.santiago.gcp.services.ItemService;

import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade DvdCd
 */
@Controller
@RequestMapping("/dvdcd")
public class DvdCdController {

    /**
     * Servico responsavel por interagir com a base de dados da entidade DvdCd
     */
    @Autowired
    private DvdCdService dvdCdService;

    /**
     * Servico responsavel por interagir com a base de dados da entidade Item
     */
    @Autowired
    private ItemService itemService;

    /**
     * Renderiza a view de criar a entidade DvdCd
     *
     * @return View 'dvdcd-save'
     */
    @GetMapping("/create")
    public String create() {
        return "dvdcd-save";
    }

    /**
     * Renderiza a view de atualizar a entidade DvdCd
     *
     * @param id    Identificar da entidade DvdCd a ser modificada
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'dvdcd-save'
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {

        Optional<DvdCd> dvdcd = this.dvdCdService.getById(id);
        if (!dvdcd.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.DVDCD.getValor(), dvdcd);

        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.DVDCD.getValor());
        if (!item.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.ITEM.getValor(), item);

        return "dvdcd-save";
    }

    /**
     * Salva ou atualiza um DvdCd e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@ModelAttribute DvdCdDto dto) {

        DvdCd dvdCdEntity = this.dvdCdService.save(dto);

        dto.setItemId(dvdCdEntity.getId());
        dto.setTipo("dvdcd");

        Item itemEntity = this.itemService.save(dto);

        return "item-index";
    }

    /**
     * Deleta um DvdCd e um Item da base de dados
     *
     * @param id Identificar da entidade DvdCd a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        this.dvdCdService.delete(id);
        this.itemService.deleteByItemId(id);

        return "item-index";
    }
}
