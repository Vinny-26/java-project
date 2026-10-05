README:
# Plataforma de Reservas

## Equipe

- Integrante 1: Vinícius
- Integrante 2: Felipe
- Integrante 3: Nicolas

## Variante

- Variante: Veículos
- Regra específica: Um veículo só pode ser alugado por uma pessoa de cada vez, devemos utilizar um identificador único para cada veículo.

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
Adicione um "OK" sempre que um requisito for atendido!
1. Implementar classe veículo com restrições seguras(identificador único imutável)
2. Sobrescrever método Equals e testar
3. Estruturar métodos necessários. (Reserva, Pagamento, fimReserva)

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
