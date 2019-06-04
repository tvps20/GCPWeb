package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.DvdCd;

/**
 * Interface de comunicação com a base de dados DvdCd
 */
@Repository
public interface DvdCdRepository extends JpaRepository<DvdCd, Long> {
}
