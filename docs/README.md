README:
# Plataforma de Reservas

## Equipe

- Integrante 1: Vinícius
- Integrante 2: Felipe
- Integrante 3: Nicolas

## Variante

- Variante: dominio.Carro
- Regra específica: Um carro só pode ser alugado por uma pessoa de cada vez, devemos utilizar um identificador único para cada veículo.

## Diagrama de Classes

```mermaid
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

class servico.ConsultaReservaServico {
-repositorio.Repositorio~dominio.Reserva, Integer~ repositorio
+buscarReservaPorStatus(status: dominio.StatusReserva) List~dominio.Reserva~
+buscarReservaPorCPF(cpf: String) List~dominio.Reserva~
+buscarReservaPorMarcaCarro(marca: dominio.MarcaCarro) List~dominio.Reserva~
+calcularFaturamentoConcluido() BigDecimal
+CarroPossuiReservaAtiva(placa: String) boolean
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
```

## Requisitos atendidos

1. Classes iniciais e métodos implementadas
2. Método Equals reimplementado nas classes dominio.Carro e dominio.Pessoa
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
