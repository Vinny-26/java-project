import excecoes.DadosInvalidosException;
import java.math.BigDecimal;
import java.util.List;

public class ConsultaReservaServico
{
    private final Repositorio<Reserva, Integer> repositorio;

    public ConsultaReservaServico(Repositorio<Reserva, Integer> repositorio)
    {
        if (repositorio == null)
        {
            throw new DadosInvalidosException("O repositório não pode ser nulo.");
        }
        this.repositorio = repositorio;
    }

    // Filtrando reservas por status
    public List<Reserva> buscarReservaPorStatus(StatusReserva statusReserva)
    {
        if (statusReserva == null) return List.of();

        return repositorio.listarTodos().stream()
                .filter(r -> r.getStatus() == statusReserva)
                .toList();
    }

    // Buscar todas reservas através do CPF
    public List<Reserva> buscarReservaPorCPF(String cpf)
    {
        if (cpf == null || cpf.isBlank()) return List.of();

        return repositorio.listarTodos().stream()
                .filter(r -> r.getPessoa().getCpf().equals(cpf))
                .toList();
    }

    // Buscar reserva por marcas de carro
    public List<Reserva> buscarReservaPorMarcaCarro(MarcaCarro marca)
    {
        if (marca == null) return List.of();

        return repositorio.listarTodos().stream()
                .filter(r -> r.getCarro().getMarca() == marca)
                .toList();
    }

    // Somar faturamento das reservas concluídas
    public BigDecimal calcularFaturamentoConcluido()
    {
        return repositorio.listarTodos().stream()
                .filter(r -> r.getStatus() == StatusReserva.CONCLUIDA)
                .map(Reserva::getCustoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Verificar se existe reserva ativa para o veículo
    public boolean carroPossuiReservaAtiva(String placa)
    {
        if (placa == null || placa.isBlank()) return false;

        return repositorio.listarTodos().stream()
                .filter(r -> r.getStatus() == StatusReserva.CRIADA)
                .anyMatch(r -> r.getCarro().getPlaca().equalsIgnoreCase(placa.trim()));
    }

}
