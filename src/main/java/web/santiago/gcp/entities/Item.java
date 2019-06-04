package web.santiago.gcp.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import web.santiago.gcp.enuns.EstadoItem;
import web.santiago.gcp.enuns.TipoColecao;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representa um objeto a ser guardado na coleção. Entidade generica para qualquer objeto
 * Possui propriedades para linkar suas informações as informações do seu objeto relacionado
 */
@Data
@EqualsAndHashCode(exclude = "emprestimos")
@javax.persistence.Entity
public class Item extends Entity {

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

    @ManyToOne
    @JoinColumn
    private Saga saga;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<Emprestimo> emprestimos;

    public Item(String wow, EstadoItem bom, BigDecimal bigDecimal, String item_novo, boolean b, int i, int i1, double v, boolean b1, Date date, String s, int i2, TipoColecao jogodigital, int i3, Emprestimo... emprestimos) {
        this.emprestimos = Stream.of(emprestimos).collect(Collectors.toSet());
        this.emprestimos.forEach(x -> x.setItem(this));
    }

    public Item(String titulo, String estado, BigDecimal preco, String observacoes, boolean emprestado, int quantidade, int qtdEmprestimos, float importancia, boolean wishlist, Date disponibilidade, String url, long itemId, String tipo) {
        this.titulo = titulo;
        this.estado = estado;
        this.preco = preco;
        this.observacoes = observacoes;
        this.emprestado = emprestado;
        this.quantidade = quantidade;
        this.qtdEmprestimos = qtdEmprestimos;
        this.importancia = importancia;
        this.wishlist = wishlist;
        this.disponibilidade = disponibilidade;
        this.url = url;
        this.itemId = itemId;
        this.tipo = tipo;

        this.emprestimos = new HashSet<>();
    }

    public Item() {
    }
}
