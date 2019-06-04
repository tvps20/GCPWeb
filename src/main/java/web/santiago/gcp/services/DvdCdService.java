package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.repositories.DvdCdRepository;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade DvdCd e o repositorio da entidade DvdCd
 */
@Service
public class DvdCdService extends BaseService<DvdCd, DvdCdDto> {

    @Autowired
    public DvdCdService(DvdCdRepository dvdCdRepository) {
        super(dvdCdRepository);
    }

    /**
     * Transforma uma Dto DvdCd em Entidade DvdCd
     *
     * @param dto Dto a ser transformada em DvdCd
     * @return Entity DvdCd
     */
    @Override
    public DvdCd mapper(DvdCdDto dto) {
        DvdCd dvdcd = new DvdCd(dto.getMarca(), dto.getConteudo(), dto.isAssistido());

        return dvdcd;
    }
}
