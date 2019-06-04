package web.santiago.gcp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.DvdCdDto;
import web.santiago.gcp.entities.DvdCd;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.repositories.DvdCdRepository;

import java.util.List;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade DvdCd e o repositorio da entidade DvdCd
 *
 * @author Santiago Brothers
 */
@Service
public class DvdCdService extends BaseService<DvdCd, DvdCdDto> {

    private static final Logger logger = LoggerFactory.getLogger(DvdCdService.class);

    @Autowired
    public DvdCdService(DvdCdRepository dvdCdRepository) {
        super(dvdCdRepository);
    }

    /**
     * Executa um cast do objeto repositorio generico da super classe para o tipo especifico do servico
     *
     * @return Repositorio especifico do servico
     */
    private DvdCdRepository getRepository() {
        return (DvdCdRepository) this.repository;
    }

    /**
     * Recupera todos os Dvd/Cd assistidos ou não
     *
     * @param assistidos Filtro booleano
     * @return Lista DvdCd
     */
    public List<DvdCd> getAllByAssistidos(boolean assistidos) {
        return this.getRepository().findAllByAssistido(assistidos);
    }

    /**
     * Transforma uma Dto DvdCd em Entidade DvdCd
     *
     * @param dto Dto a ser transformada em DvdCd
     * @return Entity DvdCd
     */
    @Override
    public DvdCd mapper(DvdCdDto dto) {

        logger.info("Mapping 'DvdCdDto' to 'DvdCd'");

        DvdCd dvdcd = new DvdCd(dto.getMarca(), dto.getConteudo(), dto.isAssistido());

        if (dto.getItemId() != 0)
            dvdcd.setId(dto.getItemId());

        return dvdcd;
    }

    /**
     * Transforma uma Entidade DvdCd e Item em um Dto DvdCd
     *
     * @param item  Entidade Item a ser mapeada para Dto
     * @param dvdCd Entidade DvdCd a ser mapeada para Dto
     * @return DvdCdDto Resultado
     */
    public DvdCdDto createDtoFromItemDvdCd(Item item, DvdCd dvdCd) {

        DvdCdDto dto = new DvdCdDto();
        dto.setMarca(dvdCd.getMarca());
        dto.setConteudo(dvdCd.getConteudo());
        dto.setAssistido(dvdCd.isAssistido());

        this.maperItemToDto(item, dto);
        return dto;
    }
}
