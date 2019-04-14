package web.santiago.gcp.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Define as informações enviadas pela view para o controller da entidade Emprestimo
 * @author Santiago Brothers
 */
@Data
public class EmprestimoDto extends BaseDto {

    @NotNull
    private long item;

    @NotNull
    private long amigo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date devolucao;

    private boolean devolvido;

    public EmprestimoDto() {

    }

    public EmprestimoDto(long item) {
        this.item = item;
    }
}
