import java.util.Scanner;

public class carro {
    private String placa, marca;
    private int modelo;
    private boolean arCondicionado, disponivel;

    // Leitor estático acessível por toda a classe
    private static Scanner leitor = new Scanner(System.in);

    // set atribui valor
    public void setMarca(String marca) {
        this.marca = marca;
    }

    // get lê valor (sem parâmetro)
    public String getMarca() {
        return this.marca;
    }

    boolean isDisponivel() {
