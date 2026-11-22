package politica;

import dominio.Carro;
import dominio.Periodo;

import java.math.BigDecimal;

public class PrecoDescontoLongaDuracao implements CalculoPreco
{
    @Override
    public BigDecimal calcular(Carro carro, Periodo periodo)
    {
        long dias = periodo.diasDoPeriodo();
        BigDecimal valorbase = carro.getValor().multiply(BigDecimal.valueOf(dias));

        if (dias >= 7)
        {
            return valorbase.multiply(new BigDecimal("0.90"));
        }
        return valorbase;
    }
}
