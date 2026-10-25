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
- Objetivo: Entender e implementar a interface necessária para o checkpoint 2
- Parte do projeto afetada: Interface PoliticaCancelamento e classes que implementam(EstornoComMulta e EstornoTotal)
- Resultado aproveitado: Entendimento das responsabilidades da classe e interface
- Verificações executadas: O código implementado foi verificado pelos integrantes, garantindo que a interface foi implementada da maneira correta
- Correções ou decisões da equipe: Decidiu-se desacoplar a lógica de cancelamento da classe `Reserva`, utilizando a interface `PoliticaCancelamento` com assinatura única (`calcularEstorno`) e duas 
- implementações separadas (`CancelamentoEstornoTotal` e `CancelamentoComMulta`). Ajustamos os parâmetros utilizados pela interface para facilitar os testes JUnit.
