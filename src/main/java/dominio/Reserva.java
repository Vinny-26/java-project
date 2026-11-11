package dominio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import politica.*;
import excecoes.DadosInvalidosException;
import excecoes.EstadoInvalidoException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reserva implements Identificavel<Integer> {
    private final int id;
    private StatusReserva status;
    private final Periodo periodo;
    private final Pessoa pessoa;
    private final Carro carro;

    /**
     * Construtor privado mestre.
     * Controla se o débito financeiro deve ser executado (criação) ou ignorado (restauração).
     */
    private Reserva(int id, StatusReserva status, Periodo periodo, Pessoa pessoa, Carro carro, boolean debitar) {
        if (id <= 0 || periodo == null || pessoa == null || carro == null) {
            throw new DadosInvalidosException("Não é possível criar Reserva com dados inválidos.");
        }
        if (!carro.isAtivo()) {
            throw new EstadoInvalidoException("Não é possível criar a reserva, o carro está inativo.");
        }

        this.id = id;
        this.periodo = periodo;
        this.pessoa = pessoa;
        this.carro = carro;
        this.status = (status != null) ? status : StatusReserva.CRIADA;

        if (debitar) {
            this.pessoa.debitar(getCustoTotal());
        }
    }

    /**
     * Construtor padrão da aplicação para criar novas reservas no domínio.
     * Define o status como CRIADA e efetua o débito inicial.
     */
    public Reserva(int id, Periodo periodo, Pessoa pessoa, Carro carro) {
        this(id, StatusReserva.CRIADA, periodo, pessoa, carro, true);
    }

    /**
     * Construtor de domínio com status explícito.
     * Efetua o débito financeiro.
     **/
    public Reserva(int id, StatusReserva status, Periodo periodo, Pessoa pessoa, Carro carro) {
        this(id, status, periodo, pessoa, carro, true);
    }


    // Método de fabrica utilizado pelo Jackson para RESTAURAR uma Reserva do JSON.

    @JsonCreator
    public static Reserva restaurar(
            @JsonProperty("id") int id,
            @JsonProperty("status") StatusReserva status,
            @JsonProperty("periodo") Periodo periodo,
            @JsonProperty("pessoa") Pessoa pessoa,
            @JsonProperty("carro") Carro carro) {
        return new Reserva(id, status, periodo, pessoa, carro, false);
    }

    private BigDecimal calcularCustoTotal(Carro carro, Periodo periodo) {
        return carro.getValor().multiply(BigDecimal.valueOf(periodo.diasDoPeriodo()));
    }

    public void cancelar(LocalDateTime momentoCancelamento, PoliticaCancelamento politica) {
        if (this.status != StatusReserva.CRIADA) {
            throw new EstadoInvalidoException("Apenas reservas com status CRIADA podem ser canceladas.");
        }
        if (momentoCancelamento == null || politica == null) {
            throw new DadosInvalidosException("Momento do Cancelamento e Politica de Cancelamento não podem ser null.");
        }
        BigDecimal valorEstorno = politica.calcularEstorno(this, momentoCancelamento);
        this.pessoa.creditar(valorEstorno);
        this.status = StatusReserva.CANCELADA;
    }

    public void concluir() {
        if (this.status != StatusReserva.CRIADA) {
            throw new EstadoInvalidoException("Apenas reservas com status CRIADA podem ser concluídas.");
        }
        this.status = StatusReserva.CONCLUIDA;
    }

    @Override
    public Integer getId() { return id; }
    public StatusReserva getStatus() { return status; }
    public Periodo getPeriodo() { return periodo; }
    public Pessoa getPessoa() { return pessoa; }
    public Carro getCarro() { return carro; }

    @JsonIgnore
    public BigDecimal getCustoTotal() { return calcularCustoTotal(carro, periodo); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reserva outra = (Reserva) obj;
        return this.id == outra.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}