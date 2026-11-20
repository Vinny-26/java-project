package dominio;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    private final BigDecimal custoTotal;

    /**
     * Construtor privado mestre.
     * Controla se o débito financeiro deve ser executado (criação) ou ignorado (restauração).
     */
    private Reserva(int id, StatusReserva status, Periodo periodo, Pessoa pessoa, Carro carro, CalculoPreco calculoPreco,
                    BigDecimal custoRestaurado, boolean debitar)
    {
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

        if (custoRestaurado != null)
        {
            this.custoTotal = custoRestaurado;
        }
        else
        {
            if (calculoPreco == null)
            {
                throw new DadosInvalidosException("A estratégia de calculo de preço não pode ser nula!");
            }
            this.custoTotal = calculoPreco.calcular(carro, periodo);
        }
        if (debitar)
        {
            this.pessoa.debitar(getCustoTotal());
        }
    }

    /**
     * Construtor padrão da aplicação para criar novas reservas no domínio.
     * Define o status como CRIADA e utiliza PrecoPadrao.
     */
    public Reserva(int id, Periodo periodo, Pessoa pessoa, Carro carro) {
        this(id, StatusReserva.CRIADA, periodo, pessoa, carro, new PrecoPadrao(), null, true);
    }

    /**
     * Construtor com estratégia de preço customizada e status CRIADA.
     */
    public Reserva(int id, Periodo periodo, Pessoa pessoa, Carro carro, CalculoPreco calculoPreco) {
        this(id, StatusReserva.CRIADA, periodo, pessoa, carro, calculoPreco, null, true);
    }

    /**
     * Construtor com status explícito e preço padrão (retrocompatibilidade) com a abordagem utilizada anteriormente.
     */
    public Reserva(int id, StatusReserva status, Periodo periodo, Pessoa pessoa, Carro carro) {
        this(id, status, periodo, pessoa, carro, new PrecoPadrao(), null, true);
    }

    /**
     * Construtor de domínio com status explícito.
     * Efetua o débito financeiro.
     **/
    public Reserva(int id, StatusReserva status, Periodo periodo, Pessoa pessoa, Carro carro, CalculoPreco calculoPreco) {
        this(id, status, periodo, pessoa, carro, calculoPreco, null, true);
    }


    // Método de fabrica utilizado pelo Jackson para RESTAURAR uma Reserva do JSON.

    @JsonCreator
    public static Reserva restaurar(
            @JsonProperty("id") int id,
            @JsonProperty("status") StatusReserva status,
            @JsonProperty("periodo") Periodo periodo,
            @JsonProperty("pessoa") Pessoa pessoa,
            @JsonProperty("carro") Carro carro,
            @JsonProperty("custoTotal") BigDecimal custoTotal)
    {
        if (custoTotal == null || custoTotal.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new DadosInvalidosException("O custo total restaurado deve ser maior ou igual a 0!");
        }
        return new Reserva(id, status, periodo, pessoa, carro, null, custoTotal, false);
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
    public BigDecimal getCustoTotal() { return custoTotal; }

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