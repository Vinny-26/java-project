# Decisões do projeto

Registre decisões que afetem o modelo, a API, as dependências ou a evolução.

## Modelo de registro

### Título

- Data: 02/10
- Problema observado: Programação Orientada à Intuição
- Alternativas consideradas: Criação de diagrama de classes ou estrutura básica das classes
- Decisão: Criação de diagrama de classes
- Consequências: Organização da estrutura do projeto
- Teste ou evidência que verifica a decisão: As classes tornaram-se responsáveis pelos seus próprios atributos e o código ficou melhor organizado

- Data: 03/10
- Problema observado: Erros de compilação em código dos colaboradores 
- Alternativas consideradas: Criação de branch específica para cada colaborador
- Decisão: Criação de branch
- Consequências: Organização da estrutura do projeto, trabalho em execução na branch do colaborador e trabalho concluído e testado na main
- Teste ou evidência que verifica a decisão: Os 06erros diminuíram e os códigos voltaram a compilar normalmente, dividindo o trabalho de cada integrante e aumentando a produtividade

- Data: 06/10
- Problema observado: Necessidade de representar valores monetários (dinheiro) com precisão absoluta e sem erros de arredondamento
- Alternativas consideradas: BigDecimal vs. int (valor expresso em centavos)
- Decisão: Adoção de int representando a menor unidade monetária (centavos, ex: R$ 10,00 = 1000 centavos)
- Consequências: Eliminação de imprecisões (comuns em float/double), simplificação das operações de débito e crédito (+ e -), e código mais enxuto mantendo total precisão financeira para operações do escopo atual
- Teste ou evidência que verifica a decisão: Testes unitários em dominio.PessoaTest e dominio.ReservaTest validando os débitos e saldos com exatidão

- Data: 16/10
- Problema observado: Risco de inconsistência e erros de digitação ao tratar marcas de carro como String livre (ex: "Fiat", "fiat", "FIAT "), além da necessidade de validar manualmente quais marcas são aceitas pelo sistema
- Alternativas consideradas: Uso de String com métodos de sanitização/validação ou criação de uma classe de domínio específica para marcas.
- Decisão: Utilização do Enum dominio.MarcaCarro para centralizar as marcas de veículos suportadas.
- Consequências: Alta facilidade de implementação, eliminação de erros por digitação e extrema facilidade para adicionar novas marcas futuramente (bastando declarar uma nova constante no Enum).
- Teste ou evidência que verifica a decisão: Simplificação na instanciação da entidade dominio.Carro e filtragem direta por igualdade de referência (==) no método de consulta buscarReservaPorMarcaCarro validado no servico.ConsultaReservaServicoTest, sem a necessidade de comparações ou tratamentos de String.

- Data: 25/10
- Problema observado: Rigidez e alto acoplamento na classe dominio.Reserva ao tratar regras de cancelamento e estorno financeiro, dificultando as implementações caso novas políticas fossem criadas.
- Alternativas consideradas: Manter condicionais if/else dentro do método cancelar() na classe dominio.Reserva, criar subclasses de dominio.Reserva para cada tipo de cancelamento ou desacoplar a regra de cálculo.
- Decisão: Implementar a interface politica.PoliticaCancelamento e as implementações concretas politica.EstornoTotal e politica.EstornoComMulta.
- Consequências: Desacoplamento completo da classe dominio.Reserva, alta flexibilidade para adicionar novas regras de estorno no futuro sem alterar código existente e elimina o uso de instanceof ou checagens por equals.
- Teste ou evidência que verifica a decisão: Testes unitários em dominio.ReservaTest e servico.ConsultaReservaServicoTest validando os cenários de estorno integral (100%) e parcial com multa (80%) em casos específicos.


