package web.santiago.gcp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Define o layout inicial da aplicação
 */
@Controller
public class HomeController {

    /**
     * Renderiza a View de apresentação da aplicação
     *
     * @param model Thymeleaf Model para passar dados do controller para view
     * @return View 'home'
     */
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", "gcp");

        return "home";
    }
}
