README:
# Plataforma de Reservas

## Equipe

- Integrante 1: Vinícius
- Integrante 2: Felipe
- Integrante 3: Nicolas

## Variante

- Variante: Carro
- Regra específica: Um carro só pode ser alugado por uma pessoa de cada vez, devemos utilizar um identificador único para cada veículo.

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
    
    class Pessoa {
      -String nome
      -String cpf
      -int saldoEmCentavos
    }
    
    class Carro {
      -String place
      -String cor
      -boolean arCondicionado
      -int precoEmCentavos
      -boolean ativo
      +ativar()
      +desativar()
      +boolean estaAtivo()
    }

    class Periodo {
        <<record>>
        +LocalDateTime inicio
        +LocalDateTime fim
        +temSobreposicao(outro: Periodo) boolean
    }

    class Reserva {
      -int id
      +cancelar()
      -StatusReserva status
    }
    
    Reserva "1" *-- "1" Periodo : composição
    Reserva "0..*" --> "1" Pessoa : responsável
    Reserva "0..*" --> "1" Carro : recurso
    Reserva "1" --> "1" StatusReserva : estado
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
