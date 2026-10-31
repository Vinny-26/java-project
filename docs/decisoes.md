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
- Teste ou evidência que verifica a decisão: Testes unitários em PessoaTest e ReservaTest validando os débitos e saldos com exatidão

- Data: 16/10
- Problema observado: Risco de inconsistência e erros de digitação ao tratar marcas de carro como String livre (ex: "Fiat", "fiat", "FIAT "), além da necessidade de validar manualmente quais marcas são aceitas pelo sistema
- Alternativas consideradas: Uso de String com métodos de sanitização/validação ou criação de uma classe de domínio específica para marcas.
- Decisão: Utilização do Enum MarcaCarro para centralizar as marcas de veículos suportadas.
- Consequências: Alta facilidade de implementação, eliminação de erros por digitação e extrema facilidade para adicionar novas marcas futuramente (bastando declarar uma nova constante no Enum).
- Teste ou evidência que verifica a decisão: Simplificação na instanciação da entidade Carro e filtragem direta por igualdade de referência (==) no método de consulta buscarReservaPorMarcaCarro validado no ConsultaReservaServicoTest, sem a necessidade de comparações ou tratamentos de String.

- Data: 25/10
- Problema observado: Rigidez e alto acoplamento na classe Reserva ao tratar regras de cancelamento e estorno financeiro, dificultando as implementações caso novas políticas fossem criadas.
- Alternativas consideradas: Manter condicionais if/else dentro do método cancelar() na classe Reserva, criar subclasses de Reserva para cada tipo de cancelamento ou desacoplar a regra de cálculo.
- Decisão: Implementar a interface PoliticaCancelamento e as implementações concretas EstornoTotal e EstornoComMulta.
- Consequências: Desacoplamento completo da classe Reserva, alta flexibilidade para adicionar novas regras de estorno no futuro sem alterar código existente e elimina o uso de instanceof ou checagens por equals.
- Teste ou evidência que verifica a decisão: Testes unitários em ReservaTest e ConsultaReservaServicoTest validando os cenários de estorno integral (100%) e parcial com multa (80%) em casos específicos.


- Data: 31/10
- Problema observado: Necessidade de armazenar e consultar entidades em memória de forma genérica e realizar buscas avançadas.
- Alternativas consideradas: Criar repositórios em memória separados para cada entidade do sistema, utilizar funções extratoras de ID (Function<T, ID>) no construtor ou implementar um contrato genérico de identificação
- Decisão: Implementar a interface Identificavel<ID> nas entidades e criar a classe genérica RepositorioMemoria<T, ID>, combinada a uma classe de serviço (ConsultaReservaServico) dedicada a consultas
- Consequências: Estrutura de repositório reutilizável para qualquer entidade (Reserva, Carro, Pessoa) e desacoplamento total entre o armazenamento em memória e as regras de consulta/agregacao
- Teste ou evidência que verifica a decisão: Passagem com sucesso de $100\%$ dos testes unitários em RepositorioMemoriaTest e ConsultaReservaServicoTest, cobrindo as operações de CRUD em memória e as consultas