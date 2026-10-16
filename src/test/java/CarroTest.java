import excecoes.DadosInvalidosException;
import excecoes.EstadoInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarroTest {

    // Métodos auxiliares para simplificação de código(Sugestão de IA)
    private Carro criarCarroPadrao() {
        return new Carro("A6EVBCD", "Azul", true, 1000, true, "Hyundai", "HB-20");
    }

    private Carro criarCarroInativo() {
        return new Carro("A6EVBCD", "Azul", true, 1000, false, "Hyundai", "HB-20");
    }

@Test
    void criacaoValida()
{
    Carro carro = criarCarroPadrao();

    assertEquals("A6EVBCD", carro.getPlaca());
    assertEquals("Azul", carro.getCor());
    assertTrue(carro.hasArCondicionado());
    assertEquals(1000, carro.getPrecoEmCentavos());
    assertTrue(carro.isAtivo());
    assertEquals("Hyundai", carro.getMarca());
    assertEquals("HB-20", carro.getModelo());
}

    @Test
    void placaEspacadaEMinusculaEFormatada() {
        Carro carro = new Carro(" a6evbcd ", "Azul", true, 1000, true, "Hyundai", "HB-20");

        assertEquals("A6EVBCD", carro.getPlaca());
    }

    @Test
    void placaTamanhoInvalidaCriacao() {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("aaecdeasv23", "Azul", true, 1000, true, "Hyundai", "HB-20")
        );
    }

    @Test
    void placaNulaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro(null, "Azul", true, 1000, true, "Hyundai", "HB-20")
        );
    }
    @Test
    void placaVaziaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro(" ", "Azul", true, 1000, true, "Hyundai", "HB-20")
        );
    }

    @Test
    void corNulaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", null, true, 1000, true, "Hyundai", "HB-20")
        );
    }

    @Test
    void corVaziaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", " ", true, 1000, true, "Hyundai", "HB-20")
        );
    }

    @Test
    void marcaNulaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, 1000, true, null, "HB-20")
        );
    }

    @Test
    void marcaVaziaInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, 1000, true, " ", "HB-20")
        );
    }

    @Test
    void modeloNuloInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, 1000, true, "Hyundai", null)
        );
    }

    @Test
    void modeloVazioInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, 1000, true, "Hyundai", " ")
        );
    }

    @Test
    void carroGratuitoInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, 0, true, "Hyundai", "HB-20")
        );
    }

    @Test
    void carroNegativoInvalidaCriacao()
    {
        assertThrows(
                DadosInvalidosException.class,
                () -> new Carro("A6EVBCD", "Azul", true, -1, true, "Hyundai", "HB-20")
        );
    }

    // Validação de equals + hashcode
    @Test
    void carroComMesmaPlacaSaoIguais()
    {
        Carro carroA = criarCarroPadrao();
        Carro carroB = new Carro("A6EVBCD", "Vermelho", false, 2000, false, "Fiat", "Uno");

        assertEquals(carroA, carroB);
    }

    @Test
    void carroComPlacasDiferentesNaoSaoIguais()
    {
        Carro carroA = criarCarroPadrao();
        Carro carroB = new Carro("A6EVBCT", "Azul", true, 1000, true, "Hyundai", "HB-20");

        assertNotEquals(carroA, carroB);
    }

    @Test
    void contratoHashCode()
    {
        Carro carroA = criarCarroPadrao();
        Carro carroB = new Carro("A6EVBCD", "Vermelho", false, 2000, false, "Fiat", "Uno");

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
