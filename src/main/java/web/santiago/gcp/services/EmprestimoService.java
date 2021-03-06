package web.santiago.gcp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.santiago.gcp.dtos.EmprestimoDto;
import web.santiago.gcp.entities.Amigo;
import web.santiago.gcp.entities.Emprestimo;
import web.santiago.gcp.entities.Item;
import web.santiago.gcp.exceptions.EntityNotFoundException;
import web.santiago.gcp.repositories.EmprestimoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Representa a camada de comunicação entre o Controller das rotas da entidade Emprestimo e o repositorio da entidade Emprestimo
 *
 * @author Santiago Brothers
 */
@Service
public class EmprestimoService extends BaseService<Emprestimo, EmprestimoDto> {

    /**
     * Interface de comunicacao com a entidade Item
     */
    private ItemService itemService;

    /**
     * Interface de comunicacao com a entidade Amigo
     */
    private AmigoService amigoService;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository, ItemService itemService, AmigoService amigoService) {
        super(emprestimoRepository);
        this.itemService = itemService;
        this.amigoService = amigoService;
    }

    /**
     * Recupera todos os emprestimos não devolvidos até uma determinada data
     *
     * @return Lista de Emrepstimos
     */
    public List<Emprestimo> getAllEmprestimoAbertos() {
        return getRepository().findAllByDevolucaoBeforeAndDevolvido(new Date(), false);
    }

    /**
     * Atualiza um emprestimo para devolvido
     *
     * @param emprestimo Emprestimo a ser devolvido
     * @return Emprestimo atualizado
     */
    public Emprestimo devolver(Emprestimo emprestimo) {
        emprestimo.setDevolvido(true);
        emprestimo.getItem().setEmprestado(false);
        this.itemService.update(emprestimo.getItem());
        return this.repository.save(emprestimo);
    }

    /**
     * Executa uma busca por um emprestimo nao devolvido relacionado a um item
     *
     * @param itemId Item relacionado ao emprestimo
     * @return Emprestimo
     */
    public Optional<Emprestimo> getEmprestimoNaoDevolvidoByItemId(long itemId) {
        return getRepository().findByItemIdAndDevolvidoFalse(itemId);
    }

    /**
     * Executa um cast do objeto repositorio generico da super classe para o tipo especifico do servico
     *
     * @return Repositorio especifico do servico
     */
    private EmprestimoRepository getRepository() {
        return (EmprestimoRepository) this.repository;
    }

    /**
     * Transforma uma Dto Emprestimo em Entidade Emprestimo
     *
     * @param dto Dto a ser transformada em Emprestimo
     * @return Emprestimo
     */
    @Override
    public Emprestimo mapper(EmprestimoDto dto) {

        Emprestimo emprestimo = new Emprestimo(dto.getDevolucao(), dto.isDevolvido());

        Optional<Item> item = this.itemService.getById(dto.getItem());
        if (!item.isPresent()) {
            throw new EntityNotFoundException("Não encontrado relacionamento entre emprestimo e item", "Item", "Mapper EmprestimoDto to Emprestimo Entity");
        }

        emprestimo.setItem(item.get());

        Optional<Amigo> amigo = this.amigoService.getById(dto.getAmigo());
        if (!amigo.isPresent()) {
            throw new EntityNotFoundException("Não encontrado relacionamento entre emprestimo e amigo", "Amigo", "Mapper EmprestimoDto to Emprestimo Entity");
        }

        emprestimo.setAmigo(amigo.get());

        emprestimo.setDevolvido(dto.isDevolvido());
        emprestimo.setDevolucao(dto.getDevolucao());

        return emprestimo;
    }
}
