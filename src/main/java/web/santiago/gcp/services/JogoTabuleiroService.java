package web.santiago.gcp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.JogoTabuleiroDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoTabuleiro;
import web.santiago.gcp.repositories.JogoTabuleiroRepository;

import java.util.List;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade JogoTabuleiro e o repositorio da entidade JogoTabuleiro
 *
 * @author Santiago Brothers
 */
@Service
public class JogoTabuleiroService extends BaseService<JogoTabuleiro, JogoTabuleiroDto> {

    private static final Logger logger = LoggerFactory.getLogger(JogoTabuleiroService.class);

    @Autowired
    public JogoTabuleiroService(JogoTabuleiroRepository jogoTabuleiroRepository) {
        super(jogoTabuleiroRepository);
    }

    /**
     * Executa um cast do objeto repositorio generico da super classe para o tipo especifico do servico
     *
     * @return Repositorio especifico do servico
     */
    private JogoTabuleiroRepository getRepository() {
        return (JogoTabuleiroRepository) this.repository;
    }

    /**
     * Recupera todos os Jogos de Tabuleiro baseado em sua marca
     *
     * @param marca Marca a ser buscada
     * @return Lista JogoTabuleiro
     */
    public List<JogoTabuleiro> getAllByMarca(String marca) {
        if (marca != null && marca != "")
            return this.getRepository().findAllByMarca(marca);

        return this.getRepository().findAll();
    }

    /**
     * Transforma uma Dto JogoTabuleiro em Entidade JogoTabuleiro
     *
     * @param dto Dto a ser transformada em JogoTabuleiro
     * @return Entity JogoTabuleiro
     */
    @Override
    public JogoTabuleiro mapper(JogoTabuleiroDto dto) {

        logger.info("Mapping 'JogoTabuleiroDto' to 'JogoTabuleiro'");

        JogoTabuleiro jogoTabuleiro = new JogoTabuleiro(dto.getMarca());

        if (dto.getItemId() != 0)
            jogoTabuleiro.setId(dto.getItemId());

        return jogoTabuleiro;
    }

    /**
     * Transforma uma Entidade JogoTabuleiro e Item em um Dto Dlc
     *
     * @param item          Entidade Item a ser mapeada para Dto
     * @param jogoTabuleiro Entidade JogoTabuleiro a ser mapeada para Dto
     * @return JogoTabuleiroDto Resultado
     */
    public JogoTabuleiroDto createDtoFromItemJogoTabuleiro(Item item, JogoTabuleiro jogoTabuleiro) {

        JogoTabuleiroDto dto = new JogoTabuleiroDto();
        dto.setMarca(jogoTabuleiro.getMarca());

        this.maperItemToDto(item, dto);
        return dto;
    }
}
