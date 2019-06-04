package web.santiago.gcp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


public class SagaDto extends BaseDto {

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String titulo;

    @Getter
    @Setter
    private List<Long> items;
}
