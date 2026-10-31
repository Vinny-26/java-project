import excecoes.DadosInvalidosException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaReservaServicoTest {

    private Repositorio<Reserva, Integer> repositorio;
    private ConsultaReservaServico servico;

    private Pessoa pessoa1;
    private Pessoa pessoa2;
    private Carro carroHyundai;
    private Carro carroFiat;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioMemoria<>();
        servico = new ConsultaReservaServico(repositorio);

        // Pessoas
        pessoa1 = new Pessoa("Vinícius", new BigDecimal("20000"), "11111111111");
        pessoa2 = new Pessoa("Felipe", new BigDecimal("20000"), "22222222222");

        // Carros (Diárias: 1000 e 1500)
        carroHyundai = new Carro("ABC1234", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20");
        carroFiat = new Carro("XYZ9876", "Preto", true, new BigDecimal("1500"), true, MarcaCarro.FIAT, "Uno");

        LocalDateTime inicioBase = LocalDateTime.of(2026, 11, 10, 10, 0);

        // Reserva 1: CRIADA | Pessoa 1 | Hyundai (2 diárias = 2000)
        Periodo periodo1 = new Periodo(inicioBase, inicioBase.plusDays(2));
        Reserva r1 = new Reserva(1, periodo1, pessoa1, carroHyundai);

        // Reserva 2: CONCLUÍDA | Pessoa 1 | Toyota (2 diárias = 3000)
        Periodo periodo2 = new Periodo(inicioBase.plusDays(5), inicioBase.plusDays(7));
        Reserva r2 = new Reserva(2, periodo2, pessoa1, carroFiat);
        r2.concluir();

        // Reserva 3: CANCELADA | Pessoa 2 | Hyundai (2 diárias = 2000)
        Periodo periodo3 = new Periodo(inicioBase.plusDays(10), inicioBase.plusDays(12));
        Reserva r3 = new Reserva(3, periodo3, pessoa2, carroHyundai);
        r3.cancelar(inicioBase.plusDays(8), new EstornoTotal());

        repositorio.salvar(r1);
        repositorio.salvar(r2);
        repositorio.salvar(r3);
    }

    // Validação do construtor e invariantes
    @Test
    void repositorioNuloLancaExcecao()
    {
        assertThrows(DadosInvalidosException.class, () -> new ConsultaReservaServico(null));

    }

    // Busca por Status
    @Test
    void buscaReservaStatusComSucesso()
    {
        List<Reserva> criadas = servico.buscarReservaPorStatus(StatusReserva.CRIADA);
        assertEquals(1, criadas.size());
        assertEquals(1, criadas.get(0).getId());

        List<Reserva> concluidas = servico.buscarReservaPorStatus(StatusReserva.CONCLUIDA);
        assertEquals(1, concluidas.size());
        assertEquals(2, concluidas.get(0).getId());
    }

    @Test
    void buscaStatusNuloRetornaListaVazia()
    {
        assertTrue(servico.buscarReservaPorStatus(null).isEmpty());
    }

    // Busca por CPF
    @Test
    void buscaReservaCPFComSucesso()
    {
        List<Reserva> reservasPessoa1 = servico.buscarReservaPorCPF("11111111111");
        assertEquals(2, reservasPessoa1.size());

        List<Reserva> reservasPessoa2 = servico.buscarReservaPorCPF("22222222222");
        assertEquals(1, reservasPessoa2.size());
    }

    @Test
    void retornaListaVaziaCPFInvalido()
    {
        assertTrue(servico.buscarReservaPorCPF("00000000000").isEmpty());
        assertTrue(servico.buscarReservaPorCPF(null).isEmpty());
        assertTrue(servico.buscarReservaPorCPF("    ").isEmpty());
    }

    // Busca por marca do carro

    @Test
    void buscaReservaMarcaComSucesso()
    {
        List<Reserva> reservasHyundai = servico.buscarReservaPorMarcaCarro(MarcaCarro.HYUNDAI);
        assertEquals(2, reservasHyundai.size());

        List<Reserva> reservasFiat = servico.buscarReservaPorMarcaCarro(MarcaCarro.FIAT);
        assertEquals(1, reservasFiat.size());
    }

    @Test
    void retornaListaVaziaMarcaNula()
    {
        assertTrue(servico.buscarReservaPorMarcaCarro(null).isEmpty());
    }

    // Calculo de faturamento

    @Test
    void calculaFaturamentoReservasConcluidas()
    {
        BigDecimal faturamento = servico.calcularFaturamentoConcluido();

        assertEquals(0, new BigDecimal("3000").compareTo(faturamento));
    }

    @Test
    void retornaZeroQuandoNaoTemReservas()
    {
        Repositorio<Reserva, Integer> repoVazio = new RepositorioMemoria<>();
        ConsultaReservaServico servicoVazio = new ConsultaReservaServico(repoVazio);

        BigDecimal faturamento = servicoVazio.calcularFaturamentoConcluido();
        assertEquals(0, BigDecimal.ZERO.compareTo(faturamento));
    }

    // Verificação de Reserva Ativa

    @Test
    void retornaTrueSeReservaExistentePlaca() {
        assertTrue(servico.carroPossuiReservaAtiva("ABC1234"));
        assertTrue(servico.carroPossuiReservaAtiva(" abc1234 "));
    }

    @Test
    void retornaFalseSeReservaInexistentePlaca() {
        assertFalse(servico.carroPossuiReservaAtiva("XYZ9876"));

        assertFalse(servico.carroPossuiReservaAtiva("PLACA999"));
        assertFalse(servico.carroPossuiReservaAtiva(null));
    }

}
