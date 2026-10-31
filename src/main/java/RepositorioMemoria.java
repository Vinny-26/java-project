import excecoes.DadosInvalidosException;

import java.util.*;

public class RepositorioMemoria<T extends Identificavel<ID>, ID> implements Repositorio<T, ID>
{
    private final Map<ID, T> mapa = new HashMap<>();

    @Override
    public void salvar(T entidade)
    {
        if (entidade == null || entidade.getId() == null)
        {
            throw new DadosInvalidosException("Não é possível salvar entidade com ID nulo ou tipo nulo");
        }
        mapa.put(entidade.getId(), entidade);
    }

    @Override
    public Optional<T> buscarPorId(ID id)
    {
        if (id == null)
        {
            return Optional.empty();
        }
        return Optional.ofNullable(mapa.get(id));
    }

    @Override
    public List<T> listarTodos()
    {
        return new ArrayList<>(mapa.values());
    }

    @Override
    public void deletarPorId(ID id)
    {
        if (id == null || !mapa.containsKey(id))
        {
            throw new DadosInvalidosException("O ID não foi encontrado para remoção");
        }
        mapa.remove(id);
    }
}
