package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Emprestimo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interface de comunicação com a base de dados Emprestimo
 *
 * @author Santiago Brothers
 */
@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    /**
     * Executa uma busca por todos os emprestimos a partir de uma data
     *
     * @param before    Data base para busca
     * @param devolvido Devolvidos ou Não
     * @return Lista de Emprestimos
     */
    List<Emprestimo> findAllByDevolucaoBeforeAndDevolvido(Date before, boolean devolvido);

    /**
     * Executa uma busca de um emprestimo não devolvido pelo item relacionado
     *
     * @param itemId Item relacionado ao emprestimo
     * @return Emprestimo
     */
    Optional<Emprestimo> findByItemIdAndDevolvidoFalse(Long itemId);
}
