package web.santiago.gcp.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web.santiago.gcp.dtos.UserDto;
import web.santiago.gcp.entities.User;
import web.santiago.gcp.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("v1/user")
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
     * Busca todas as Sagas na base de dados
     *
     * @param pageable objeto paginável
     * @return ResponseEntity<?>
     */
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        logger.info("Get all 'User' from data source");
        return new ResponseEntity<>(this.userService.listAll(pageable), HttpStatus.OK);
    }

    /**
     * Salva ou atualiza um Saga e um Item na base de dados
     * @param dto Objeto de transferencia de dados enviado pela view
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserDto dto) {
        if (dto.getId() != 0)
            logger.info("Updating 'User' Id: {} on data source", dto.getId());
        else
            logger.info("Creating new 'User' on data source");

        String encryptPwd = this.passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encryptPwd);

        User entity = this.userService.save(dto);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    /**
     * Destroi a variavel de sessão e desloga do sistema
     * @param session variavel de sessão do sistema
     * @return View 'login'
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        logger.info("Logout of sistem");
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/livreAcesso")
    public ResponseEntity<?> liveAcesso(){
        return new ResponseEntity<>("Rota de acesso livre.", HttpStatus.OK);
    }
}
