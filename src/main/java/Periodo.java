import java.time.LocalDateTime;

public record Periodo(LocalDateTime inicio, LocalDateTime fim)
{
    public Periodo
    {
        if (inicio == null || fim == null)
        {
            throw new IllegalArgumentException("Inicio e fim são obrigatórios.");
        }
        if (inicio.isBefore(fim))
        {
            throw new IllegalArgumentException("A data/hora inicial deve ser estritamente anterior à final.");
        }
    }
    public boolean temSobreposicao(Periodo outro)
    {
        if (outro == null)
        {
            return false;
        }
        return this.inicio.isBefore(outro.fim()) && this.fim.isAfter(outro.inicio());
        // Retorna resultado do teste se inicio é depois do fim do outro e fim é antes do início do outro
    }
    LocalDateTime getPeriodo()
    {
        return this.inicio;
    }
}
