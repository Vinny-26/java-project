import java.util.List;
import java.util.Optional;

public interface Repositorio<T extends Identificavel<ID>, ID>
{
    void salvar(T entidade);
    Optional<T> buscarPorId(ID id);
    List<T> listarTodos();
    void deletarPorId(ID id);
}
