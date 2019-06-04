package web.santiago.gcp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.santiago.gcp.entities.Dlc;

import java.util.List;

/**
 * Interface de comunicação com a base de dados DlcDto
 * @author Santiago Brothers
 */
@Repository
public interface DlcRepository extends JpaRepository<Dlc, Long> {

    /**
     * Executa uma busca por Dlcs baseado em sua localização
     * @param localizacao Filtro de Localização
     * @return Lista de Dlcs
     */
    List<Dlc> findAllByLocalizacao(String localizacao);
}
