package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Hq;

import java.util.List;
import java.util.Optional;

/**
 * Interface de comunicação com a base de dados Hq
 * @author Santiago Brothers
 */
@Repository
public interface HqRepository extends JpaRepository<Hq, Long> {

}
