package web.santiago.gcp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.santiago.gcp.dtos.JogoDigitalDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.repositories.JogoDigitalRepository;

import java.util.List;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade JogoDigital e o repositorio da entidade JogoDigital
 * @author Santiago Brothers
 */
@Service
public class JogoDigitalService extends BaseService<JogoDigital, JogoDigitalDto> {
	
	private static final Logger logger = LoggerFactory.getLogger(JogoDigitalService.class);

    @Autowired
    public JogoDigitalService(JogoDigitalRepository jogoDigitalRepository) {
        super(jogoDigitalRepository);
    }

    /**
     * Executa um cast do objeto repositorio generico da super classe para o tipo especifico do servico
     *
     * @return Repositorio especifico do servico
     */
    private JogoDigitalRepository getRepository() {
        return (JogoDigitalRepository) this.repository;
    }

    /**
     * Recupera todos os Jogos Digitais baseado em seu console
     * @param console Console a ser buscado
     * @return Lista JogoDigital
     */
    public List<JogoDigital> getAllByConsole(String console) {
        if(console != null && console != "")
            return this.getRepository().findAllByConsole(console);

        return this.getRepository().findAll();
    }

    /**
     * Transforma uma Dto JogoDigital em Entidade JogoDigital
     *
     * @param dto Dto a ser transformada em JogoDigital
     * @return Entity JogoDigital
     */
    @Override
    public JogoDigital mapper(JogoDigitalDto dto) {

    	logger.info("Mapping 'JogoDigitalDto' to 'JogoDigital'");
    	
        JogoDigital jogoDigital = new JogoDigital(dto.getConsole(), dto.isFinalizado());

        if (dto.getItemId() != 0)
            jogoDigital.setId(dto.getItemId());

        return jogoDigital;
    }

    /**
     * Transforma uma Entidade JogoDigital e Item em um Dto JogoDigital
     * @param item Entidade Item a ser mapeada para Dto
     * @param jogoDigital Entidade JogoDigital a ser mapeada para Dto
     * @return JogoDigitalDto Resultado
     */
    public JogoDigitalDto createDtoFromItemJogoDigital(Item item, JogoDigital jogoDigital) {

        JogoDigitalDto dto = new JogoDigitalDto();
        dto.setConsole(jogoDigital.getConsole());
        dto.setFinalizado(jogoDigital.isFinalizado());

        this.maperItemToDto(item, dto);
        return dto;
    }
}
