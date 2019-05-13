package web.santiago.gcp.exceptions;

/**
 * Representa uma entidade não encontrada no banco de dados
 *
 * @author Santiago Brothers
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Cria uma nova exceção para quando uma entidade não foi encontrada no banco de dados
     *
     * @param message        Messagem de error
     * @param entityNotFound Entidade não encontrada
     * @param operation      Operação realizada quando o erro foi gerado
     */
    public EntityNotFoundException(String message, String entityNotFound, String operation) {
        super("Entity not found: " + entityNotFound + ". Operation: " + operation + ". Message: " + message);
    }
}
