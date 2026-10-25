import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EstornoTotal implements PoliticaCancelamento {
    @Override
    public BigDecimal calcularEstorno(Reserva reserva, LocalDateTime momentoCancelamento) {
        return reserva.getCustoTotal();
    }
}
