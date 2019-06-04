package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.JogoTabuleiroDto;
import web.santiago.gcp.entities.JogoTabuleiro;
import web.santiago.gcp.repositories.JogoTabuleiroRepository;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade JogoTabuleiro e o repositorio da entidade JogoTabuleiro
 */
@Service
public class JogoTabuleiroService extends BaseService<JogoTabuleiro, JogoTabuleiroDto> {

    @Autowired
    public JogoTabuleiroService(JogoTabuleiroRepository jogoTabuleiroRepository) {
        super(jogoTabuleiroRepository);
    }

    /**
     * Transforma uma Dto JogoTabuleiro em Entidade JogoTabuleiro
     *
     * @param dto Dto a ser transformada em JogoTabuleiro
     * @return Entity JogoTabuleiro
     */
    @Override
    public JogoTabuleiro mapper(JogoTabuleiroDto dto) {

        JogoTabuleiro jogoTabuleiro = new JogoTabuleiro(dto.getMarca());

        return jogoTabuleiro;
    }
}
