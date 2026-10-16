import java.util.List;

public enum MarcaCarro {

    FIAT("Fiat", List.of("Uno", "Palio", "Mobi")),
    HYUNDAI("Hyundai", List.of("HB20", "Creta", "Tucson")),
    VOLKSWAGEN("Volkswagem", List.of("Gol", "Polo", "Virtus"));

    private final String nomeExibicao;
    private final List<String> modelosPermitidos;

    MarcaCarro(String nomeExibicao, List<String> modelosPermitidos)
    {
        this.nomeExibicao = nomeExibicao;
        this.modelosPermitidos = modelosPermitidos;
    }

    public String getNomeExibicao()
    {
        return nomeExibicao;
    }

    public List<String> getModelosPermitidos() {
        return modelosPermitidos;
    }

    public boolean possuiModelo(String modelo)
    {
        if (modelo == null || modelo.isBlank())
        {
            return false;
        }
        return modelosPermitidos.stream()
                .anyMatch(m -> m.equalsIgnoreCase(modelo.trim()));
    }
}
