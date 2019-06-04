package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.JogoDigital;

/**
 * Interface de comunicação com a base de dados JogoDigital
 */
@Repository
public interface JogoDigitalRepository extends JpaRepository<JogoDigital, Long> {
}
