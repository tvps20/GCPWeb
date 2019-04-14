package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Emprestimo;

/**
 * Interface de comunicação com a base de dados Emprestimo
 * @author Santiago Brothers
 */
@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
