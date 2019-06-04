package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.HqDto;
import web.santiago.gcp.entities.Hq;
import web.santiago.gcp.repositories.HqRepository;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Hq e o repositorio da entidade Hq
 */
@Service
public class HqService extends BaseService<Hq, HqDto> {

    @Autowired
    public HqService(HqRepository hqRepository) {
        super(hqRepository);
    }

    /**
     * Transforma uma Dto Hq em Entidade Hq
     *
     * @param dto Dto a ser transformada em Hq
     * @return Entity Hq
     */
    @Override
    public Hq mapper(HqDto dto) {

        Hq hq = new Hq(dto.getNumero(), dto.getEditora(), dto.getUniverso(), dto.getSaga());

        return hq;
    }
}
