public class Reserva {
    private int id;
    private StatusReserva status; //Enum (Guarda 1 de 3 opções "CANCELADA, CRIADA E CONCLUIDA")
    private Periodo periodo; //Record composição (guarda as datas de início e fim da resrva)
    private Pessoa pessoa; //Associação (classe pessoa/responsável)
    private Carro carro; //Associação (classe recurso)

public Reserva(int id, Periodo periodo, Pessoa pessoa, Carro carro)
{
    if( id <= 0 || periodo == null || pessoa == null || carro == null)
    {
        throw new IllegalArgumentException("Não é possível criar Reserva com dados inválidos");
    }
    if (!carro.getAtivo())
    {
        throw new IllegalStateException("Não é possível criar a reserva, o carro está inativo");
    }

    int custoTotal = carro.getPrecoEmCentavos() * periodo.diasDoPeriodo();

    pessoa.debitar(custoTotal);

    this.id = id;
    this.periodo = periodo;
    this.pessoa = pessoa;
    this.carro = carro;
    this.status = StatusReserva.CRIADA; //Iniciar o status como criada ;)
}

public void cancelar() {
    if (this.status != StatusReserva.CRIADA)
    {
        throw new IllegalStateException("Apenas reservas com status CRIADA podem ser canceladas.");
    }
    this.status = StatusReserva.CANCELADA;
}

public void concluir() {
    if (this.status != StatusReserva.CRIADA)
    {
        throw new IllegalStateException("Apenas reservas com status CRIADA podem ser concluídas.");
    }
    this.status = StatusReserva.CONCLUIDA;

}

public int getId() {return id; }
public StatusReserva getStatus() {return status; }
public Periodo getPeriodo() {return periodo; }
public Pessoa getPessoa() {return pessoa; }
public Carro getCarro() {return carro; }
}
