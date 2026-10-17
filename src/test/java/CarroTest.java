import excecoes.DadosInvalidosException;
import excecoes.EstadoInvalidoException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CarroTest {

    // Métodos auxiliares para simplificação de código(Sugestão de IA)
    private Carro criarCarroPadrao() {
        return new Carro("A6EVBCD", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20");
    }

    private Carro criarCarroInativo() {
        return new Carro("A6EVBCD", "Azul", true, new BigDecimal("1000"), false, MarcaCarro.HYUNDAI, "HB20");
    }

@Test
    void criacaoValida()
{
    Carro carro = criarCarroPadrao();

    assertEquals("A6EVBCD", carro.getPlaca());
    assertEquals("Azul", carro.getCor());
    assertTrue(carro.hasArCondicionado());
    assertEquals(0, new BigDecimal("1000").compareTo(carro.getValor()));
    assertTrue(carro.isAtivo());
    assertEquals(MarcaCarro.HYUNDAI, carro.getMarca());
    assertEquals("HB20", carro.getModelo());
}

    @Test
    void placaEspacadaEMinusculaEFormatada() {
        Carro carro = new Carro(" a6evbcd ", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20");

        assertEquals("A6EVBCD", carro.getPlaca());
    }

    @Test
    void placaTamanhoInvalidaCriacao() {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("aaecdeasv23", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20")
        );
    }

    @Test
    void placaNulaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro(null, "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20")
        );
    }
    @Test
    void placaVaziaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro(" ", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20")
        );
    }

    @Test
    void corNulaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", null, true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20")
        );
    }

    @Test
    void corVaziaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", " ", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20")
        );
    }

    @Test
    void marcaNulaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, new BigDecimal("1000"), true, null, "HB20")
        );
    }

    @Test
    void modeloNuloInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, null)
        );
    }

    @Test
    void modeloVazioInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, " ")
        );
    }

    @Test
    void modeloIncompativelLancaExcecao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "Gol")
        );
    }

    @Test
    void valorNuloInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, null, true, MarcaCarro.HYUNDAI, "HB20")
        );
    }

    @Test
    void carroGratuitoInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, new BigDecimal("0"), true, MarcaCarro.HYUNDAI, "HB20")
        );
    }

    @Test
    void carroNegativoInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, new BigDecimal("-1"), true, MarcaCarro.HYUNDAI, "HB20")
        );
    }

    // Validação de equals + hashcode
    @Test
    void carroComMesmaPlacaSaoIguais()
    {
        Carro carroA = criarCarroPadrao();
        Carro carroB = new Carro("A6EVBCD", "Vermelho", false, new BigDecimal("2000"), false, MarcaCarro.FIAT, "Uno");

        assertEquals(carroA, carroB);
    }

    @Test
    void carroComPlacasDiferentesNaoSaoIguais()
    {
        Carro carroA = criarCarroPadrao();
        Carro carroB = new Carro("A6EVBCT", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20");

        assertNotEquals(carroA, carroB);
    }

    @Test
    void contratoHashCode()
    {
        Carro carroA = criarCarroPadrao();
        Carro carroB = new Carro("A6EVBCD", "Vermelho", false, new BigDecimal("2000"), false, MarcaCarro.FIAT, "Uno");

        assertEquals(carroA.hashCode(), carroB.hashCode());
    }

    @Test
    void comparacaoComNuloOuOutroTipo()
    {
        Carro carroA = criarCarroPadrao();

        assertFalse(carroA.equals(null));
        assertFalse(carroA.equals("String de teste"));
    }

    @Test
    void setCorValido()
    {
        Carro carro = criarCarroPadrao();
        carro.setCor("Amarelo");

        assertEquals("Amarelo", carro.getCor());
    }

    @Test
    void setCorNulaLancaExcecao()
    {
        Carro carro = criarCarroPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> carro.setCor(null)
        );
    }

    @Test
    void setCorVaziaLancaExcecao()
    {
        Carro carro = criarCarroPadrao();

        assertThrows(
                DadosInvalidosException.class,
                () -> carro.setCor(" ")
        );
    }

    // Validação da transição de estados ativado e desativado

    @Test
    void ativarComSucesso()
    {
        Carro carro = criarCarroInativo();
        carro.ativar();

        assertTrue(carro.isAtivo());
    }

    @Test
    void ativaCarroAtivoLancaExcecao()
    {
        Carro carro = criarCarroPadrao();

        assertThrows(
                EstadoInvalidoException.class,
                carro::ativar
        );
    }

    @Test
    void desativarComSucesso()
    {
        Carro carro = criarCarroPadrao();
        carro.desativar();

        assertFalse(carro.isAtivo());
    }

    @Test
    void desativarCarroDesativadoLancaExcecao()
    {
        Carro carro = criarCarroInativo();

        assertThrows(
                EstadoInvalidoException.class,
                carro::desativar
        );
    }
}