- Data: 31/10
- Problema observado: Necessidade de armazenar e consultar entidades em memória de forma genérica e realizar buscas avançadas.
- Alternativas consideradas: Criar repositórios em memória separados para cada entidade do sistema, utilizar funções extratoras de ID (Function<T, ID>) no construtor ou implementar um contrato genérico de identificação
- Decisão: Implementar a interface dominio.Identificavel<ID> nas entidades e criar a classe genérica repositorio.RepositorioMemoria<T, ID>, combinada a uma classe de serviço (servico.ConsultaReservaServico) dedicada a consultas
- Consequências: Estrutura de repositório reutilizável para qualquer entidade (dominio.Reserva, dominio.Carro, dominio.Pessoa) e desacoplamento total entre o armazenamento em memória e as regras de consulta/agregacao
- Teste ou evidência que verifica a decisão: Passagem com sucesso de $100\%$ dos testes unitários em repositorio.RepositorioMemoriaTest e servico.ConsultaReservaServicoTest, cobrindo as operações de CRUD em memória e as consultas


- Data: 07/11

- Problema observado: Ao restaurar instâncias de dominio.Reserva via Jackson a partir do JSON, o construtor padrão executava novamente o débito financeiro na conta da dominio.Pessoa, duplicando a cobrança ou lançando exceção por saldo insuficiente, além da necessidade de revalidar as regras de negócio ao carregar o ficheiro.
- Alternativas consideradas: Remover o débito financeiro de todos os construtores de domínio ou criar um método de fábrica estático com anotação @JsonCreator dedicado exclusivamente à restauração.
- Decisão: Criar o método de fábrica estático dominio.Reserva.restaurar(...) anotado com @JsonCreator para desserialização do Jackson sem execução de débitos, mantendo a chamada de débito nos construtores públicos de criação de novas reservas.
- Consequências: Revalidação estrita das invariantes de domínio na desserialização do JSON (Invariante 8) sem duplicar cobranças no saldo do cliente nem alterar o comportamento da aplicação.
- Teste ou evidência que verifica a decisão: Testes unitários e de integração em repositorio.PersistenciaTest (deveSalvarERestaurarReservaValidaComSucesso e deveRejeitarJsonComInvarianteViolada) e dominio.ReservaTest executados com 100% de aprovação.


- Data: 07/11
- Problema observado: O Jackson mapeava automaticamente métodos getters públicos derivados, como getCustoTotal(), serializando o campo "custoTotal" no JSON e provocando exceções de propriedades não reconhecidas (com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException) na restauração.
- Alternativas consideradas: Criar um atributo físico custoTotal com getter/setter, ignorar propriedades desconhecidas globalmente no ObjectMapper (FAIL_ON_UNKNOWN_PROPERTIES = false) ou utilizar anotações explícitas de ignoramento.
- Decisão: Adicionar a anotação @JsonIgnore no método getter calculado getCustoTotal() na classe dominio.Reserva.
- Consequências: JSON gerado mais limpo e sem campos redundantes, garantindo que o custo total continue sendo calculado dinamicamente com base no Período e na diária do Carro.
- Teste ou evidência que verifica a decisão: Passagem sem falhas nos testes de integração de persistência em repositorio.PersistenciaTest ao gravar e restaurar listas de reservas em arquivo temporário gerido pelo @TempDir.


- Data: 07/11
- Problema observado: Necessidade de gerar relatórios sintéticos e agregações estatísticas por status das reservas.
- Alternativas consideradas: Laços imperativos tradicionais com mapas manuais ou utilização da API de Java Streams.
- Decisão: Implementar os métodos de agregação e contagem no servico.ConsultaReservaServico (contarReservasPorStatus e agruparTotalReservasPorStatus) utilizando Collectors.groupingBy e Collectors.counting da API de Java Streams.
- Consequências: Código declarativo, expressivo, legível e alinhado aos padrões modernos do Java 25 para geração de relatórios sobre o repositório.
- Teste ou evidência que verifica a decisão: Testes unitários agrupaTotalReservasPorStatusComSucesso e contaReservasPorStatusComSucesso aprovados na suíte de testes de servico.ConsultaReservaServicoTest.