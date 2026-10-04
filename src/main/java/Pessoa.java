public class Pessoa
{
    private final String titular;
    private int saldoEmCentavos;
    private final String cpf;
    public Pessoa(String titular, int saldoEmCentavos, String cpf)
    {
            this.titular = titular;
            this.saldoEmCentavos = saldoEmCentavos;
            this.cpf = cpf; // reimplementar Equals usando cpf
    }
}

