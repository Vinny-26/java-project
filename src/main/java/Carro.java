import excecoes.DadosInvalidosException;
import excecoes.EstadoInvalidoException;

public class Carro {
    private String placa;
    private String cor;
    private boolean arCondicionado;
    private int precoEmCentavos;
    private boolean ativo;
    private final MarcaCarro marca;
    private final String modelo;

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Carro outro = (Carro) obj;

        return this.placa.equals(outro.placa);
    }

    @Override
    public int hashCode()
    {
        return java.util.Objects.hash(placa);
    }

    // Construtor com todos os atributos
    public Carro(String placa, String cor, boolean arCondicionado, int precoEmCentavos, boolean status, MarcaCarro marca, String modelo) {
        if(placa == null || placa.isBlank())
        {
            throw new DadosInvalidosException("A placa do carro não pode ser nula ou vazia");
        }
        if(cor == null || cor.isBlank())
        {
            throw new DadosInvalidosException("Não é possível inserir carro com cor nula ou vazia");
        }
        if(marca == null)
        {
            throw new DadosInvalidosException("A marca do carro não pode ser nula");
        }
        if(!marca.possuiModelo(modelo))
        {
            throw new DadosInvalidosException("O modelo " + modelo + " não pertence à marca " + marca.getNomeExibicao());
        }
        placa = placa.trim().toUpperCase();
        if(placa.length() != 7)
        {
            throw new DadosInvalidosException("A placa do carro deve conter exatamente 7 caracteres");
        }
        if(precoEmCentavos <= 0)
        {
            throw new DadosInvalidosException("Não é possível cadastrar carro com valor menor ou igual a 0");
        }
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
    public boolean hasArCondicionado() {
        return arCondicionado;
    }
    public int getPrecoEmCentavos() {
        return precoEmCentavos;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public MarcaCarro getMarca() {
        return marca;
    }
    public String getModelo() {
        return modelo;
    }

    public void setCor(String cor) {
        if(cor == null || cor.isBlank())
            throw new DadosInvalidosException("Não é possível alterar a cor do carro para null ou vazia!");
        this.cor = cor;
    }
    public void setArCondicionado(boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    public void ativar() {
        if (this.ativo) {
            throw new EstadoInvalidoException("O carro já está ativo no sistema!");
        }
        this.ativo = true;
    }
    public void desativar() {
        if (!this.ativo) {
            throw new EstadoInvalidoException("O carro já está inativo no sistema!");
        }
        this.ativo = false;
    }
}
