package servico;

import dominio.Carro;
import dominio.MarcaCarro;
import dominio.Periodo;
import dominio.Pessoa;
import dominio.Reserva;
import excecoes.DadosInvalidosException;
import excecoes.EstadoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import politica.EstornoTotal;
import politica.PrecoDescontoLongaDuracao;
import repositorio.Repositorio;
import repositorio.RepositorioMemoria;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaServicoTest
{
    private Repositorio<Reserva, Integer> repositorio;
    private ReservaServico reservaServico;
    private Pessoa pessoa;
    private Carro carro;
    private LocalDateTime base;

    @BeforeEach
    void setUp()
    {
        repositorio = new RepositorioMemoria<>();
        reservaServico = new ReservaServico(repositorio);
        pessoa = new Pessoa("Carlos Mendes", new BigDecimal("10000.00"), "11122233344");
        carro = new Carro("ABC1D23", "Prata", true, new BigDecimal("200.00"), true, MarcaCarro.HYUNDAI, "HB20");
        base = LocalDateTime.of(2026, 12, 1, 10, 0);
    }

    @Test
    void criaESalvaReservaValida()
    {
        Periodo periodo = new Periodo(base, base.plusDays(3));
        Reserva criada = reservaServico.criarReserva(1, periodo, pessoa, carro);

        assertTrue(repositorio.buscarPorId(1).isPresent());
        assertEquals(0, new BigDecimal("600.00").compareTo(criada.getCustoTotal()));
    }

    @Test
    void rejeitaSobreposicaoDePeriodoMesmoCarroAtivo()
    {
        Periodo primeiroPeriodo = new Periodo(base, base.plusDays(5));
        Periodo periodoSobreposto = new Periodo(base.plusDays(3), base.plusDays(7));

        reservaServico.criarReserva(1, primeiroPeriodo, pessoa, carro);

        assertThrows(EstadoInvalidoException.class, () ->
                reservaServico.criarReserva(2, periodoSobreposto, pessoa, carro)
        );
    }

    @Test
    void aceitaPeriodoCompativelMesmoCarro()
    {
        Periodo primeiroPeriodo = new Periodo(base, base.plusDays(3));
        Periodo segundoPeriodo = new Periodo(base.plusDays(3), base.plusDays(6));

        reservaServico.criarReserva(1, primeiroPeriodo, pessoa, carro);
        Reserva segunda = reservaServico.criarReserva(2, segundoPeriodo, pessoa, carro);

        assertEquals(2, repositorio.listarTodos().size());
        assertEquals(2, segunda.getId());
    }

    @Test
    void permiteNovaReservaNoMesmoPeriodoAposCancelamento()
    {
        Periodo periodo = new Periodo(base, base.plusDays(7));
        Reserva primeira = reservaServico.criarReserva(1, periodo, pessoa, carro, new PrecoDescontoLongaDuracao());

        primeira.cancelar(base.minusDays(5), new EstornoTotal());

        Reserva substituta = reservaServico.criarReserva(2, periodo, pessoa, carro, new PrecoDescontoLongaDuracao());
        assertEquals(2, substituta.getId());
    }

    @Test
    void rejeitaReservaComIdDuplicado()
    {
        Periodo p1 = new Periodo(base, base.plusDays(2));
        Periodo p2 = new Periodo(base.plusDays(10), base.plusDays(12));

        reservaServico.criarReserva(1, p1, pessoa, carro);

        assertThrows(DadosInvalidosException.class, () ->
                reservaServico.criarReserva(1, p2, pessoa, carro)
        );
    }
}