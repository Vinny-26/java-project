package politica;

import dominio.Carro;
import dominio.Periodo;
import java.math.BigDecimal;

public interface CalculoPreco {
    BigDecimal calcular(Carro carro, Periodo periodo);
}