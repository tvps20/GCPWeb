package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Saga;

/**
 * Interface de comunicação com a base de dados Saga
 */
@Repository
public interface SagaRepository extends JpaRepository<Saga, Long> {
}
