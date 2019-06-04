package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.JogoTabuleiro;

/**
 * Interface de comunicação com a base de dados JogoTabuleiro
 */
@Repository
public interface JogoTabuleiroRepository extends JpaRepository<JogoTabuleiro, Long> {
}
