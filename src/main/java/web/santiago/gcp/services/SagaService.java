package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.SagaDto;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.entities.Saga;
import web.santiago.gcp.repositories.SagaRepository;

import java.util.List;

@Service
public class SagaService extends BaseService<Saga, SagaDto> {

    private final ItemService itemService;

    @Autowired
    public SagaService(SagaRepository sagaRepository, ItemService itemRepository) {
        super(sagaRepository);
        this.itemService = itemRepository;
    }

    @Override
    public Saga mapper(SagaDto dto) {

        Saga saga = new Saga(dto.getTitulo());

        if (dto.getId() != 0)
            saga.setId(dto.getId());

        List<Item> items = this.itemService.getAllItemsIn(dto.getItems());
        items.forEach(item -> item.setSaga(saga));
        saga.setItems(items);

        return saga;
    }
}
