package web.santiago.gcp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.UserDto;
import web.santiago.gcp.entities.User;
import web.santiago.gcp.repositories.UserRepository;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade User e o repositorio da entidade User
 *
 * @author Santiago Brothers
 */
@Service
public class UserService extends BaseService<User, UserDto> {

    private static final Logger logger = LoggerFactory.getLogger(AmigoService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

    /**
     * Transforma uma Dto User em Entidade User
     *
     * @param dto Dto a ser transformada em User
     * @return Entity User
     */
    @Override
    public User mapper(UserDto dto) {

        logger.info("Mapping 'UserDto' to 'User'");

        User entity = new User(dto.getUsername(), dto.getPassword());

        if (dto.getId() != 0)
            entity.setId(dto.getId());

        return entity;
    }
}
