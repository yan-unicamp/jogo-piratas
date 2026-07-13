# Divisão de Tarefas - Jogo Piratas (Lógica 100% primeiro, Visual no final)

Como combinado, a ideia principal agora é desenvolver o "miolo" do jogo (regras, atributos, combate e navegação) de forma completamente independente de interface gráfica nas fases iniciais. Dessa forma, a equipe garante que a matemática e o fluxo do jogo funcionam (podendo ser testados via console/prints).

A parte visual (LibGDX, Telas e UI) ficará concentrada em uma grande **Fase 5 (Mutirão Visual)**, onde todos colocarão a mão na massa juntos.

---

## Fase 1: Esqueleto e Estruturas Básicas (Apenas Backend)
**Objetivo:** Criar as classes vazias e atributos básicos do diagrama UML para que o código de todo mundo recompile sem erros.

*   **Membro 1 (Core de Entidades):** Pacote `entidades`. Criar `Personagem`, `Aliado`, `Inimigo`, `Habilidade` e `TipoHabilidade`. Focar em declarar os atributos (vida, defesa, poder) e construtores/getters/setters.
*   **Membro 2 (Controle de Batalha):** Criar as classes `GerenciadorDeBatalha` e `FilaDeTurnos` (apenas com as assinaturas de métodos como `iniciarCombate`, `executarTurno`).
*   **Membro 3 (Core de Progressão):** Criar a interface `NoMapa` e a classe base `Mapa` (com foco em como os nós vão se ligar na memória).
*   **Membro 4 (Economia e Gerenciamento):** Criar `Recompensa`, `Tripulacao`, `Loja` e o esqueleto base do `GameManager`.

> **Fim da Fase 1:** O projeto tem todas as classes estruturais criadas, mas elas ainda não "fazem" nada. Ninguém fica travado.

---

## Fase 2: Lógica Interna e Regras de Negócio
**Objetivo:** Preencher os métodos com a matemática do RPG. Tudo aqui ainda é testado com "Prints" no terminal.

*   **Membro 1 (Matemática de Combate):** Implementar a lógica real em `Personagem` (`receberDano`, `curar`) e na `Habilidade` (calcular e aplicar os efeitos nos alvos).
*   **Membro 2 (Sistemas de Turno):** Implementar a ordenação por iniciativa na `FilaDeTurnos` e a estrutura de laços de repetição dentro do `GerenciadorDeBatalha`.
*   **Membro 3 (Nós do Mapa):** Criar as classes concretas `NoBatalha`, `NoDescanso`, `NoEvento`. Escrever o algoritmo que gera o mapa aleatório dentro da classe `Mapa`.
*   **Membro 4 (Economia Viva):** Preencher a lógica interna da `Loja`, além dos métodos cruciais de `gastarDinheiro` e `receberRecompensa` dentro da `Tripulacao`.

> **Fim da Fase 2:** Cada sistema funciona de forma isolada perfeitamente no terminal.

---

## Fase 3: Integração do Backend (Jogo rodando via Terminal)
**Objetivo:** Juntar Batalha, Mapa e Economia. O jogo precisa ser completamente "jogável", imprimindo os textos no console.

*   **Membros 1 e 2 (Batalha Completa):** Integram as Entidades com o Gerenciador de Batalha, garantindo que inimigos ataquem, morram e retornem recompensas ao final de um confronto.
*   **Membros 3 e 4 (Viagem e Progresso):** O Membro 4 usa o `GameManager` para criar o Loop Principal: Pega o Mapa gerado pelo Membro 3, joga a Tripulação lá dentro e faz o console perguntar "Para qual Nó você quer ir?". Se for `NoBatalha`, chama o Gerenciador feito pelos membros 1 e 2.

> **Fim da Fase 3:** O jogo inteiro já existe, funciona e não tem bugs de lógica. É um jogo 100% de Texto (Terminal).

---

## Fase 4: O "Setup" do LibGDX (Arquitetura)
**Objetivo:** Preparar o terreno para receber a parte gráfica. Não é desenhar as telas ainda, é só configurar a "máquina gráfica".

*   Todos se juntam para entender o LibGDX.
*   A equipe cria em conjunto a classe principal `JogoPiratas` (que estende `Game`), configura o `Assets` (para carregar imagens e fontes na memória) e altera o `GameManager` para ele parar de usar o loop do console e passar a obedecer o loop de `render()` do LibGDX.

---

## Fase 5: Mutirão Visual e Frontend (Todo Mundo Junto)
**Objetivo:** Desenhar o jogo. Como o backend já está 100% sólido (testado na fase 3), todos os membros pegam pedaços da UI e ligam aos dados reais sem medo de quebrar a lógica do RPG.

*   **Equipe de Batalha (Membros 1 e 2):** Criam e estilizam a `TelaBatalha`. Como já conhecem profundamente o `GerenciadorDeBatalha`, eles apenas pegam a Vida e Turnos reais que estão acontecendo em background e conectam nas Barras de Vida e Textos do LibGDX (`Scene2D`).
*   **Equipe de Navegação (Membro 3):** Cria a `TelaMapa`. Desenha bolinhas ou ícones que representam os `Nós` na tela, e quando o jogador clica nela, invoca aquele mesmo `avancarParaNo()` testado na fase 3.
*   **Equipe de Menus (Membro 4):** Cria a `TelaMenu`, `TelaLoja` e a interface da `Tripulacao` (exibir ouro na tela, lista de aliados, aplicar botões estéticos do Scene2D).

> **Fim da Fase 5:** As mecânicas prontas ganham o revestimento visual. O jogo está completo, animado e pronto para a entrega!
