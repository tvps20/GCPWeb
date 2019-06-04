package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Hq;

/**
 * Interface de comunicação com a base de dados Hq
 */
@Repository
public interface HqRepository extends JpaRepository<Hq, Long> {
}
