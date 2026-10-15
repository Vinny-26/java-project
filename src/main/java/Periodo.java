
import excecoes.DadosInvalidosException;
import excecoes.SobreposicaoPeriodoException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record Periodo(LocalDateTime inicio, LocalDateTime fim)
{
    public Periodo
    {
        if (inicio == null || fim == null)
        {
            throw new DadosInvalidosException("Inicio e fim são obrigatórios.");
        }
        if (inicio.isAfter(fim))
        {
            throw new DadosInvalidosException("A data/hora inicial deve ser estritamente anterior à final.");
        }
        if (inicio.isEqual(fim))
        {
            throw new SobreposicaoPeriodoException("A duração da reserva deve ser estritamente maior do que 0 horas");
        }
    }
    public boolean temSobreposicao(Periodo outro)
    {
        if (outro == null)
        {
            return false;
        }
        return this.inicio.isBefore(outro.fim()) && this.fim.isAfter(outro.inicio());
        // Retorna resultado do teste se inicio é antes do fim do outro e fim é depois do início do outro
    }
    public int diasDoPeriodo()
    {
        int dias = (int) ChronoUnit.DAYS.between(this.inicio, this.fim);
        return Math.max(1, dias);
    }
    public LocalDateTime getPeriodo()
    {
        return this.inicio;
    }
}
