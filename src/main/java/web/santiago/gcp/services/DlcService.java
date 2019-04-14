package web.santiago.gcp.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.santiago.gcp.dtos.DlcDto;
import web.santiago.gcp.entities.Dlc;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.JogoDigital;
import web.santiago.gcp.enuns.TipoColecao;
import web.santiago.gcp.exceptions.EntityNotFoundException;
import web.santiago.gcp.repositories.DlcRepository;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Dlc e o repositorio da entidade Dlc
 * @author Santiago Brothers
 */
@Service
public class DlcService extends BaseService<Dlc, DlcDto> {
	
	private static final Logger logger = LoggerFactory.getLogger(DlcService.class);

    /**
     * Interface de comunicacao com a entidade JogoDigital
     */
    public JogoDigitalService jogoDigitalService;

    /**
     * Interface de comunicacao com a entidade Item
     */
    public ItemService itemService;

    @Autowired
    public DlcService(DlcRepository dlcRepository, JogoDigitalService jogoDigitalService, ItemService itemService) {

        super(dlcRepository);
        this.jogoDigitalService = jogoDigitalService;
        this.itemService = itemService;
    }

    /**
     * Executa um cast do objeto repositorio generico da super classe para o tipo especifico do servico
     *
     * @return Repositorio especifico do servico
     */
    private DlcRepository getRepository() {
        return (DlcRepository) this.repository;
    }

    /**
     * Recupera todas Dlcs baseado em uma localização
     * @param localizacao Localização a ser buscada
     * @return Lista Dlc
     */
    public List<Dlc> getAllByLocalizacao(String localizacao) {
        if(localizacao != null && localizacao != "")
            return this.getRepository().findAllByLocalizacao(localizacao);

        return  this.getRepository().findAll();
    }

    /**
     * Transforma uma Dto Dlc em Entidade Dlc
     *
     * @param dto Dto a ser transformada em Dlc
     * @return Entity Dlc
     */
    @Override
    public Dlc mapper(DlcDto dto) throws EntityNotFoundException {
    	
    	logger.info("Mapping 'DlcDto' to 'Dlc'");
    	
        Dlc dlc = new Dlc(dto.getLocalizacao());

        logger.info("Find 'Item' Id: {} related with 'Dlc'", dto.getJogo());
        Optional<Item> item = this.itemService.getById(dto.getJogo());

        if (!item.isPresent()) {
            logger.info("'Item'Id: {} not found", dto.getJogo());
            throw new EntityNotFoundException("Não encontrado relacionamento entre Dlc e Item", "Item", "Mapper DlcDto to Dlc Entity");
        }

        logger.info("Find 'JogoDigital' Id: {} related with 'Dlc'", item.get().getItemId());
        Optional<JogoDigital> jogo = this.jogoDigitalService.getById(item.get().getItemId());

        if (!jogo.isPresent()) {
        	logger.info("'JogoDigital'Id: {} not found", dto.getJogo());
            throw new EntityNotFoundException("Não encontrado relacionamento entre Dlc e JogoDigital", "JogoDigital", "Mapper DlcDto to Dlc Entity");
        }

        dlc.setJogo(jogo.get());
        return dlc;
    }

    /**
     * Transforma uma Entidade Dlc e Item em um Dto Dlc
     * @param item Entidade Item a ser mapeada para Dto
     * @param dlc Entidade Dlc a ser mapeada para Dto
     * @return DlcDto Resultado
     */
    public DlcDto createDtoFromItemDlc(Item item, Dlc dlc) {

        DlcDto dto = new DlcDto();
        dto.setLocalizacao(dlc.getLocalizacao());

        Optional<Item> relatedItem = this.itemService.getByItemIdAndTipo(dlc.getJogo().getId(), TipoColecao.JOGODIGITAL.getValor());
        dto.setJogo(relatedItem.get().getId());

        this.maperItemToDto(item, dto);
        return dto;
    }
}
