import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EstornoComMulta implements PoliticaCancelamento{
    @Override
    public BigDecimal calcularEstorno(Reserva reserva, LocalDateTime momentoCancelamento) {
        long horasFaltantes = reserva.getPeriodo().horasAteInicio(momentoCancelamento);
        // Se faltar menos de 48 horas, aplica multa (ex: devolve 80%)
        if (horasFaltantes < 48) {
            return reserva.getCustoTotal().multiply(new BigDecimal("0.80"));
        }
        // Se faltar 48h ou mais, estorna valor total
        return reserva.getCustoTotal();
    }
}
