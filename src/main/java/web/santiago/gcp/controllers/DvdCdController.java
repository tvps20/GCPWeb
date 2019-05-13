package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.services.DvdCdService;
import web.santiago.gcp.services.ItemService;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Define as rotas e ações para interagir com a entidade DvdCd
 *
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/dvdcd")
public class DvdCdController {

    private static final Logger logger = LoggerFactory.getLogger(DvdCdController.class);

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
    public String create(Model model) {
        model.addAttribute(TipoColecao.DVDCD.getValor(), new DvdCdDto());
        return "dvdcd/dvdcd-save";
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

        logger.info("Find 'DvdCd' Id: {} on data source", id);
        Optional<DvdCd> dvdcd = this.dvdCdService.getById(id);
        if (!dvdcd.isPresent()) {
            logger.error("'DvdCd' Id: {} not found", id);
            return "not-found";
        }

        logger.info("Find 'Item' related with 'DvdCd' on data source");
        Optional<Item> item = this.itemService.getByItemIdAndTipo(id, TipoColecao.DVDCD.getValor());
        if (!item.isPresent()) {
            logger.error("'Item'not found");
            return "not-found";
        }

        DvdCdDto dto = this.dvdCdService.createDtoFromItemDvdCd(item.get(), dvdcd.get());
        model.addAttribute(TipoColecao.DVDCD.getValor(), dto);

        return "dvdcd/dvdcd-save";
    }

    /**
     * Salva ou atualiza um DvdCd e um Item na base de dados
     *
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return View 'item-index'
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dvdcd") DvdCdDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "dvdcd/dvdcd-save";
        }

        if (dto.getItemId() != 0)
            logger.info("Updating 'DvdCd' Id: {} on data source", dto.getItemId());
        else
            logger.info("Creating new 'DvdCd' on data source");

        DvdCd dvdCdEntity = this.dvdCdService.save(dto);

        dto.setItemId(dvdCdEntity.getId());
        dto.setTipo("dvdcd");

        if (dto.getId() != 0)
            logger.info("Updating 'Item' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'Item' on data source");

        this.itemService.save(dto);

        return "redirect:/item";
    }

    /**
     * Deleta um DvdCd e um Item da base de dados
     *
     * @param id Identificar da entidade DvdCd a ser deletada
     * @return View 'item-index'
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        logger.info("Deleting 'DvdCd' Id:{} from data source", id);
        this.dvdCdService.delete(id);

        logger.info("Deleting 'Item' from data source");
        this.itemService.deleteByItemId(id);

        return "redirect:/item";
    }
}
