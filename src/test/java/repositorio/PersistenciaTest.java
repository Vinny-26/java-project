package repositorio;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import excecoes.DadosInvalidosException;
import dominio.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistenciaTest {

    @Test
    void deveSalvarERestaurarReservaValidaComSucesso(@TempDir Path tempDir) throws IOException {
        Path arquivoJson = tempDir.resolve("reservas.json");
        PersistenciaJsonServico persistencia = new PersistenciaJsonServico();

        Pessoa cliente = new Pessoa("Vinícius", new BigDecimal("1000.00"), "12345678900");
        Carro carro = new Carro("A6EVBCD", "Preto", true, new BigDecimal("150.00"), true, MarcaCarro.FIAT, "Uno");
        Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5));
        Reserva reservaOriginal = new Reserva(1, periodo, cliente, carro);

        // 1. Salva no disco temporário
        persistencia.salvar(arquivoJson, List.of(reservaOriginal));
        assertTrue(Files.exists(arquivoJson));

        // 2. Restaura do arquivo
        List<Reserva> restauradas = persistencia.restaurar(arquivoJson);

        // 3. Valida se o estado foi preservado
        assertEquals(1, restauradas.size());
        assertEquals(reservaOriginal.getId(), restauradas.get(0).getId());
        assertEquals(reservaOriginal.getPessoa().getCpf(), restauradas.get(0).getPessoa().getCpf());
        assertEquals(reservaOriginal.getCarro().getPlaca(), restauradas.get(0).getCarro().getPlaca());
    }

    @Test
    void deveRejeitarJsonComInvarianteViolada(@TempDir Path tempDir) throws IOException {
        Path jsonInvalido = tempDir.resolve("reserva_invalida.json");

        String conteudoCorrompido = """
            [
              {
                "id": 1,
                "periodo": {
                  "inicio": "2026-11-10T10:00:00",
                  "fim": "2026-11-05T10:00:00"
                }
              }
            ]
            """;

        // 1. Grava o JSON inválido no arquivo antes da leitura
        Files.writeString(jsonInvalido, conteudoCorrompido);

        // 2. Instancia o serviço de persistência
        PersistenciaJsonServico persistencia = new PersistenciaJsonServico();

        // 3. Captura e valida a exceção do Jackson e a causa de domínio
        ValueInstantiationException exception = assertThrows(
                ValueInstantiationException.class,
                () -> persistencia.restaurar(jsonInvalido)
        );

        assertInstanceOf(DadosInvalidosException.class, exception.getCause());
        assertEquals("A data/hora inicial deve ser estritamente anterior à final.", exception.getCause().getMessage());
    }
}