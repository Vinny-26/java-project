import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarroTest {

@Test
    void criacaoValida()
{
    String placa = "A6EVBCD";
    String cor = "Azul";
    boolean arCondicionado = true;
    int precoEmCentavos = 1000;
    boolean ativo = true;
    String marca = "Hyundai";
    String modelo = "HB-20";

    Carro carro = new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo);

    assertEquals(placa, carro.getPlaca());
    assertEquals(cor, carro.getCor());
    assertTrue(carro.getArCondicionado());
    assertEquals(precoEmCentavos, carro.getPrecoEmCentavos());
    assertTrue(carro.getAtivo());
    assertEquals(marca, carro.getMarca());
    assertEquals(modelo, carro.getModelo());
}

@Test
    void placaTamanhoInvalidaCriacao()
    {
        String placa = "aaecdeasv23";
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";

        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void placaNulaInvalidaCriacao()
    {
        String placa = null ;
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";

        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }
    @Test
    void placaVaziaInvalidaCriacao()
    {
        String placa = " " ;
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void corNulaInvalidaCriacao()
    {
        String placa = "a2cd4ef" ;
        String cor = null;
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";

        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void corVaziaInvalidaCriacao()
    {
        String placa = "ad3246c" ;
        String cor = " ";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void marcaNulaInvalidaCriacao()
    {
        String placa = "a2cd4ef" ;
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = null;
        String modelo = "HB-20";

        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void marcaVaziaInvalidaCriacao()
    {
        String placa = "ad3246c" ;
        String cor = "Roxo";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = " ";
        String modelo = "HB-20";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void modeloNuloInvalidaCriacao()
    {
        String placa = "a2cd4ef" ;
        String cor = "Vermelho";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void modeloVazioInvalidaCriacao()
    {
        String placa = "ad3246c" ;
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = " ";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void carroGratuitoInvalidaCriacao()
    {
        String placa = "ad3246c" ;
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 0;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void carroNegativoInvalidaCriacao()
    {
        String placa = "ad3246c" ;
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = -1;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo)
        );
    }

    @Test
    void carroComMesmaPlacaSaoIguais()
    {
        String placaA = "A6EVBCD";
        String corA = "Azul";
        boolean arCondicionadoA = true;
        int precoEmCentavosA = 1000;
        boolean ativoA = true;
        String marcaA = "Hyundai";
        String modeloA = "HB-20";

        String placaB = "A6EVBCD";
        String corB = "Vermelho";
        boolean arCondicionadoB = true;
        int precoEmCentavosB = 1500;
        boolean ativoB = true;
        String marcaB = "Hyundai";
        String modeloB = "HB-20";

        Carro carroA = new Carro(placaA, corA, arCondicionadoA, precoEmCentavosA, ativoA, marcaA, modeloA);
        Carro carroB = new Carro(placaB, corB, arCondicionadoB, precoEmCentavosB, ativoB, marcaB, modeloB);

        assertEquals(carroA, carroB);
    }

    @Test
    void carroComPlacasDiferentesNaoSaoIguais()
    {
        String placaA = "A6EVBCD";
        String corA = "Azul";
        boolean arCondicionadoA = true;
        int precoEmCentavosA = 1000;
        boolean ativoA = true;
        String marcaA = "Hyundai";
        String modeloA = "HB-20";

        String placaB = "A6EVBCT";
        String corB = "Vermelho";
        boolean arCondicionadoB = true;
        int precoEmCentavosB = 1500;
        boolean ativoB = true;
        String marcaB = "Hyundai";
        String modeloB = "HB-20";

        Carro carroA = new Carro(placaA, corA, arCondicionadoA, precoEmCentavosA, ativoA, marcaA, modeloA);
        Carro carroB = new Carro(placaB, corB, arCondicionadoB, precoEmCentavosB, ativoB, marcaB, modeloB);

        assertNotEquals(carroA, carroB);
    }

    @Test
    void contratoHashCode()
    {
        String placaA = "A6EVBCD";
        String corA = "Azul";
        boolean arCondicionadoA = true;
        int precoEmCentavosA = 1000;
        boolean ativoA = true;
        String marcaA = "Hyundai";
        String modeloA = "HB-20";

        String placaB = "A6EVBCD";
        String corB = "Vermelho";
        boolean arCondicionadoB = true;
        int precoEmCentavosB = 1500;
        boolean ativoB = true;
        String marcaB = "Hyundai";
        String modeloB = "HB-20";

        Carro carroA = new Carro(placaA, corA, arCondicionadoA, precoEmCentavosA, ativoA, marcaA, modeloA);
        Carro carroB = new Carro(placaB, corB, arCondicionadoB, precoEmCentavosB, ativoB, marcaB, modeloB);

        assertEquals(carroA.hashCode(), carroB.hashCode());
    }

    @Test
    void comparacaoComNuloOuOutroTipo()
    {
        String placaA = "A6EVBCD";
        String corA = "Azul";
        boolean arCondicionadoA = true;
        int precoEmCentavosA = 1000;
        boolean ativoA = true;
        String marcaA = "Hyundai";
        String modeloA = "HB-20";

        Carro carroA = new Carro(placaA, corA, arCondicionadoA, precoEmCentavosA, ativoA, marcaA, modeloA);

        assertFalse(carroA.equals(null));
        assertFalse(carroA.equals("String de teste"));
    }

    @Test
    void ativarComSucesso()
    {
        String placa = "A6EVBCD";
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = false;
        String marca = "Hyundai";
        String modelo = "HB-20";

        Carro carro = new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo);

        carro.ativar();

        assertTrue(carro.getAtivo());
    }

    @Test
    void ativaCarroAtivo()
    {
        String placa = "A6EVBCD";
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";

        Carro carro = new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo);

        assertThrows(
                IllegalStateException.class,
                () -> carro.ativar()
        );
    }

    @Test
    void desativarComSucesso()
    {
        String placa = "A6EVBCD";
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = true;
        String marca = "Hyundai";
        String modelo = "HB-20";

        Carro carro = new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo);

        carro.desativar();

        assertFalse(carro.getAtivo());
    }

    @Test
    void desativarCarroDesativado()
    {
        String placa = "A6EVBCD";
        String cor = "Azul";
        boolean arCondicionado = true;
        int precoEmCentavos = 1000;
        boolean ativo = false;
        String marca = "Hyundai";
        String modelo = "HB-20";

        Carro carro = new Carro(placa, cor, arCondicionado, precoEmCentavos, ativo, marca, modelo);

        assertThrows(
                IllegalStateException.class,
                () -> carro.desativar()
        );
    }
}
