package app;

import dominio.Carro;
import dominio.MarcaCarro;
import dominio.Periodo;
import dominio.Pessoa;
import dominio.Reserva;
import politica.EstornoComMulta;
import politica.PrecoDescontoLongaDuracao;
import politica.PrecoPadrao;
import repositorio.PersistenciaJsonServico;
import repositorio.Repositorio;
import repositorio.RepositorioMemoria;
import servico.ConsultaReservaServico;
import servico.ReservaServico;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Repositorio<Reserva, Integer> repositorio = new RepositorioMemoria<>();
        ReservaServico reservaServico = new ReservaServico(repositorio);
        ConsultaReservaServico consultaServico = new ConsultaReservaServico(repositorio);
        PersistenciaJsonServico persistencia = new PersistenciaJsonServico();
        Path arquivoJson = Path.of("dados-demo", "reservas.json");

        System.out.println("=== 1. CADASTRO DE PARTICIPANTES E VEÍCULOS ===");
        Pessoa cliente1 = new Pessoa("Ana Silva", new BigDecimal("5000.00"), "11122233344");
        Pessoa cliente2 = new Pessoa("Bruno Souza", new BigDecimal("400.00"), "55566677788");

        Carro carroAtivo = new Carro("ABC1D23", "Prata", true, new BigDecimal("200.00"), true, MarcaCarro.HYUNDAI, "HB20");
        Carro carroInativo = new Carro("XYZ9W87", "Preto", false, new BigDecimal("150.00"), false, MarcaCarro.FIAT, "Uno");

        LocalDateTime base = LocalDateTime.of(2026, 12, 1, 10, 0);

        System.out.println("\n=== 2. FLUXO PRINCIPAL E ESTRATÉGIAS DE PREÇO ===");
        // Reserva 1: 3 dias * R$ 200,00 = R$ 600,00 (PrecoPadrao)
        Reserva r1 = reservaServico.criarReserva(1, new Periodo(base, base.plusDays(3)), cliente1, carroAtivo, new PrecoPadrao());
        // Reserva 2: 7 dias * R$ 200,00 = R$ 1.400,00 - 10% = R$ 1.260,00 (PrecoDescontoLongaDuracao)
        Reserva r2 = reservaServico.criarReserva(2, new Periodo(base.plusDays(5), base.plusDays(12)), cliente1, carroAtivo, new PrecoDescontoLongaDuracao());

        System.out.println("Reserva 1 criada (Tarifa Padrão): R$ " + r1.getCustoTotal());
        System.out.println("Reserva 2 criada (Desconto Longa Duração): R$ " + r2.getCustoTotal());
        System.out.println("Saldo restante Ana Silva: R$ " + cliente1.getSaldo());

        System.out.println("\n=== 3. RECUSAS E PROTEÇÃO DE INVARIANTES ===");
        try
        {
            reservaServico.criarReserva(3, new Periodo(base.plusDays(1), base.plusDays(4)), cliente1, carroAtivo);
        }
        catch (RuntimeException e)
        {
            System.out.println("[Recusa - Sobreposição]: " + e.getMessage());
        }

        try
        {
            reservaServico.criarReserva(4, new Periodo(base, base.plusDays(2)), cliente1, carroInativo);
        }
        catch (RuntimeException e)
        {
            System.out.println("[Recusa - Veículo Inativo]: " + e.getMessage());
        }

        try
        {
            reservaServico.criarReserva(5, new Periodo(base.plusDays(15), base.plusDays(18)), cliente2, carroAtivo);
        }
        catch (RuntimeException e)
        {
            System.out.println("[Recusa - Saldo Insuficiente]: " + e.getMessage());
        }

        System.out.println("\n=== 4. CICLO DE VIDA E POLÍTICA DE CANCELAMENTO (RF5, RF6) ===");
        r1.concluir();
        // Cancelamento com menos de 48h de antecedência -> estorno de 80% de R$ 1.260,00 = R$ 1.008,00
        r2.cancelar(base.plusDays(4), new EstornoComMulta());
        System.out.println("Status R1: " + r1.getStatus() + " | Status R2: " + r2.getStatus());
        System.out.println("Saldo Ana Silva após estorno parcial: R$ " + cliente1.getSaldo());

        System.out.println("\n=== 5. CONSULTAS, ORDENAÇÃO E RELATÓRIO SINTÉTICO ===");
        System.out.println("Faturamento concluído: R$ " + consultaServico.calcularFaturamentoConcluido());
        System.out.println("Resumo por status: " + consultaServico.agruparTotalReservasPorStatus());
        System.out.println("IDs ordenados por maior custo: " +
                consultaServico.listarReservasOrdenadasPorCustoDecrescente().stream().map(Reserva::getId).toList());

        System.out.println("\n=== 6. PERSISTÊNCIA E RESTAURAÇÃO JSON ===");
        persistencia.salvar(arquivoJson, repositorio.listarTodos());
        List<Reserva> restauradas = persistencia.restaurar(arquivoJson);
        System.out.println("Reservas restauradas do arquivo JSON: " + restauradas.size());
        System.out.println("Saldo preservado sem cobrança duplicada: R$ " + restauradas.getFirst().getPessoa().getSaldo());
    }
}