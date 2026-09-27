public static final class Pessoa
{
    private String titular;
    private int saldo;

    public void setTitular(String titular)
    {
        this.titular = titular;
    }
}

public static void main(String[] args)
{
    Pessoa p = new Pessoa();
    p.setTitular("Niggolas");
    System.out.println(p.titular);
}

