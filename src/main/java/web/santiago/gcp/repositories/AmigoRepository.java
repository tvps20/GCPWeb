package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Amigo;

/**
 * Interface de comunicação com a base de dados Amigo
 */
@Repository
public interface AmigoRepository extends JpaRepository<Amigo, Long> {

}
