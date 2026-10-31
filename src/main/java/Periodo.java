
import excecoes.DadosInvalidosException;
import excecoes.SobreposicaoPeriodoException;

import java.time.Duration;
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
        if (!inicio.isBefore(fim))
        {
            throw new DadosInvalidosException("A data/hora inicial deve ser estritamente anterior à final.");
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

    public void validarSemSobreposicao(Periodo outro) {
        if (temSobreposicao(outro)) {
            throw new SobreposicaoPeriodoException("O período informado se sobrepõe a um período já existente.");
        }
    }

    public int diasDoPeriodo()
    {
        int dias = (int) ChronoUnit.DAYS.between(this.inicio, this.fim);
        return Math.max(1, dias);
    }
    public long horasAteInicio(LocalDateTime momentoConsulta) {
        if (momentoConsulta == null) {
            throw new DadosInvalidosException("Momento de consulta não pode ser nulo");
        }
        return Duration.between(momentoConsulta, this.inicio).toHours();
    }

}
