package web.santiago.gcp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Define o layout inicial da aplicação
 * @author Santiago Brothers
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
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
