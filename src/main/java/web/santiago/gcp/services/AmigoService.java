package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.AmigoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.repositories.AmigoRepository;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Amigo e o repositorio da entidade Amigo
 */
@Service
public class AmigoService extends BaseService<Amigo, AmigoDto> {

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

        Amigo entity = new Amigo(
                dto.getNome(),
                dto.getSexo(),
                dto.getParentesco(),
                dto.getEndereco(),
                dto.getTelefone()
        );

        return entity;
    }
}
