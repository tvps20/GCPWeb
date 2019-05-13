package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.santiago.gcp.entities.Emprestimo;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.services.EmprestimoService;
import web.santiago.gcp.services.ItemService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Define o layout inicial da aplicação
 *
 * @author Santiago Brothers
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final ItemService itemService;
    private final EmprestimoService emprestimoService;

    @Autowired
    public HomeController(ItemService itemService, EmprestimoService emprestimoService) {
        this.itemService = itemService;
        this.emprestimoService = emprestimoService;
    }

    /**
     * Renderiza a View de apresentação da aplicação
     *
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'home'
     */
    @GetMapping("/")
    public String homePage(Model model) {

        List<Emprestimo> emprestimos = this.emprestimoService.getAllEmprestimoAbertos();
        List<Item> wishlist = this.itemService.getWishListItems();
        Optional<BigDecimal> result = wishlist.stream().map(item -> item.getPreco()).reduce((accumulator, next) -> accumulator.add(next));
        BigDecimal total = result.isPresent() ? result.get() : new BigDecimal(0);


        model.addAttribute("appName", "gcp");
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("emprestimos", emprestimos);
        model.addAttribute("wishlistSum", total);

        return "home/home";
    }

    @GetMapping("/home/sobre")
    public String sobrePage() {
        return "sobre/sobre";
    }
}
