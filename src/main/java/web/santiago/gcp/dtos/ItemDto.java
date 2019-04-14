package web.santiago.gcp.dtos;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Define as propriedades bases que todos os items devem enviar aos controllers
 * @author Santiago Brothers
 */
@Data
public class ItemDto extends BaseDto {

    @NotNull
    @NotEmpty
    private String titulo;

    private String estado;
    private BigDecimal preco;
    private String observacoes;
    private boolean emprestado;

    @Min(1)
    private int quantidade;

    // Ranks
    private int qtdEmprestimos;

    @Max(5)
    private float importancia;

    // Wish List Propriedades
    private boolean wishlist;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date disponibilidade;
    private String url;


    // Relacionamento com os demais items do banco
    private long itemId;
    private String tipo;

    private long sagaId;

    public ItemDto() {
    }
}
