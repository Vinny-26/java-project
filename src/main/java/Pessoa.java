private static final class Pessoa
{
    private String titular;
    private int saldo;
    private int cpf;

    public void setTitular(String titular)
    {
        this.titular = titular;
    }
    public String getTitular()
    {
        return this.titular;
    }
    public int getSaldo()
    {
        return this.saldo;
    }
}

public static void main(String[] args)
{
    Pessoa p = new Pessoa();
    p.setTitular("Niggolas");
    System.out.println(p.getTitular());
    System.out.println(p.getSaldo());
}

