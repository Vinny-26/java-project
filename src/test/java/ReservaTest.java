import excecoes.DadosInvalidosException;
import excecoes.EstadoInvalidoException;
import excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    private Pessoa pessoaValida;
    private Carro carroValido;
    private Periodo periodoValido;

    @BeforeEach
    void setUp()
    {
        pessoaValida = new Pessoa("Vinícius", new BigDecimal("10000"), "11111111111");
        carroValido = new Carro("A6EVBCD", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20");
        periodoValido = new Periodo(LocalDateTime.now(), LocalDateTime.now().plusDays(2));
    }

    @Test
    void criacaoValida()
    {
        Reserva reserva = new Reserva(1, periodoValido, pessoaValida, carroValido);

        assertEquals(1, reserva.getId());
        assertEquals(periodoValido, reserva.getPeriodo());
        assertEquals(pessoaValida, reserva.getPessoa());
        assertEquals(carroValido, reserva.getCarro());
        assertEquals(StatusReserva.CRIADA,reserva.getStatus());
        assertEquals(0, new BigDecimal("8000").compareTo(pessoaValida.getSaldo()));
    }

    @Test
    void idInvalidoLancaExcecao() {
        assertThrows(DadosInvalidosException.class,
                () -> new Reserva (-1, periodoValido, pessoaValida, carroValido)
        );
    }

    @Test
    void periodoNuloLancaExcecao() {
        assertThrows(DadosInvalidosException.class,
                () -> new Reserva (1, null, pessoaValida, carroValido)
        );
    }

    @Test
    void pessoaNulaLancaExcecao() {
        assertThrows(DadosInvalidosException.class,
                () -> new Reserva (1, periodoValido, null, carroValido)
        );
    }

    @Test
    void carroNuloLancaExcecao() {
        assertThrows(DadosInvalidosException.class,
                () -> new Reserva (1, periodoValido, pessoaValida, null)
        );
    }

    @Test
    void carroInativoLancaExcecao() {
        carroValido.desativar();
        assertThrows(EstadoInvalidoException.class,
                () -> new Reserva(1, periodoValido, pessoaValida, carroValido)
        );
    }

    @Test
    void saldoInsuficienteLancaExcecao() {
        pessoaValida = new Pessoa("Vinícius", new BigDecimal("100"), "11111111111");
        assertThrows(SaldoInsuficienteException.class,
                () -> new Reserva (1, periodoValido, pessoaValida, carroValido)
        );
    }

    // ==========================================
    // 2. TRANSIÇÕES DE ESTADO (MÁQUINA DE ESTADOS)
    // ==========================================

    @Test
    void cancelarComSucessoEstorno() {
        Reserva reserva = new Reserva(1, periodoValido, pessoaValida, carroValido);
        assertEquals(0, new BigDecimal("8000").compareTo(pessoaValida.getSaldo()));

        reserva.cancelar();
        assertEquals(StatusReserva.CANCELADA, reserva.getStatus());
        assertEquals(0, new BigDecimal("10000").compareTo(pessoaValida.getSaldo()));
    }

    @Test
    void concluirComSucesso() {
        Reserva reserva = new Reserva(1, periodoValido, pessoaValida, carroValido);
        reserva.concluir();
        assertEquals(StatusReserva.CONCLUIDA, reserva.getStatus());
    }

    @Test
    void cancelarReservaJaCanceladaLancaExcecao() {
        Reserva reserva = new Reserva(1, periodoValido, pessoaValida, carroValido);
        reserva.cancelar();
        assertThrows(EstadoInvalidoException.class,
                () -> reserva.cancelar());
    }

    @Test
    void cancelarReservaConcluidaLancaExcecao() {
        Reserva reserva = new Reserva(1, periodoValido, pessoaValida, carroValido);
        reserva.concluir();
        assertThrows(EstadoInvalidoException.class,
                () -> reserva.cancelar());
    }

    @Test
    void concluirReservaJaConcluidaLancaExcecao() {
        Reserva reserva = new Reserva(1, periodoValido, pessoaValida, carroValido);
        reserva.concluir();
        assertThrows(EstadoInvalidoException.class,
                () -> reserva.concluir());
    }

    @Test
    void concluirReservaCanceladaLancaExcecao() {
        Reserva reserva = new Reserva(1, periodoValido, pessoaValida, carroValido);
        reserva.cancelar();
        assertThrows(EstadoInvalidoException.class,
                () -> reserva.concluir());
    }

    // Validação do equals e hashcode

    @Test
    void reservaMesmoIdSaoIguais()
    {
        Reserva reservaA = new Reserva(1, periodoValido, pessoaValida, carroValido);
        Pessoa pessoaB = new Pessoa("Felipe", new BigDecimal("10000"), "22222222222");
        Reserva reservaB = new Reserva(1, periodoValido, pessoaB, carroValido);

        assertEquals(reservaA, reservaB);
    }

    @Test
    void reservaIdDiferentesNaoSaoIguais()
    {
        Reserva reservaA = new Reserva(1, periodoValido, pessoaValida, carroValido);
        Reserva reservaB = new Reserva(3, periodoValido, pessoaValida, carroValido);

        assertNotEquals(reservaA, reservaB);
    }

    @Test
    void contratoHashCode()
    {
        Reserva reservaA = new Reserva(1, periodoValido, pessoaValida, carroValido);
        Pessoa pessoaB = new Pessoa("Felipe", new BigDecimal("10000"), "22222222222");
        Reserva reservaB = new Reserva(1, periodoValido, pessoaB, carroValido);

        assertEquals(reservaA.hashCode(), reservaB.hashCode());
    }

    @Test
    void comparacaoComNuloOuOutroTipo()
    {
        Reserva reservaA = new Reserva(1, periodoValido, pessoaValida, carroValido);

        assertFalse(reservaA.equals(null));
        assertFalse(reservaA.equals("String de teste"));
    }

}

