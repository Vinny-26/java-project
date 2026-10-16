import excecoes.DadosInvalidosException;
import excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest
{

    // Métodos auxiliares para simplificação de código(Sugestão de IA)
    private Pessoa criarPessoaPadrao()
    {
        return new Pessoa("Vinicius", 1000, "11111111111");
    }

    private Pessoa criarPessoaComSaldo(int saldoEmCentavos)
    {
        return new Pessoa("Vinicius", saldoEmCentavos, "11111111111");
    }

    // Validação do construtor
    @Test
    void criacaoValida()
    {

        Pessoa pessoa = criarPessoaPadrao();

        assertEquals("Vinicius", pessoa.getTitular());
        assertEquals(1000, pessoa.getSaldoEmCentavos());
        assertEquals("11111111111", pessoa.getCpf());
    }

    @Test
    void permiteSaldoZero()
    {
        assertDoesNotThrow(() -> criarPessoaComSaldo(0));
    }

    @Test
    void titularNuloLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa(null, 1000, "11111111111")
        );
    }

    @Test
    void titularVazioLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa(" ", 1000, "11111111111")
        );
    }

    @Test
    void cpfNuloLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", 1000, null)
        );
    }

    @Test
    void cpfVazioLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", 1000, " ")
        );
    }

    @Test
    void cpfInvalidoLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", 1000, "111.111.111-11111")
        );
    }

    @Test
    void saldoInvalidoLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", -1, "11111111111")
        );
    }

    // Validação do Equals + HashCode
    @Test
    void pessoasComMesmoCpfSaoIguais()
    {
        Pessoa pessoaA = new Pessoa("Vinicius", 1000, "11111111111");
        Pessoa pessoaB = new Pessoa("Felipe", 1500, "11111111111");

        assertEquals(pessoaA, pessoaB);
    }

    @Test
    void pessoasComCpfDiferentesNaoSaoIguais()
    {
        Pessoa pessoaA = new Pessoa("Vinicius", 1000, "11111111111");
        Pessoa pessoaB = new Pessoa("Vinicius", 1000, "22222222222");

        assertNotEquals(pessoaA, pessoaB);
    }

    @Test
    void contratoHashCode()
    {
        Pessoa pessoaA = new Pessoa("Vinicius", 1000, "11111111111");
        Pessoa pessoaB = new Pessoa("Felipe", 1500, "11111111111");

        assertEquals(pessoaA.hashCode(), pessoaB.hashCode());
    }

    @Test
    void comparacaoComNuloOuOutroTipo()
    {
        Pessoa pessoaA = criarPessoaPadrao();

        assertFalse(pessoaA.equals(null));
        assertFalse(pessoaA.equals("String de teste"));
    }

    // Validação do saldo suficiente

    @Test
    void saldoMaiorValor()
    {
        Pessoa pessoa = criarPessoaComSaldo(1000);
        assertTrue(pessoa.saldoSuficiente(200));
    }

    @Test
    void saldoIgualValor()
    {
        Pessoa pessoa = criarPessoaComSaldo(200);
        assertTrue(pessoa.saldoSuficiente(200));
    }

    @Test
    void saldoMenorValor()
    {
        Pessoa pessoa = criarPessoaComSaldo(200);
        assertFalse(pessoa.saldoSuficiente(1000));
    }

    // Validacao debitar

    @Test
    void debitarValorZeroLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();
        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.debitar(0)
        );
    }

    @Test
    void debitarComSucesso()
    {
        Pessoa pessoa = criarPessoaComSaldo(1000);
        pessoa.debitar(200);

        assertEquals(800, pessoa.getSaldoEmCentavos());
    }

    @Test
    void debitarNegativoLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.debitar(-200)
        );
    }

    @Test
    void debitarComSaldoInsuficienteLancaExcecao()
    {
        Pessoa pessoa = criarPessoaComSaldo(200);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> pessoa.debitar(1000)
        );
    }

    // Validacao creditar

    @Test
    void creditarValorZeroLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.creditar(0)
        );
    }

    @Test
    void creditarComSucesso()
    {
        Pessoa pessoa = criarPessoaComSaldo(1000);
        pessoa.creditar(200);

        assertEquals(1200, pessoa.getSaldoEmCentavos());
    }

    @Test
    void creditarNegativoLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.creditar(-200)
        );
    }
}
