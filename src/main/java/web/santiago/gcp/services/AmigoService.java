package web.santiago.gcp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.santiago.gcp.dtos.AmigoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.repositories.AmigoRepository;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Amigo e o repositorio da entidade Amigo
 * @author Santiago Brothers
 */
@Service
public class AmigoService extends BaseService<Amigo, AmigoDto> {
	
	private static final Logger logger = LoggerFactory.getLogger(AmigoService.class);

    @Autowired
    public AmigoService(AmigoRepository amigoRepository) {
        super(amigoRepository);
    }

    /**
     * Transforma uma Dto Amigo em Entidade Amigo
     *
     * @param dto Dto a ser transformada em Amigo
     * @return Entity Amigo
     */
    @Override
    public Amigo mapper(AmigoDto dto) {

    	logger.info("Mapping 'AmigoDto' to 'Amigo'");
    	
        Amigo entity = new Amigo(
                dto.getNome(),
                dto.getSexo(),
                dto.getParentesco(),
                dto.getEndereco(),
                dto.getTelefone()
        );

        if (dto.getId() != 0)
            entity.setId(dto.getId());

        return entity;
    }
}
