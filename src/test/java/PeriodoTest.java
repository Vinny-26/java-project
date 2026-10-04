import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void inicialPosteriorfinal()
    {
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 10, 10, 8, 0);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Periodo(inicio, fim));
    }

    @Test
    void inicialIgualfinal()
    {
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 10, 10, 10, 0);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Periodo(inicio, fim));
    }

    @Test
    void inicioNuloLancaExcecao()
    {
        LocalDateTime inicio = null;
        LocalDateTime fim = LocalDateTime.of(2026, 10, 10, 8, 0);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Periodo(inicio, fim)
        );
    }

    @Test
    void fimNuloLancaExcecao()
    {
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 10, 10, 0);
        LocalDateTime fim = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> new Periodo(inicio, fim)
        );
    }
}
