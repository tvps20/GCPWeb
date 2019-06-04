package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.DlcDto;
import web.santiago.gcp.entities.Dlc;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.exceptions.EntityNotFoundException;
import web.santiago.gcp.repositories.DlcRepository;

import java.util.Optional;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Dlc e o repositorio da entidade Dlc
 */
@Service
public class DlcService extends BaseService<Dlc, DlcDto> {

    /**
     * Interface de comunicacao com a entidade Item
     */
    public JogoDigitalService jogoDigitalService;

    @Autowired
    public DlcService(DlcRepository dlcRepository, JogoDigitalService jogoDigitalService) {

        super(dlcRepository);
        this.jogoDigitalService = jogoDigitalService;
    }

    /**
     * Transforma uma Dto Dlc em Entidade Dlc
     *
     * @param dto Dto a ser transformada em Dlc
     * @return Entity Dlc
     */
    @Override
    public Dlc mapper(DlcDto dto) throws EntityNotFoundException {
        Dlc dlc = new Dlc(dto.getLocalizacao());

        Optional<JogoDigital> jogo = this.jogoDigitalService.getById(dto.getJogo());

        if (!jogo.isPresent()) {
            throw new EntityNotFoundException("Não encontrado relacionamento entre Dlc e JogoDigital", "JogoDigital", "Mapper DlcDto to Dlc Entity");
        }

        dlc.setJogo(jogo.get());
        return dlc;
    }
}
