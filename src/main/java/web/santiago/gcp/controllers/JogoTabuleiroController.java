package web.santiago.gcp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.JogoTabuleiroDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoTabuleiro;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.ItemService;
import web.santiago.gcp.services.JogoTabuleiroService;

import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade JogoTabuleiro
 */
@Controller
@RequestMapping("/jogotabuleiro")
public class JogoTabuleiroController {

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
    public String create() {
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

        Optional<JogoTabuleiro> jogoTabuleiro = this.jogoTabuleiroService.getById(id);
        if (!jogoTabuleiro.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.JOGOTABULEIRO.getValor(), jogoTabuleiro);

        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.JOGOTABULEIRO.getValor());
        if (!item.isPresent()) {
            return "not-found";
        }

        model.addAttribute(TipoColecao.ITEM.getValor(), item);

        return "jogotabuleiro-save";
    }

    /**
     * Salva ou atualiza um JogoTabuleiro e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@ModelAttribute JogoTabuleiroDto dto) {

        JogoTabuleiro jogoTabuleiroEntity = this.jogoTabuleiroService.save(dto);

        dto.setItemId(jogoTabuleiroEntity.getId());
        dto.setTipo("jogotabuleiro");

        Item itemEntity = this.itemService.save(dto);

        return "item-index";
    }

    /**
     * Deleta uma JogoTabuleiro e um Item da base de dados
     *
     * @param id Identificar da entidade JogoTabuleiro a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        this.jogoTabuleiroService.delete(id);
        this.itemService.deleteByItemId(id);

        return "item-index";
    }
}
