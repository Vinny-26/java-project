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
      +cancelar()
      +concluir()
    }

    Reserva "1" *-- "1" Periodo : composição
    Reserva "0..*" --> "1" Pessoa : responsável
    Reserva "0..*" --> "1" Carro : recurso
    Reserva "1" --> "1" StatusReserva : estado
    Carro "0..*" --> "1" MarcaCarro : marca