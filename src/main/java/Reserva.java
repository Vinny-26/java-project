public class reserva {
    private int id;
    private StatusReserva status; //Enum (Guarda 1 de 3 opções "CANCELADA, CRIADA E CONCLUIDA")
    private Periodo periodo; //Record composição (guarda as datas de início e fim da resrva)
    private Responsavel pessoa; //Associação (classe pessoa/responsável)
    private Recurso carro; //Associação (classe recurso)

public reserva(int id, Periodo periodo, Responsavel pessoa, Recurso carro) {
    this.id = id;
    this.periodo = periodo;
    this.pessoa = pessoa;
    this.carro = carro;
    this.status = StatusReserva.CRIADA; //Iniciar o status como criada ;)

}
public void cancelar() {
    this.status = StatusReserva.CANCELADA;
}

public void concluir() {
    this.status = StatusReserva.CONCLUIDA;
}


public int getid() {return id; }
public StatusResrva getstatus() {return status; }
public Periodo getperiodo() {return periodo; }
public Responsavel getpessoa() {return pessoa; }
public Recurso getcarro() {return recurso; }
}
