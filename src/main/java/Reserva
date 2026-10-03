public class reserva {
    private int id;
    private StatusReserva status; //Enum (Guarda 1 de 3 opções "CANCELADA, CRIADA E CONCLUIDA")
    private Periodo periodo; //Record composição (guarda as datas de início e fim da resrva)
    private Pessoa responsavel; //Associação (classe pessoa/responsável)
    private Carro recurso; //Associação (classe recurso)

public reserva(int id, Periodo periodo, Pessoa responsavel, Carro recurso) {
    this.id = id;
    this.periodo = periodo;
    this.responsavel = responsavel;
    this.recurso = recurso;
    this.status = StatusReserva.CRIADA; //Iniciar o status como criada ;)

}
public void cancelar() {
    this.status = StatusReserva.CANCELADA;
}

public int getid() {return id; }
public StatusResrva getstatus() {return status; }
public Periodo getperiodo() {return periodo; }
public Pessoa getresponsavel() {return responsavel; }
public Carro getrecurso() {return recurso; }
}
