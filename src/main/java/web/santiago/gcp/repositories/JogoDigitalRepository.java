package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.JogoDigital;

import java.util.List;

/**
 * Interface de comunicação com a base de dados JogoDigital
 * @author Santiago Brothers
 */
@Repository
public interface JogoDigitalRepository extends JpaRepository<JogoDigital, Long> {

    /**
     * Executa uma busca para recuperar todos os Jogos Digitais baseado em um Console
     * @param console Console a ser buscado
     * @return Lista Jogo Digital
     */
    List<JogoDigital> findAllByConsole(String console);
}
