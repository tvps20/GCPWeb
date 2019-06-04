package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.DvdCd;

import java.util.List;

/**
 * Interface de comunicação com a base de dados DvdCd
 *
 * @author Santiago Brothers
 */
@Repository
public interface DvdCdRepository extends JpaRepository<DvdCd, Long> {

    /**
     * Executa uma busca por Dvds/Cds que ja foram assistidos ou não
     *
     * @param assistidos Booleano para assistidos ou não assistidos
     * @return Lista de DvdCd
     */
    List<DvdCd> findAllByAssistido(boolean assistidos);
}
