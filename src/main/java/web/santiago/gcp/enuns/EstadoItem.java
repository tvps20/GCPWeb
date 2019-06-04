package web.santiago.gcp.enuns;

/**
 * Define os possiveis estados dos items
 *
 * @author Santiago Brothers
 */
public enum EstadoItem {
    NOVO("Novo"), RESTAURADO("Restaurado"), REGULAR("Regular"), AVARIADO("Avariado"), DANIFICADO("Danificado");

    /**
     * Guarda o valor do estado
     */
    private String estado;

    /**
     * Seta o valor do Estado
     *
     * @param valor Valor a ser guardado na enumeração
     */
    EstadoItem(String valor) {
        this.estado = valor;
    }

    /**
     * Retorna o valor do Estado
     *
     * @return Valor guardado na enumeração
     */
    public String getEstado() {
        return this.estado;
    }
}
