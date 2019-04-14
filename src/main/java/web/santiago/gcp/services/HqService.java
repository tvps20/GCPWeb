package web.santiago.gcp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.santiago.gcp.dtos.HqDto;
import web.santiago.gcp.entities.Hq;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.repositories.HqRepository;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Hq e o repositorio da entidade Hq
 * @author Santiago Brothers
 */
@Service
public class HqService extends BaseService<Hq, HqDto> {
	
	private static final Logger logger = LoggerFactory.getLogger(HqService.class);

    @Autowired
    public HqService(HqRepository hqRepository) {
        super(hqRepository);
    }

    /**
     * Executa um cast do objeto repositorio generico da super classe para o tipo especifico do servico
     *
     * @return Repositorio especifico do servico
     */
    private HqRepository getRepository() {
        return (HqRepository) this.repository;
    }

    /**
     * Recupera todas as Hqs baseado em sua editora e/ou universo
     * @param editora Editora a ser buscada
     * @param universo Universo a ser buscado
     * @return Lista Hq
     */
    public List<Hq> getAllByEditoraAndUniverso(String editora, String universo) {

        List<Hq> hqs = this.getRepository().findAll();

        Stream<Hq> results = hqs.stream();

        if (editora != null && editora != "")
            results = results.filter(hq -> hq.getEditora().equals(editora));

        if (universo != null && universo != "")
            results = results.filter(hq -> hq.getUniverso().equals(universo));

        return results.collect(Collectors.toList());
    }

    /**
     * Transforma uma Dto Hq em Entidade Hq
     *
     * @param dto Dto a ser transformada em Hq
     * @return Entity Hq
     */
    @Override
    public Hq mapper(HqDto dto) {

    	logger.info("Mapping 'HqDto' to 'Hq");
    	
        Hq hq = new Hq(dto.getNumero(), dto.getEditora(), dto.getUniverso(), dto.getSaga());

        if (dto.getItemId() != 0)
            hq.setId(dto.getItemId());

        return hq;
    }

    /**
     * Transforma uma Entidade Hq e Item em um Dto Hq
     * @param item Entidade Item a ser mapeada para Dto
     * @param hq Entidade Hq a ser mapeada para Dto
     * @return HqDto Resultado
     */
    public HqDto createDtoFromItemHq(Item item, Hq hq) {

        HqDto dto = new HqDto();

        dto.setEditora(hq.getEditora());
        dto.setNumero(hq.getNumero());
        dto.setSaga(hq.getSaga());
        dto.setUniverso(hq.getUniverso());

        this.maperItemToDto(item, dto);
        return dto;
    }
}
