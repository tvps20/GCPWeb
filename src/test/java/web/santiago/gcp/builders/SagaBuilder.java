package web.santiago.gcp.builders;

import web.santiago.gcp.dtos.SagaDto;
import web.santiago.gcp.entities.Saga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class SagaBuilder {

    private Saga saga;
    private Collection<Saga> sagas;
    private SagaDto sagaDto;

    public static SagaBuilder mockSagaBuilder() {
        SagaBuilder builder = new SagaBuilder();
        builder.saga = new Saga("Xadrez");
        builder.saga.setId(1L);

        return builder;
    }

    public static SagaBuilder mockCollectionSagaBuilder() {
        SagaBuilder builder = new SagaBuilder();
        builder.sagas = new ArrayList<Saga>();

        for (int i = 0; i < 10; i++) {
            Saga novaSaga = new Saga("Xadrez");

            builder.sagas.add(novaSaga);
        }

        return builder;
    }

    public static SagaBuilder mockSagaDtoBuilder() {
        SagaBuilder builder = new SagaBuilder();
        builder.sagaDto = new SagaDto();
        builder.sagaDto.setTitulo("Saga Dto");
        builder.sagaDto.setId(1L);

        return builder;
    }

    // Methods
    public Saga getSaga() {
        return this.saga;
    }

    public Optional<Saga> getSagaOptional() {
        Optional<Saga> empty = Optional.of(this.saga);
        return empty;
    }

    public Optional<Saga> getSagaEmptyOptional() {
        return Optional.empty();
    }

    public Collection<Saga> getSagas() {
        return this.sagas;
    }

    public SagaDto getSagaDto() {
        return this.sagaDto;
    }
}
