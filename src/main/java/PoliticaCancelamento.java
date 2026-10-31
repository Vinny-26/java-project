import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PoliticaCancelamento {
    BigDecimal calcularEstorno(Reserva reserva, LocalDateTime momentoCancelamento);
}