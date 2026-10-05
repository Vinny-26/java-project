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