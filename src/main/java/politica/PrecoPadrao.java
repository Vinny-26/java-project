package politica;

import java.math.BigDecimal;
import dominio.Periodo;
import dominio.Carro;

public class PrecoPadrao implements CalculoPreco
{
    @Override
    public BigDecimal calcular(Carro carro, Periodo periodo)
    {
        BigDecimal valorbase = carro.getValor();
        return valorbase.multiply(BigDecimal.valueOf(periodo.diasDoPeriodo()));
    }
}
