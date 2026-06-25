# Divisão de Tarefas - Jogo Piratas (RPG)

Para garantir que todos trabalhem ativamente em todas as fases e ninguém fique "esperando" o código do outro, a divisão de tarefas foi feita de forma **horizontal (por etapas de desenvolvimento)**. Dessa forma, todos participam da fundação, da lógica e da integração final.

Abaixo está a proposta dividida em 3 fases, com tarefas equilibradas para os 4 membros em cada etapa.

---

## Fase 1: Esqueleto e Estruturas Básicas
**Objetivo:** Criar as classes, atributos e assinaturas de métodos (mesmo que vazios) para que o código de todo mundo recompile sem erros de dependência.

*   **Membro 1:** Cria as classes `Personagem` (Abstrata), `Aliado` e `Inimigo`. (Define os atributos de vida, defesa, etc., e os getters/setters).
*   **Membro 2:** Cria as classes `Habilidade`, `TipoHabilidade` (Enum) e `Recompensa`.
*   **Membro 3:** Cria a Interface `NoMapa` e a classe base `Mapa` (apenas a estrutura de dados que vai guardar os nós).
*   **Membro 4:** Cria as classes `Tripulacao`, `Loja` e o esqueleto inicial do `GameManager`.

> **Fim da Fase 1:** O projeto tem todas as classes criadas, mas elas ainda não "fazem" nada. Ninguém fica travado por falta de arquivo.

---

## Fase 2: Lógica Interna e Regras de Negócio
**Objetivo:** Preencher os métodos vazios da Fase 1 com a lógica real de programação (cálculos, IFs, manipulação de listas).

*   **Membro 1:** Cria e implementa a `FilaDeTurnos` (Lógica para receber uma lista de personagens, ordenar por iniciativa e ciclar os turnos).
*   **Membro 2:** Implementa a lógica profunda de `executarAcao` na classe `Habilidade` (calcular o dano/cura/defesa e aplicar nos atributos de um `Personagem` alvo).
*   **Membro 3:** Implementa as classes filhas de Mapa: `NoBatalha`, `NoEvento` e `NoDescanso`. (Programa o que acontece internamente quando o método `entrarNo()` é chamado).
*   **Membro 4:** Implementa a lógica da economia: métodos `gastarDinheiro` e `receberRecompensa` na `Tripulacao`, e a lógica de `comprarMelhoria` na `Loja`.

> **Fim da Fase 2:** Cada sistema funciona de forma independente. O dano é calculado certo, a fila ordena certo, a loja cobra o valor certo.

---

## Fase 3: Integração e O "Loop Principal" do Jogo
**Objetivo:** Conectar todas as partes soltas para que o jogo possa ser jogado do início ao fim. É a fase de mais colaboração.

*   **Membros 1 e 2 (Dupla de Combate):** Implementam juntos o `GerenciadorDeBatalha`. 
    *   *Membro 1* foca no `loop` de executar os turnos, chamando a Fila e as habilidades.
    *   *Membro 2* foca na instanciação (`iniciarCombate`, gerando inimigos aleatórios) e condições de fim (`verificarVitoriaOuDerrota`, dando a recompensa).
*   **Membro 3 (Navegação):** Finaliza o `Mapa`, focando na lógica procedural de `gerarProximosNos()` e de `avancarParaNo()`, garantindo que o jogador avance nas etapas.
*   **Membro 4 (Maestro):** Preenche o `GameManager`. Pega o mapa gerado pelo Membro 3, joga a Tripulação lá dentro e faz o `loopPrincipal()` chamar o `GerenciadorDeBatalha` ou a `Loja` dependendo de qual nó o jogador pisar.

> **Fim da Fase 3:** O jogo roda. A equipe toda passa a trabalhar junta procurando e corrigindo *Bugs* e balanceando os valores (Ex: inimigo muito forte, itens muito caros).

---

### Por que esse modelo é bom?
*   **Evita gargalos:** Ninguém espera o outro terminar para poder trabalhar, pois na Fase 1 todos definem como as classes vão se comunicar.
*   **Conhecimento compartilhado:** Todo mundo mexe em um pedacinho da estrutura base e todo mundo ajuda na integração final. 
*   **Justiça:** Ninguém fica com a "parte chata" inteira. Todos tem momentos de criar estrutural (Fase 1), lógica de algoritmos (Fase 2) e conectar as coisas (Fase 3).
