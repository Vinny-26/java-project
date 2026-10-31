import excecoes.DadosInvalidosException;
import excecoes.EstadoInvalidoException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reserva implements Identificavel<Integer> {
    private final int id;
    private StatusReserva status; //Enum (Guarda 1 de 3 opções "CANCELADA, CRIADA E CONCLUIDA")
    private final Periodo periodo; //Record composição (guarda as datas de início e fim da resrva)
    private final Pessoa pessoa; //Associação (classe pessoa/responsável)
    private final Carro carro; //Associação (classe recurso)

public Reserva(int id, Periodo periodo, Pessoa pessoa, Carro carro)
{
    if( id <= 0 || periodo == null || pessoa == null || carro == null)
    {
        throw new DadosInvalidosException("Não é possível criar Reserva com dados inválidos");
    }
    if (!carro.isAtivo())
    {
        throw new EstadoInvalidoException("Não é possível criar a reserva, o carro está inativo");
    }

    this.id = id;
    this.periodo = periodo;
    this.pessoa = pessoa;
    this.carro = carro;
    this.status = StatusReserva.CRIADA; //Iniciar o status como criada ;)
    pessoa.debitar(getCustoTotal());
}

    private BigDecimal calcularCustoTotal(Carro carro, Periodo periodo)
    {
        return carro.getValor().multiply(BigDecimal.valueOf(periodo.diasDoPeriodo()));
    }

public void cancelar(LocalDateTime momentoCancelamento, PoliticaCancelamento politica) {
    if (this.status != StatusReserva.CRIADA)
    {
        throw new EstadoInvalidoException("Apenas reservas com status CRIADA podem ser canceladas.");
    }
    if(momentoCancelamento == null || politica == null)
    {
        throw new DadosInvalidosException("Momento do Cancelamento e Politica de Cancelamento não podem ser null.");
    }
    BigDecimal valorEstorno = politica.calcularEstorno(this, momentoCancelamento);
    this.pessoa.creditar(valorEstorno);
    this.status = StatusReserva.CANCELADA;
}

public void concluir() {
    if (this.status != StatusReserva.CRIADA)
    {
        throw new EstadoInvalidoException("Apenas reservas com status CRIADA podem ser concluídas.");
    }
    this.status = StatusReserva.CONCLUIDA;

}
public Integer getId() {return id; }
public StatusReserva getStatus() {return status; }
public Periodo getPeriodo() {return periodo; }
public Pessoa getPessoa() {return pessoa; }
public Carro getCarro() {return carro; }
public BigDecimal getCustoTotal() {return calcularCustoTotal(carro, periodo);}

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reserva outra = (Reserva) obj;
        return this.id == outra.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

}
