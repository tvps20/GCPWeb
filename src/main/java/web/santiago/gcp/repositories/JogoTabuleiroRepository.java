package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.JogoTabuleiro;

import java.util.List;

/**
 * Interface de comunicação com a base de dados JogoTabuleiro
 * @author Santiago Brothers
 */
@Repository
public interface JogoTabuleiroRepository extends JpaRepository<JogoTabuleiro, Long> {

    /**
     * Executa uma busca no banco baseado na marca do jogo
     * @param marca Filtro por Marca
     * @return Lista Jogo Tabuleiro
     */
    List<JogoTabuleiro> findAllByMarca(String marca);
}
