import excecoes.DadosInvalidosException;
import java.math.BigDecimal;
import excecoes.SaldoInsuficienteException;

public class Pessoa {
    private final String titular;
    private BigDecimal saldo;
    private final String cpf;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Pessoa outro = (Pessoa) obj;

        return this.cpf.equals(outro.cpf);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(cpf);
    }

    public Pessoa(String titular, BigDecimal saldo, String cpf) {
        if (titular == null || titular.isBlank()) {
            throw new DadosInvalidosException("O nome do titular deve estar preenchido e não pode ser nulo");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new DadosInvalidosException("O cpf do titular deve estar preenchido e não pode ser nulo");
        }
        if (cpf.length() != 11) {
            throw new DadosInvalidosException("O cpf do titular deve conter apenas 11 digitos");
        }
        if (saldo == null || saldo.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new DadosInvalidosException("O saldo do titular não pode ser menor que 0 ou nulo");
        }
        this.titular = titular;
        this.saldo = saldo;
        this.cpf = cpf;
    }

    public String getTitular() {
        return this.titular;
    }

    public BigDecimal getSaldo() {
        return this.saldo;
    }

    public String getCpf() {
        return this.cpf;
    }

    boolean saldoSuficiente(BigDecimal valor) {
        return this.saldo.compareTo(valor) >= 0;
    }

    public void debitar(BigDecimal valor) {

        if(valor == null)
        {
            throw new DadosInvalidosException("O valor para débito não pode ser nulo");
        }

        if (valor.compareTo(BigDecimal.ZERO) <=0)
        {
            throw new DadosInvalidosException("O valor para débito deve ser estritamente positivo");
        }
        if (!saldoSuficiente(valor)) {
            throw new SaldoInsuficienteException("Saldo insuficiente para efetuar a Reserva");
        }
        this.saldo = this.saldo.subtract(valor);
    }
    public void creditar(BigDecimal valor)
    {

        if(valor == null)
        {
            throw new DadosInvalidosException("O valor para creditar não pode ser nulo");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new DadosInvalidosException("O valor a ser creditado deve ser estritamente positivo");
        }
        else
        {
            this.saldo = this.saldo.add(valor);
        }
    }
}

