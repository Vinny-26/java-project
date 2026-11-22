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

    class politica.CalculoPreco {
    <<interface>>
    +calcular(carro: dominio.Carro, periodo: dominio.Periodo) BigDecimal
    }
    
    class politica.PrecoPadrao {
    +calcular(carro: dominio.Carro, periodo: dominio.Periodo) BigDecimal
    }

    class politica.PrecoDescontoLongaDuracao {
    +calcular(carro: dominio.Carro, periodo: dominio.Periodo) BigDecimal
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
        -BigDecimal custoTotal
        +getCustoTotal() BigDecimal
        +cancelar(momento: LocalDateTime, politica: politica.PoliticaCancelamento)
        +concluir()
        +restaurar(...) dominio.Reserva$
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
    +salvar(caminho: Path, reservas: List~dominio.Reserva~)
    +restaurar(caminho: Path) List~dominio.Reserva~
    }
    
    class servico.ReservaServico {
    -repositorio.Repositorio~dominio.Reserva, Integer~ repositorio
    +criarReserva(id: int, periodo: dominio.Periodo, pessoa: dominio.Pessoa, carro: dominio.Carro) dominio.Reserva
    +criarReserva(id: int, periodo: dominio.Periodo, pessoa: dominio.Pessoa, carro: dominio.Carro, calculoPreco: politica.CalculoPreco) dominio.Reserva
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
    +listarReservasOrdenadasPorDataInicio() List~dominio.Reserva~
    +listarReservasOrdenadasPorCustoDecrescente() List~dominio.Reserva~
    }

    class app.Main {
    +main(args: String[])$
    }

    dominio.Identificavel~Integer~ <|.. dominio.Reserva : implementa
    politica.PoliticaCancelamento <|.. politica.EstornoTotal : implementa
    politica.PoliticaCancelamento <|.. politica.EstornoComMulta : implementa
    politica.CalculoPreco <|.. politica.PrecoPadrao : implementa
    politica.CalculoPreco <|.. politica.PrecoDescontoLongaDuracao : implementa
    repositorio.Repositorio~T, ID~ <|.. repositorio.RepositorioMemoria~T, ID~ : implementa

    dominio.Reserva "1" *-- "1" dominio.Periodo : composição
    dominio.Reserva "0..*" --> "1" dominio.Pessoa : responsável
    dominio.Reserva "0..*" --> "1" dominio.Carro : recurso
    dominio.Reserva "1" --> "1" dominio.StatusReserva : estado
    dominio.Reserva ..> politica.PoliticaCancelamento : usa
    dominio.Reserva ..> politica.CalculoPreco : usa 
    dominio.Carro "0..*" --> "1" dominio.MarcaCarro : marca
    servico.ReservaServico --> repositorio.Repositorio~dominio.Reserva, Integer~ : coordena
    servico.ConsultaReservaServico --> repositorio.Repositorio~dominio.Reserva, Integer~ : consulta
    repositorio.PersistenciaJsonServico ..> dominio.Reserva : restaura
    app.Main ..> servico.ReservaServico : executa
    app.Main ..> servico.ConsultaReservaServico : executa
    app.Main ..> repositorio.PersistenciaJsonServico : executa