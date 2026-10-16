import excecoes.DadosInvalidosException;
import excecoes.SobreposicaoPeriodoException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PeriodoTest {

    @Test
    void criacaoValida()
    {
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 10, 8, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 10, 10, 10, 0);

        Periodo periodo = new Periodo(inicio, fim);

        assertEquals(inicio, periodo.inicio());
        assertEquals(fim, periodo.fim());
    }

    @Test
    void inicialPosteriorfinalLancaExcecao()
    {
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 10, 10, 8, 0);

        assertThrows(
                DadosInvalidosException.class,
                () -> new Periodo(inicio, fim));
    }

    @Test
    void inicialIgualfinalLancaExcecao()
    {
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 10, 10, 10, 0);

        assertThrows(
                DadosInvalidosException.class,
                () -> new Periodo(inicio, fim));
    }

    @Test
    void inicioNuloLancaExcecao()
    {
        LocalDateTime inicio = null;
        LocalDateTime fim = LocalDateTime.of(2026, 10, 10, 8, 0);

        assertThrows(
                DadosInvalidosException.class,
                () -> new Periodo(inicio, fim)
        );
    }

    @Test
    void fimNuloLancaExcecao()
    {
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fim = null;

        assertThrows(
                DadosInvalidosException.class,
                () -> new Periodo(inicio, fim)
        );
    }

    // Teste das sobreposições
    @Test
    void sobreposicaoEsquerda()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 8, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 10, 0);

        LocalDateTime inicioB = LocalDateTime.of(2026, 10, 10, 7, 0);
        LocalDateTime fimB = LocalDateTime.of(2026, 10, 10, 9, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);
        Periodo periodoB = new Periodo(inicioB, fimB);

        assertTrue(periodoA.temSobreposicao(periodoB));
    }

    @Test
    void sobreposicaoDireita()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 12, 0);

        LocalDateTime inicioB = LocalDateTime.of(2026, 10, 10, 11, 0);
        LocalDateTime fimB = LocalDateTime.of(2026, 10, 10, 13, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);
        Periodo periodoB = new Periodo(inicioB, fimB);

        assertTrue(periodoA.temSobreposicao(periodoB));
    }

    @Test
    void aContidoEmB()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 12, 0);

        LocalDateTime inicioB = LocalDateTime.of(2026, 10, 10, 8, 0);
        LocalDateTime fimB = LocalDateTime.of(2026, 10, 10, 18, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);
        Periodo periodoB = new Periodo(inicioB, fimB);

        assertTrue(periodoA.temSobreposicao(periodoB));
    }

    @Test
    void aEnvolveB()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 8, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 18, 0);

        LocalDateTime inicioB = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimB = LocalDateTime.of(2026, 10, 10, 12, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);
        Periodo periodoB = new Periodo(inicioB, fimB);

        assertTrue(periodoA.temSobreposicao(periodoB));

    }

    @Test
    void sobreposicaoInexistente()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 12, 0);

        LocalDateTime inicioB = LocalDateTime.of(2026, 10, 10, 13, 0);
        LocalDateTime fimB = LocalDateTime.of(2026, 10, 10, 18, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);
        Periodo periodoB = new Periodo(inicioB, fimB);

        assertFalse(periodoA.temSobreposicao(periodoB));
    }

    @Test
    void retiraNaDevolucaoNaoSobrepoe()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 12, 0);

        LocalDateTime inicioB = LocalDateTime.of(2026, 10, 10, 12, 0);
        LocalDateTime fimB = LocalDateTime.of(2026, 10, 10, 14, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);
        Periodo periodoB = new Periodo(inicioB, fimB);

        assertFalse(periodoA.temSobreposicao(periodoB));
    }

    @Test
    void periodoNuloNaoGeraExcecao()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 12, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);

        assertFalse(periodoA.temSobreposicao(null));
    }

    // Teste do cálculo de dias da Reserva

    @Test
    void periodoMenorQue24RetornaUmaDiaria()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 12, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);

        assertEquals(1, periodoA.diasDoPeriodo());
    }

    @Test
    void periodoMultiploRetornaCorretamente()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 12, 12, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);

        assertEquals(2, periodoA.diasDoPeriodo());
    }

    // Teste de exceções na sobreposição

    @Test
    void validarSemSobreposicaoLancaExcecaoQuandoConflito()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 12, 12, 0);
        LocalDateTime inicioB = LocalDateTime.of(2026, 10, 10, 11, 0);
        LocalDateTime fimB = LocalDateTime.of(2026, 10, 10, 13, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);
        Periodo periodoB = new Periodo(inicioB, fimB);

        assertThrows(
                SobreposicaoPeriodoException.class,
                () -> periodoA.validarSemSobreposicao(periodoB)
        );
    }

    @Test
    void validarSemSobreposicaoQuandoLivre()
    {
        LocalDateTime inicioA = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fimA = LocalDateTime.of(2026, 10, 10, 12, 0);
        LocalDateTime inicioB = LocalDateTime.of(2026, 10, 10, 13, 0);
        LocalDateTime fimB = LocalDateTime.of(2026, 10, 10, 14, 0);

        Periodo periodoA = new Periodo(inicioA, fimA);
        Periodo periodoB = new Periodo(inicioB, fimB);

        assertDoesNotThrow(() -> periodoA.validarSemSobreposicao(periodoB));
    }
}
