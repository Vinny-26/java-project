public class Carro {
    private String placa;
    private String cor;
    private boolean arCondicionado;
    private int precoEmCentavos;
    private boolean ativo;
    private final String marca;
    private final String modelo;

    // Construtor com todos os atributos
    public Carro(String placa, String cor, boolean arCondicionado, int precoEmCentavos, boolean status, String marca, String modelo) {
        if(placa == null || placa.isBlank())
        {
            throw new IllegalArgumentException("A placa do carro não pode ser nula ou vazia");
        }
        if(cor == null || cor.isBlank())
        {
            throw new IllegalArgumentException("Não é possível inserir carro com cor nula ou vazia");
        }
        if(marca == null || marca.isBlank())
        {
            throw new IllegalArgumentException("Não é possível cadastrar carro com marca nula ou vazia");
        }
        if (modelo == null || modelo.isBlank())
        {
            throw new IllegalArgumentException("Não é possível cadastrar carro com modelo nulo ou vazio");
        }
        if(placa.length() != 7)
        {
            throw new IllegalArgumentException("A placa do carro deve conter exatamente 7 caracteres");
        }
        if(precoEmCentavos <= 0)
        {
            throw new IllegalArgumentException("Não é possível cadastrar carro com valor menor ou igual a 0");
        }
        placa = placa.trim().toUpperCase();
        this.placa = placa;
        this.cor = cor; // Estudar possível enum
        this.arCondicionado = arCondicionado;
        this.precoEmCentavos = precoEmCentavos;
        this.ativo = status;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }
    public String getCor() {
        return cor;
    }
    public boolean getArCondicionado() {
        return arCondicionado;
    }
    public int getPrecoEmCentavos() {
        return precoEmCentavos;
    }
    public boolean getAtivo() {
        return ativo;
    }
    public String getMarca() {
        return marca;
    }
    public String getModelo() {
        return modelo;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
    public void setArCondicionado(boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    public void ativar() {
        if (this.ativo) {
            throw new IllegalArgumentException("O carro já está alugado!");
        }
        this.ativo = true;
    }
    public void desativar() {
        if (!this.ativo) {
            throw new IllegalArgumentException("O carro já está disponível!");
        }
        System.out.println("Carro devolvido com sucesso!");
    }

}
