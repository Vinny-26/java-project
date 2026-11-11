package repositorio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dominio.Reserva;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PersistenciaJsonServico {

    private final ObjectMapper objectMapper;

    public PersistenciaJsonServico() {
        this.objectMapper = new ObjectMapper();
        // Suporte para java.time (LocalDateTime)
        this.objectMapper.registerModule(new JavaTimeModule());
        // Formatação legível (indentação) para inspecionabilidade (RF9)
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Salva datas no formato ISO-8601 legível em vez de timestamps numéricos
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


     // Salva a lista de reservas em formato JSON no caminho especificado.

    public void salvar(Path caminho, List<Reserva> reservas) throws IOException {
        if (caminho == null || reservas == null) {
            throw new IllegalArgumentException("Caminho e lista de reservas não podem ser nulos.");
        }

        // Garante que os diretórios pai existam antes de escrever
        if (caminho.getParent() != null) {
            Files.createDirectories(caminho.getParent());
        }

        objectMapper.writeValue(caminho.toFile(), reservas);
    }

    //Carrega as reservas a partir do arquivo JSON.

    public List<Reserva> restaurar(Path caminho) throws IOException {
        if (caminho == null || !Files.exists(caminho)) {
            throw new IllegalArgumentException("Arquivo de dados não encontrado: " + caminho);
        }

        return objectMapper.readValue(
                caminho.toFile(),
                new TypeReference<List<Reserva>>() {}
        );
    }
}
