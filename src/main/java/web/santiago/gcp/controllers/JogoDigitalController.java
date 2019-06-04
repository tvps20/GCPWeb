package web.santiago.gcp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.JogoDigitalService;

import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade JogoDigital
 */
@Controller
@RequestMapping("/jogodigital")
public class JogoDigitalController {

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
    public String create() {
        return "jogodigital-save";
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

        Optional<JogoDigital> jogoDigital = this.jogoDigitalService.getById(id);
        if (!jogoDigital.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.JOGODIGITAL.getValor(), jogoDigital);

        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.JOGODIGITAL.getValor());
        if (!item.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.ITEM.getValor(), item);

        return "jogodigital-save";
    }

    /**
     * Salva ou atualiza um JogoDigital e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@ModelAttribute JogoDigitalDto dto) {

        JogoDigital jogoDigitalEntity = this.jogoDigitalService.save(dto);

        dto.setItemId(jogoDigitalEntity.getId());
        dto.setTipo("jogodigital");

        Item itemEntity = this.itemService.save(dto);

        return "item-index";
    }

    /**
     * Deleta uma JogoDigital e um Item da base de dados
     *
     * @param id Identificar da entidade JogoDigital a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        this.jogoDigitalService.delete(id);
        this.itemService.deleteByItemId(id);

        return "item-index";
    }
}
