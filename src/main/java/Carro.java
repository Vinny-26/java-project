import java.util.Scanner;
public class Carro {
    private String placa;
    private String cor;
    private boolean arCondicionado;
    private int precoEmCentavos;
    private boolean disponivel;
    private String marca;
    private String modelo;

    // Construtor com todos os atributos
    public Carro(String placa, String cor, boolean arCondicionado, int precoEmCentavos, boolean disponivel, String marca, String modelo) {
        this.placa = placa;
        this.cor = cor;
        this.arCondicionado = arCondicionado;
        this.precoEmCentavos = precoEmCentavos;
        this.disponivel = disponivel;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isArCondicionado() {
        return arCondicionado;
    }
    public void setArCondicionado(boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    public int getPrecoEmCentavos() {
        return precoEmCentavos;
    }
    public void setPrecoEmCentavos(int precoEmCentavos) {
        this.precoEmCentavos = precoEmCentavos;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void alugar() {
        if (!this.disponivel) {
            throw new IllegalStateException("O carro não está disponível.");
        }
        this.disponivel = false;
    }

    public void devolver() {
        this.disponivel = true;
        System.out.println("Carro devolvido com sucesso!");
    }

    //Foi a IA que gerou esse teste
    @Override
    public String toString() {
        return "Carro {" +
                "marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", cor='" + cor + '\'' +
                ", arCondicionado=" + arCondicionado +
                ", precoEmCentavos=" + precoEmCentavos +
                ", disponivel=" + disponivel +
                '}';
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);

        System.out.println("<--- CADASTRO DE ALUGUEL --->");

        System.out.print("Digite a marca do carro: ");
        String marca = leitor.nextLine();

        System.out.print("Digite o modelo do carro: ");
        String modelo = leitor.nextLine();

        System.out.print("Digite a placa: ");
        String placa = leitor.nextLine();

        System.out.print("Digite a cor: ");
        String cor = leitor.nextLine();

        System.out.print("Deseja ar condicionado? (SIM - NAO): ");
        boolean arCondicionado = leitor.nextLine().equalsIgnoreCase("s");

        System.out.print("Digite o preço em centavos (ex: 15000): ");
        int precoEmCentavos = Integer.parseInt(leitor.nextLine());

        Carro meuCarro = new Carro(placa, cor, arCondicionado, precoEmCentavos, true, marca, modelo);

        System.out.println("\nCarro cadastrado com sucesso:");
        System.out.println(meuCarro);

        leitor.close();
    }
}
