import excecoes.DadosInvalidosException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioMemoriaTest {
    private Repositorio<Reserva, Integer> repositorio;
    private Reserva reservaValida1;
    private Reserva reservaValida2;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioMemoria<>();

        Pessoa pessoa = new Pessoa("Vinícius", new BigDecimal("10000"), "11111111111");
        Carro carro = new Carro("A6EVBCD", "Azul", true, new BigDecimal("1000"), true, MarcaCarro.HYUNDAI, "HB20");
        Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));

        reservaValida1 = new Reserva(1, periodo, pessoa, carro);
        reservaValida2 = new Reserva(2, periodo, pessoa, carro);
    }

    @Test
    void salvaEBuscaPorID() {
        repositorio.salvar(reservaValida1);

        Optional<Reserva> resultado = repositorio.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(reservaValida1, resultado.get());
    }

    @Test
    void retornaVazioQuandoIDInexistente() {
        Optional<Reserva> resultado = repositorio.buscarPorId(999);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void retornaVazioQuandoIDNulo() {
        Optional<Reserva> resultado = repositorio.buscarPorId(null);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void impossibilitaSalvarNulo() {
        assertThrows(DadosInvalidosException.class, () -> repositorio.salvar(null));
    }

    @Test
    void listaTodasReservasCadastradas() {
        repositorio.salvar(reservaValida1);
        repositorio.salvar(reservaValida2);

        List<Reserva> todas = repositorio.listarTodos();

        assertEquals(2, todas.size());
        assertTrue(todas.contains(reservaValida1));
        assertTrue(todas.contains(reservaValida2));
    }

    @Test
    void deletaPorIDComSucesso()
    {
        repositorio.salvar(reservaValida1);

        assertTrue(repositorio.buscarPorId(1).isPresent());

        repositorio.deletarPorId(1);

        assertTrue(repositorio.buscarPorId(1).isEmpty());
        assertEquals(0, repositorio.listarTodos().size());
    }

    @Test
    void lancaExcecaoQuandoDeletaNuloOuInexistente()
    {
        assertThrows(DadosInvalidosException.class, () -> repositorio.deletarPorId(999));
        assertThrows(DadosInvalidosException.class, () -> repositorio.deletarPorId(null));
    }

}