package politica;

import dominio.Carro;
import dominio.MarcaCarro;
import dominio.Periodo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculoPrecoTest
{
    private final Carro carro = new Carro(
            "ABC1D23",
            "Prata",
            true,
            new BigDecimal("100.00"),
            true,
            MarcaCarro.HYUNDAI,
            "HB20"
    );

    private final LocalDateTime base = LocalDateTime.of(2026, 12, 1, 10, 0);

    @Test
    void precoPadraoReservaMenorQueSete()
    {
        CalculoPreco estrategia = new PrecoPadrao();
        Periodo tresDias = new Periodo(base, base.plusDays(3));
        BigDecimal valorCalculado = estrategia.calcular(carro, tresDias);

        assertEquals(0, new BigDecimal("300.00").compareTo(valorCalculado));
    }

    @Test
    void precoPadraoCobraIntegralMesmoAcimaDeSeteDias()
    {
        CalculoPreco estrategia = new PrecoPadrao();
        Periodo dezDias = new Periodo(base, base.plusDays(10));
        BigDecimal valorCalculado = estrategia.calcular(carro, dezDias);

        assertEquals(0, new BigDecimal("1000.00").compareTo(valorCalculado));
    }

    @Test
    void semDescontoParaPeriodoMenorQueSeteDias()
    {
        CalculoPreco estrategia = new PrecoDescontoLongaDuracao();
        Periodo seisDias = new Periodo(base, base.plusDays(6));
        BigDecimal valorCalculado = estrategia.calcular(carro, seisDias);

        assertEquals(0, new BigDecimal("600.00").compareTo(valorCalculado));
    }

    @Test
    void descontoDezPorcentoParaSeteDias()
    {
        CalculoPreco estrategia = new PrecoDescontoLongaDuracao();
        Periodo seteDias = new Periodo(base, base.plusDays(7));
        BigDecimal valorCalculado = estrategia.calcular(carro, seteDias);

        // 7 * 100 = 700 -> com 10% de desconto = 630.00
        assertEquals(0, new BigDecimal("630.00").compareTo(valorCalculado));
    }

    @Test
    void descontoDezPorcentoParaPeriodoMaiorSeteDias()
    {
        CalculoPreco estrategia = new PrecoDescontoLongaDuracao();
        Periodo dezDias = new Periodo(base, base.plusDays(10));
        BigDecimal valorCalculado = estrategia.calcular(carro, dezDias);

        // 10 * 100 = 1000 -> com 10% de desconto = 900.00
        assertEquals(0, new BigDecimal("900.00").compareTo(valorCalculado));
    }
}