package excecoes;

public class CreditoInsuficienteException extends DominioException {
    public CreditoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}
