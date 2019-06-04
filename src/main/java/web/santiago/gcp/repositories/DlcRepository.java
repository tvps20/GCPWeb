package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Dlc;

/**
 * Interface de comunicação com a base de dados DlcDto
 */
@Repository
public interface DlcRepository extends JpaRepository<Dlc, Long> {
}
