package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.repositories.JogoDigitalRepository;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade JogoDigital e o repositorio da entidade JogoDigital
 */
@Service
public class JogoDigitalService extends BaseService<JogoDigital, JogoDigitalDto> {

    @Autowired
    public JogoDigitalService(JogoDigitalRepository jogoDigitalRepository) {
        super(jogoDigitalRepository);
    }

    /**
     * Transforma uma Dto JogoDigital em Entidade JogoDigital
     *
     * @param dto Dto a ser transformada em JogoDigital
     * @return Entity JogoDigital
     */
    @Override
    public JogoDigital mapper(JogoDigitalDto dto) {

        JogoDigital jogoDigital = new JogoDigital(dto.getConsole(), dto.isFinalizado());

        return jogoDigital;
    }
}
