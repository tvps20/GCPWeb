package web.santiago.gcp.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.santiago.gcp.dtos.UserDto;
import web.santiago.gcp.entities.User;
import web.santiago.gcp.services.UserService;

import java.io.IOException;

/**
 * Define as rotas e ações para interagir com a entidade User
 * @author Santiago Brothers
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Servico responsavel por interagir com a base de dados da entidade Amigo
     */
    @Autowired
    private UserService userService;

    /**
     * Classe responsavel por criptografar a senha que será adicionada no banco.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Renderiza a view de criar a entidade User
     *
     * @return View 'user-save'
     */
	@GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("user", new UserDto());
		
        return "login/user-save";
    }

    /**
     * Salva ou atualiza um User a base de dados
     *
     * @param userDto Objeto de transferencia de dados enviado pela view
     * @return View 'raiz do sistema'
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {

	    if(!userDto.getPassword().equals(userDto.getConfirmaPassword())){
           bindingResult.addError(new FieldError("user", "invalidSenhas", "As senhas estão diferentes"));
        }

        if (bindingResult.hasErrors()) {
            return "login/user-save";
        }

        if (userDto.getId() != 0)
            logger.info("Updating 'User' Id: {} on data source", userDto.getId());
        else
            logger.info("Creating new 'User' on data source");

        String encryptPwd = this.passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptPwd);

        User entity = this.userService.save(userDto);

    	return "redirect:/";
    }

    /**
     * Destroi a variavel de sessão e desloga do sistema
     * @param session variavel de sessão do sistema
     * @return View 'login'
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("Logout of sistem");
        session.invalidate();
        return "redirect:/login";
    }
}
