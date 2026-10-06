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
- Teste ou evidência que verifica a decisão: Os erros diminuíram e os códigos voltaram a compilar normalmente, dividindo o trabalho de cada integrante e aumentando a produtividade

- Data: 06/10
- Problema observado: Necessidade de representar valores monetários (dinheiro) com precisão absoluta e sem erros de arredondamento
- Alternativas consideradas: `BigDecimal` vs. `int` (valor expresso em centavos)
- Decisão: Adoção de `int` representando a menor unidade monetária (centavos, ex: R$ 10,00 = 1000 centavos)
- Consequências: Eliminação de imprecisões (comuns em `float`/`double`), simplificação das operações de débito e crédito (`+` e `-`), e código mais enxuto mantendo total precisão financeira para operações do escopo atual
- Teste ou evidência que verifica a decisão: Testes unitários em `PessoaTest` e `ReservaTest` validando os débitos e saldos com exatidão