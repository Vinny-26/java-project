# Registro de uso de inteligência artificial

Registre usos materiais de IA que tenham influenciado código, testes,
documentação ou decisões.

## Modelo de registro

### Uso

- Data: todo o ciclo do projeto
- Ferramenta: Gemini
- Objetivo: Corrigir bugs, aumentar a cobertura dos testes e revisar o código implementado pelos integrantes
- Parte do projeto afetada: UML e arquivos de teste
- Resultado aproveitado: Durante a implementação das classes iniciais, pudemos verificar alguns métodos faltantes e aumentar a cobertura dos testes
- Verificações executadas: Todos os testes foram implementados pelos integrantes, utilizando a IA apenas para verificar partes do código que não foram testadas corretamente ou estavam com falso positivo
- Correções ou decisões da equipe: Decidimos utilizar a IA apenas na verificação do código e cobertura dos testes.

- Data: 25/10/2026
- Ferramenta: Gemini
- Objetivo: Entender e implementar a interface necessária para a Politica de Cancelamento
- Parte do projeto afetada: Interface politica.PoliticaCancelamento e classes que implementam(politica.EstornoComMulta e politica.EstornoTotal)
- Resultado aproveitado: Entendimento das responsabilidades da classe e interface
- Verificações executadas: O código implementado foi verificado pelos integrantes, garantindo que a interface foi implementada da maneira correta
- Correções ou decisões da equipe: 
    - Decidiu-se desacoplar a lógica de cancelamento da classe dominio.Reserva, utilizando a interface politica.PoliticaCancelamento com assinatura única (calcularEstorno) e duas implementações separadas (politica.EstornoTotal e politica.EstornoComMulta).
    - Ajustamos os parâmetros utilizados pela interface para facilitar os testes JUnit.

- Data: 31/10/2026
- Ferramenta: Gemini
- Objetivo: Implementar o repositório genérico em memória e a camada de consultas
- Parte do projeto afetada: Interface dominio.Identificavel, interface repositorio.Repositorio<T, ID>, classe repositorio.RepositorioMemoria<T, ID>, serviço servico.ConsultaReservaServico e suítes de testes repositorio.RepositorioMemoriaTest e servico.ConsultaReservaServicoTest
- Resultado aproveitado: Estruturação da arquitetura de repositórios genéricos e construção de consultas agregadas via Stream
- Verificações executadas: Execução da suíte completa de testes unitários, cobrindo operações de CRUD em memória, isolamento de escopo por entidade e regras das consultas
- Correções ou decisões da equipe:
    - Optou-se por utilizar o contrato dominio.Identificavel<ID> na classe dominio.Reserva para eliminar a necessidade de passar funções extratoras no construtor.
    - As operações de agregação financeira no serviço de consulta utilizaram reduce com BigDecimal.ZERO para garantir precisão e evitar perdas decimais.