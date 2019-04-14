package web.santiago.gcp.enuns;

/**
 * Define os tipos de coleções do sistema
 * @author Santiago Brothers
 */
public enum TipoColecao {
    HQ("hq"), DLC("dlc"), DVDCD("dvdcd"), JOGODIGITAL("jogodigital"), JOGOTABULEIRO("jogotabuleiro"), ITEM("item");

    /**
     * Guarda o tipo da coleção
     */
    private String valorColecao;

    /**
     * Seta o valor da Coleção
     *
     * @param valor Valor a ser guardado na enumeração
     */
    TipoColecao(String valor) {
        this.valorColecao = valor;
    }

    /**
     * Retorna o valor da Coleção
     *
     * @return Valor guardado na enumeração
     */
    public String getValor() {
        return this.valorColecao;
    }
}
