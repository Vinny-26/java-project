---
config:
  theme: dark
  look: neo
---
classDiagram

    class dominio.StatusReserva {
      <<enum>>
      CRIADA
      CANCELADA
      CONCLUIDA
    }

    class dominio.MarcaCarro {
      <<enum>>
      FIAT
      VOLKSWAGEN
      HYUNDAI
      CHEVROLET
      +getNomeExibicao() String
      +possuiModelo(modelo: String) boolean
    }

    class dominio.Identificavel~ID~ {
      <<interface>>
      +getId() ID
    }

    class politica.PoliticaCancelamento {
      <<interface>>
      +calcularEstorno(reserva: dominio.Reserva, momentoCancelamento: LocalDateTime) BigDecimal
    }

    class politica.EstornoTotal {
      +calcularEstorno(reserva: dominio.Reserva, momentoCancelamento: LocalDateTime) BigDecimal
    }

    class politica.EstornoComMulta {
      +calcularEstorno(reserva: dominio.Reserva, momentoCancelamento: LocalDateTime) BigDecimal
    }

    class dominio.Pessoa {
      -String titular
      -String cpf
      -BigDecimal saldo
      +saldoSuficiente(valor: BigDecimal) boolean
      +debitar(valor: BigDecimal)
      +creditar(valor: BigDecimal)
    }

    class dominio.Carro {
      -String placa
      -String cor
      -boolean arCondicionado
      -BigDecimal valor
      -boolean ativo
      -dominio.MarcaCarro marca
      -String modelo
      +ativar()
      +desativar()
    }

    class dominio.Periodo {
      <<record>>
      +LocalDateTime inicio
      +LocalDateTime fim
      +temSobreposicao(outro: dominio.Periodo) boolean
      +diasDoPeriodo() long
    }

    class dominio.Reserva {
      -int id
      -dominio.StatusReserva status
      +getCustoTotal() BigDecimal
      +cancelar(momento: LocalDateTime, politica: politica.PoliticaCancelamento)
      +concluir()
      +restaurar(id: int, status: StatusReserva, periodo: Periodo, pessoa: Pessoa, carro: Carro) Reserva$
    }

    class repositorio.Repositorio~T, ID~ {
      <<interface>>
      +salvar(entidade: T)
      +buscarPorId(id: ID) Optional~T~
      +listarTodos() List~T~
      +deletarPorId(id: ID)
    }

    class repositorio.RepositorioMemoria~T, ID~ {
      -Map~ID, T~ mapa
      +salvar(entidade: T)
      +buscarPorId(id: ID) Optional~T~
      +listarTodos() List~T~
      +deletarPorId(id: ID)
    }

    class repositorio.PersistenciaJsonServico {
      -ObjectMapper objectMapper
      +salvar(caminho: Path, reservas: List~dominio.Reserva~) void
      +restaurar(caminho: Path) List~dominio.Reserva~
    }

    class servico.ConsultaReservaServico {
      -repositorio.Repositorio~dominio.Reserva, Integer~ repositorio
      +buscarReservaPorStatus(status: dominio.StatusReserva) List~dominio.Reserva~
      +buscarReservaPorCPF(cpf: String) List~dominio.Reserva~
      +buscarReservaPorMarcaCarro(marca: dominio.MarcaCarro) List~dominio.Reserva~
      +calcularFaturamentoConcluido() BigDecimal
      +carroPossuiReservaAtiva(placa: String) boolean
      +contarReservasPorStatus(status: dominio.StatusReserva) long
      +agruparTotalReservasPorStatus() Map~dominio.StatusReserva, Long~
    }

    dominio.Identificavel~Integer~ <|.. dominio.Reserva : implementa
    politica.PoliticaCancelamento <|.. politica.EstornoTotal : implementa
    politica.PoliticaCancelamento <|.. politica.EstornoComMulta : implementa
    repositorio.Repositorio~T, ID~ <|.. repositorio.RepositorioMemoria~T, ID~ : implementa

    dominio.Reserva "1" *-- "1" dominio.Periodo : composição
    dominio.Reserva "0..*" --> "1" dominio.Pessoa : responsável
    dominio.Reserva "0..*" --> "1" dominio.Carro : recurso
    dominio.Reserva "1" --> "1" dominio.StatusReserva : estado
    dominio.Reserva ..> politica.PoliticaCancelamento : usa
    dominio.Carro "0..*" --> "1" dominio.MarcaCarro : marca
    servico.ConsultaReservaServico --> repositorio.Repositorio~dominio.Reserva, Integer~ : consulta
    repositorio.PersistenciaJsonServico ..> dominio.Reserva : persiste / restaura