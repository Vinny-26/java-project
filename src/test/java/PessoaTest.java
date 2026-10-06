import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest
{
    // Validação do construtor
    @Test
    void criacaoValida()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);

        assertEquals(titular, pessoa.getTitular());
        assertEquals(saldoEmCentavos, pessoa.getSaldoEmCentavos());
        assertEquals(cpf, pessoa.getCpf());
    }

    @Test
    void titularNulo()
    {
        String titular = null;
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        assertThrows(
                IllegalArgumentException.class,
                () -> new Pessoa(titular, saldoEmCentavos, cpf)
        );
    }

    @Test
    void titularVazio()
    {
        String titular = " ";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Pessoa(titular, saldoEmCentavos, cpf)
        );
    }

    @Test
    void cpfNulo()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> new Pessoa(titular, saldoEmCentavos, cpf)
        );
    }

    @Test
    void cpfVazio()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = " ";

        assertThrows(
                IllegalArgumentException.class,
                () -> new Pessoa(titular, saldoEmCentavos, cpf)
        );
    }

    @Test
    void cpfInvalidoLancaExcecao()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "111.111.111-11111";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Pessoa(titular, saldoEmCentavos, cpf)
        );
    }

    @Test
    void saldoInvalidoLancaExcecao()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = -1;
        String cpf = "11111111111";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Pessoa(titular, saldoEmCentavos, cpf)
        );
    }

    // Validação do Equals + HashCode
    @Test
    void pessoasComMesmoCpfSaoIguais()
    {
        String titularA = "Vinicius";
        int saldoEmCentavosA = 1000;
        String cpfA = "11111111111";

        String titularB = "Felipe";
        int saldoEmCentavosB = 1500;
        String cpfB = "11111111111";

        Pessoa pessoaA = new Pessoa(titularA, saldoEmCentavosA, cpfA);
        Pessoa pessoaB = new Pessoa(titularB, saldoEmCentavosB, cpfB);

        assertEquals(pessoaA, pessoaB);
    }

    @Test
    void pessoasComCpfDiferentesNaoSaoIguais()
    {
        String titularA = "Vinicius";
        int saldoEmCentavosA = 1000;
        String cpfA = "11111111111";

        String titularB = "Vinicius";
        int saldoEmCentavosB = 1500;
        String cpfB = "22222222222";

        Pessoa pessoaA = new Pessoa(titularA, saldoEmCentavosA, cpfA);
        Pessoa pessoaB = new Pessoa(titularB, saldoEmCentavosB, cpfB);

        assertNotEquals(pessoaA, pessoaB);
    }

    @Test
    void contratoHashCode()
    {
        String titularA = "Vinicius";
        int saldoEmCentavosA = 1000;
        String cpfA = "11111111111";

        String titularB = "Felipe";
        int saldoEmCentavosB = 1500;
        String cpfB = "11111111111";

        Pessoa pessoaA = new Pessoa(titularA, saldoEmCentavosA, cpfA);
        Pessoa pessoaB = new Pessoa(titularB, saldoEmCentavosB, cpfB);

        assertEquals(pessoaA.hashCode(), pessoaB.hashCode());
    }

    @Test
    void comparacaoComNuloOuOutroTipo()
    {
        String titularA = "Vinicius";
        int saldoEmCentavosA = 1000;
        String cpfA = "11111111111";

        Pessoa pessoaA = new Pessoa(titularA, saldoEmCentavosA, cpfA);

        assertFalse(pessoaA.equals(null));
        assertFalse(pessoaA.equals("String de teste"));
    }

    // Validação do saldo suficiente

    @Test
    void saldoMaiorValor()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = 200;

        assertTrue(pessoa.saldoSuficiente(valorEmCentavos));
    }

    @Test
    void saldoIgualValor()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 200;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = 200;

        assertTrue(pessoa.saldoSuficiente(valorEmCentavos));
    }

    @Test
    void saldoMenorValor()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 200;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = 1000;

        assertFalse(pessoa.saldoSuficiente(valorEmCentavos));
    }

    // Validacao debitar

    @Test
    void debitarValorZero()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> pessoa.debitar(valorEmCentavos)
        );
    }

    @Test
    void debitarComSucesso()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = 200;

        pessoa.debitar(valorEmCentavos);

        assertEquals(800, pessoa.getSaldoEmCentavos());
    }

    @Test
    void debitarNegativo()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = -200;

        assertThrows(
                IllegalArgumentException.class,
                () -> pessoa.debitar(valorEmCentavos)
        );
    }

    @Test
    void debitarComSaldoInsuficiente()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 200;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = 1000;

        assertThrows(
                IllegalArgumentException.class,
                () -> pessoa.debitar(valorEmCentavos)
        );
    }

    // Validacao creditar

    @Test
    void creditarValorZero()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = 0;

        assertThrows(
                IllegalArgumentException.class,
                () -> pessoa.creditar(valorEmCentavos)
        );
    }

    @Test
    void creditarComSucesso()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = 200;

        pessoa.creditar(valorEmCentavos);

        assertEquals(1200, pessoa.getSaldoEmCentavos());
    }

    @Test
    void creditarNegativo()
    {
        String titular = "Vinicius";
        int saldoEmCentavos = 1000;
        String cpf = "11111111111";

        Pessoa pessoa = new Pessoa(titular, saldoEmCentavos, cpf);
        int valorEmCentavos = -200;

        assertThrows(
                IllegalArgumentException.class,
                () -> pessoa.creditar(valorEmCentavos)
        );
    }
}
