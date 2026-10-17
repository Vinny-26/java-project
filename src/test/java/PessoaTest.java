import excecoes.DadosInvalidosException;
import excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest
{

    // Métodos auxiliares para simplificação de código(Sugestão de IA)
    private Pessoa criarPessoaPadrao()
    {
        return new Pessoa("Vinicius", new BigDecimal("1000"), "11111111111");
    }

    private Pessoa criarPessoaComSaldo(int saldo)
    {
        return new Pessoa("Vinicius", new BigDecimal(saldo), "11111111111");
    }

    // Validação do construtor
    @Test
    void criacaoValida()
    {

        Pessoa pessoa = criarPessoaPadrao();

        assertEquals("Vinicius", pessoa.getTitular());
        assertEquals(0, new BigDecimal("1000").compareTo(pessoa.getSaldo()));
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
                () -> new Pessoa(null, new BigDecimal("1000"), "11111111111")
        );
    }

    @Test
    void titularVazioLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa(" ", new BigDecimal("1000"), "11111111111")
        );
    }

    @Test
    void cpfNuloLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", new BigDecimal("1000"), null)
        );
    }

    @Test
    void cpfVazioLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", new BigDecimal("1000"), " ")
        );
    }

    @Test
    void cpfInvalidoLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", new BigDecimal("1000"), "111.111.111-11111")
        );
    }

    @Test
    void saldoInvalidoLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", new BigDecimal("-1"), "11111111111")
        );
    }

    @Test
    void saldoNuloLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Pessoa("Vinicius", null, "11111111111")
        );
    }

    // Validação do Equals + HashCode
    @Test
    void pessoasComMesmoCpfSaoIguais()
    {
        Pessoa pessoaA = new Pessoa("Vinicius", new BigDecimal("1000"), "11111111111");
        Pessoa pessoaB = new Pessoa("Felipe", new BigDecimal("1500"), "11111111111");

        assertEquals(pessoaA, pessoaB);
    }

    @Test
    void pessoasComCpfDiferentesNaoSaoIguais()
    {
        Pessoa pessoaA = new Pessoa("Vinicius", new BigDecimal("1000"), "11111111111");
        Pessoa pessoaB = new Pessoa("Vinicius", new BigDecimal("1000"), "22222222222");

        assertNotEquals(pessoaA, pessoaB);
    }

    @Test
    void contratoHashCode()
    {
        Pessoa pessoaA = new Pessoa("Vinicius", new BigDecimal("1000"), "11111111111");
        Pessoa pessoaB = new Pessoa("Felipe", new BigDecimal("1500"), "11111111111");

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
        assertTrue(pessoa.saldoSuficiente(new BigDecimal("200")));
    }

    @Test
    void saldoIgualValor()
    {
        Pessoa pessoa = criarPessoaComSaldo(200);
        assertTrue(pessoa.saldoSuficiente(new BigDecimal("200")));
    }

    @Test
    void saldoMenorValor()
    {
        Pessoa pessoa = criarPessoaComSaldo(200);
        assertFalse(pessoa.saldoSuficiente(new BigDecimal("1000")));
    }

    // Validacao debitar

    @Test
    void debitarNuloLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();
        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.debitar(null)
        );
    }

    @Test
    void debitarValorZeroLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();
        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.debitar(new BigDecimal("0"))
        );
    }

    @Test
    void debitarComSucesso()
    {
        Pessoa pessoa = criarPessoaComSaldo(1000);
        pessoa.debitar(BigDecimal.valueOf(200));

        assertEquals(0, new BigDecimal("800").compareTo(pessoa.getSaldo()));
    }

    @Test
    void debitarNegativoLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.debitar(new BigDecimal("-200"))
        );
    }

    @Test
    void debitarComSaldoInsuficienteLancaExcecao()
    {
        Pessoa pessoa = criarPessoaComSaldo(200);

        assertThrows(
                SaldoInsuficienteException.class,
                () -> pessoa.debitar(new BigDecimal("1000"))
        );
    }

    // Validacao creditar

    @Test
    void creditarNuloLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.creditar(null)
        );
    }

    @Test
    void creditarValorZeroLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.creditar(new BigDecimal("0"))
        );
    }

    @Test
    void creditarComSucesso()
    {
        Pessoa pessoa = criarPessoaComSaldo(1000);
        pessoa.creditar(new BigDecimal("200"));

        assertEquals(0, new BigDecimal("1200").compareTo(pessoa.getSaldo()));
    }

    @Test
    void creditarNegativoLancaExcecao()
    {
        Pessoa pessoa = criarPessoaPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> pessoa.creditar(new BigDecimal("-200"))
        );
    }
}
