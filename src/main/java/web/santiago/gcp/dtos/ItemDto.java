package web.santiago.gcp.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Define as propriedades bases que todos os items devem enviar aos controllers
 */
@Data
public class ItemDto extends BaseDto {

    private String titulo;
    private String estado;
    private BigDecimal preco;
    private String observacoes;
    private boolean emprestado;
    private int quantidade;

    // Ranks
    private int qtdEmprestimos;
    private float importancia;

    // Wish List Propriedades
    private boolean wishlist;
    private Date disponibilidade;
    private String url;


    // Relacionamento com os demais items do banco
    private long itemId;
    private String tipo;

    private long sagaId;

    public ItemDto() {
    }
}
