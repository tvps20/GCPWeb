package web.santiago.gcp.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Define as informações enviadas pela view para o controller da entidade Emprestimo
 *
 * @author Santiago Brothers
 */
@Data
public class EmprestimoDto extends BaseDto {

    @NotNull
    private long item;

    @NotNull
    @Min(value = 1, message = "Deve-se ter um Amigo cadastrado!")
    private long amigo;

    @Future
    @NotNull(message = "A data de devolução deve ser definida!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date devolucao;

    private boolean devolvido;

    public EmprestimoDto() {

    }

    public EmprestimoDto(long item) {
        this.item = item;
    }


}
