package excecoes;

public class SaldoInsuficienteException extends DominioException
{
    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
