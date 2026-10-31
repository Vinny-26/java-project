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