README:
# Plataforma de Reservas

## Equipe

- Integrante 1: Vinícius
- Integrante 2: Felipe
- Integrante 3: Nicolas

## Variante

- Variante: Carro
- Regra específica: Um carro só pode ser alugado por uma pessoa de cada vez, devemos utilizar um identificador único para cada veículo.

## Repositório

- URL: https://github.com/Vinny-26/java-project
- Branch: main
- Hash da entrega: ed65e2bb99845a6eeaec03438a19a7da8c7d170e

## Diagrama de Classes

```mermaid
---
config:
  theme: dark
  look: neo
---
classDiagram

    class StatusReserva {
        <<enum>>
        CRIADA
        CANCELADA
        CONCLUIDA
    }

    class MarcaCarro {
        <<enum>>
        FIAT
        VOLKSWAGEN
        HYUNDAI
        CHEVROLET
        +getNomeExibicao() String
        +possuiModelo(modelo: String) boolean
    }

    class Identificavel~ID~ {
        <<interface>>
        +getId() ID
    }

    class PoliticaCancelamento {
        <<interface>>
        +calcularEstorno(reserva: Reserva, momentoCancelamento: LocalDateTime) BigDecimal
    }

    class EstornoTotal {
        +calcularEstorno(reserva: Reserva, momentoCancelamento: LocalDateTime) BigDecimal
    }

    class EstornoComMulta {
        +calcularEstorno(reserva: Reserva, momentoCancelamento: LocalDateTime) BigDecimal
    }

    class Pessoa {
        -String titular
        -String cpf
        -BigDecimal saldo
        +saldoSuficiente(valor: BigDecimal) boolean
        +debitar(valor: BigDecimal)
        +creditar(valor: BigDecimal)
    }

    class Carro {
        -String placa
        -String cor
        -boolean arCondicionado
        -BigDecimal valor
        -boolean ativo
        -MarcaCarro marca
        -String modelo
        +ativar()
        +desativar()
    }

    class Periodo {
        <<record>>
        +LocalDateTime inicio
        +LocalDateTime fim
        +temSobreposicao(outro: Periodo) boolean
        +diasDoPeriodo() long
    }

    class Reserva {
        -int id
        -StatusReserva status
        +getCustoTotal() BigDecimal
        +cancelar(momento: LocalDateTime, politica: PoliticaCancelamento)
        +concluir()
    }

    class Repositorio~T, ID~ {
<<interface>>
+salvar(entidade: T)
+buscarPorId(id: ID) Optional~T~
+listarTodos() List~T~
+deletarPorId(id: ID)
}

class RepositorioMemoria~T, ID~ {
-Map~ID, T~ mapa
+salvar(entidade: T)
+buscarPorId(id: ID) Optional~T~
+listarTodos() List~T~
+deletarPorId(id: ID)
}

class ConsultaReservaServico {
-Repositorio~Reserva, Integer~ repositorio
+buscarReservaPorStatus(status: StatusReserva) List~Reserva~
+buscarReservaPorCPF(cpf: String) List~Reserva~
+buscarReservaPorMarcaCarro(marca: MarcaCarro) List~Reserva~
+calcularFaturamentoConcluido() BigDecimal
+CarroPossuiReservaAtiva(placa: String) boolean
    }

Identificavel~Integer~ <|.. Reserva : implementa
PoliticaCancelamento <|.. EstornoTotal : implementa
PoliticaCancelamento <|.. EstornoComMulta : implementa
Repositorio~T, ID~ <|.. RepositorioMemoria~T, ID~ : implementa

Reserva "1" *-- "1" Periodo : composição
Reserva "0..*" --> "1" Pessoa : responsável
Reserva "0..*" --> "1" Carro : recurso
Reserva "1" --> "1" StatusReserva : estado
Reserva ..> PoliticaCancelamento : usa
Carro "0..*" --> "1" MarcaCarro : marca
ConsultaReservaServico --> Repositorio~Reserva, Integer~ : consulta
```

## Requisitos atendidos

1. Classes iniciais e métodos implementadas
2. Método Equals reimplementado nas classes Carro e Pessoa
3. Testes feitos e validados em todas as classes e Record

Atualize esta seção em cada marco do projeto.

## Como executar

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

## Estrutura
As classes de domínio serão criadas pela equipe depois da escolha da variante.
Não adicione camadas, interfaces ou padrões antes de existir um requisito que
justifique a decisão.
