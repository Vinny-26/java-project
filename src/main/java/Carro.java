public class Carro {
    private final String placa;
    private String cor;
    private boolean arCondicionado;
    private int precoEmCentavos;
    private boolean ativo;
    private final String marca;
    private final String modelo;

    // Construtor com todos os atributos
    public Carro(String placa, String cor, boolean arCondicionado, int precoEmCentavos, boolean ativo, String marca, String modelo) {
        this.placa = placa;
        this.cor = cor;
        this.arCondicionado = arCondicionado;
        this.precoEmCentavos = precoEmCentavos;
        this.ativo = ativo;
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
