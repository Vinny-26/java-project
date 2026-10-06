public class Pessoa
{
    private final String titular;
    private int saldoEmCentavos;
    private final String cpf;

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Pessoa outro = (Pessoa) obj;

        return this.cpf.equals(outro.cpf);
    }

    @Override
    public int hashCode()
    {
        return java.util.Objects.hash(cpf);
    }

    public Pessoa(String titular, int saldoEmCentavos, String cpf)
    {
        if(titular == null || titular.isBlank())
        {
            throw new IllegalArgumentException("O nome do titular deve estar preenchido e não pode ser nulo");
        }
        if(cpf == null || cpf.isBlank())
        {
            throw new IllegalArgumentException("O cpf do titular deve estar preenchido e não pode ser nulo");
        }
        if(cpf.length() != 11)
        {
            throw new IllegalArgumentException("O cpf do titular deve conter apenas 11 digitos");
        }
        if(saldoEmCentavos <= 0)
        {
            throw new IllegalArgumentException("O saldo do titular não pode ser menor ou igual a 0");
        }
            this.titular = titular;
            this.saldoEmCentavos = saldoEmCentavos;
            this.cpf = cpf;
    }

    public String getTitular()
    {
        return this.titular;
    }
    public int getSaldoEmCentavos()
    {
        return this.saldoEmCentavos;
    }
    public String getCpf()
    {
        return this.cpf;
    }

    boolean saldoSuficiente(int valorEmCentavos)
    {
        return this.saldoEmCentavos >= valorEmCentavos;
    }

    public void debitar(int valorEmCentavos)
    {
        if(valorEmCentavos > 0)
        {
            if(!saldoSuficiente(valorEmCentavos))
            {
                throw new IllegalArgumentException("Voce não possui saldo suficiente para efetuar a Reserva");
            }
            else
            {
                this.saldoEmCentavos -= valorEmCentavos;
            }
        }
        else
        {
            throw new IllegalArgumentException("Não é possível debitar um valor negativo");
        }
    }
    public void creditar(int valorEmCentavos)
    {
        if(valorEmCentavos > 0)
        {
            this.saldoEmCentavos += valorEmCentavos;
        }
        else
        {
            throw new IllegalArgumentException("Não é possível creditar valor negativo");
        }
    }
}

