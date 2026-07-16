# Jogo Piratas - Handoff e Diretrizes do Projeto

Este documento contém todo o contexto, as decisões de design e as preferências técnicas adotadas no desenvolvimento do **Jogo Piratas** (usando a framework Java LibGDX). Utilize-o em novas sessões para que a IA compreenda imediatamente o estado do projeto e o seu gosto pessoal para a UI/UX e desenvolvimento.

## 1. Stack Tecnológica e Comandos
- **Linguagem:** Java
- **Framework:** LibGDX (Scene2D para interface)
- **Compilação e Teste:** Utilize o comando `./gradlew desktop:run` na raiz do projeto para rodar a aplicação.

## 2. Preferências Visuais e Estéticas (UI/UX)
Você gosta de interfaces de alta qualidade, legíveis, limpas e que deem a sensação de um jogo "premium" de verdade.
- **Resolução:** O jogo roda em `1920x1080` (Full HD). 
- **Redimensionamento:** Utilizamos `FitViewport` (ex: `new FitViewport(1920, 1080)`) na `Stage` para garantir que a tela escale perfeitamente e mantenha a proporção de 16:9, sem esticar os assets (adicionando barras pretas automaticamente se o formato da janela não bater).
- **Tipografia:** 
  - A fonte original da engine é filtrada (`TextureFilter.Linear`) para não ficar pixelada. 
  - Usamos escala de `1.4f` na classe base (`JogoPiratas.java`).
  - É essencial aplicar espaçamento horizontal artificial entre as letras editando o `xadvance` dos Glifos da fonte (`glyph.xadvance += 2`), trazendo elegância à tipografia.
- **Botões (Scene2D):** 
  - Usamos `TextButton`.
  - Tamanho ideal e testado para os botões do painel de batalha: `300x50` com padding de `10`. Isso evita texto cortado e gera respiro.
- **Barra de Vida (HP):** Não usamos apenas números. O HP é renderizado usando uma `ProgressBar` verde (`ProgressBarStyle`), ao lado do texto de HP Atual/Máximo e com uma animação (`setAnimateDuration(0.2f)`) sempre que o personagem toma dano.

## 3. Disposição do Layout em Batalha
- **Estrutura:** Dividido logicamente em `leftColumn` (Aliados) e `rightColumn` (Inimigos) que ficam inseridos na `battleArea`. 
- **Centralização:** Em vez de jogar as colunas para os extremos da tela, usamos `.expand().center()` para manter os times perto do centro da tela.
- **Espelhamento (Sprites):** Como sprites geralmente vêm olhando para a esquerda, forçamos os aliados a encararem os inimigos. Convertendo a textura para `TextureRegion` e usando `region.flip(true, false)` apenas para a equipe Aliada.
- **Componente Scene2D Recomendado:** Sempre colocar o `Image` do personagem dentro de um `Group`, e não num `Container`. O `Group` preserva as coordenadas x/y livres para usarmos as `Actions` (animações), sem que o sistema de Layout tente sobrepor nossas animações durante um `update`.

## 4. Animações e "Juice" de Combate (Actions)
As batalhas devem ter um peso visual, não apenas texto trocando na tela.
- **Animação de Ataque:** Quando um personagem age, disparamos ações paralelas para simular um solavanco:
  - **Avanço Direcional:** Move-se 60 pixels na direção do oponente (aliados movem `moveX = 60`, inimigos `moveX = -60`).
  - **Zoom:** Escala simultânea de `1.3f`.
  - Após 0.1s, retorna à escala e posição originais perfeitamente sincronizado (`Actions.sequence`).
- **Animação de Dano:** Quando um alvo toma golpe (após um `Actions.delay(0.2f)` do impacto do inimigo chegar), a imagem ganha um tom Vermelho e pisca intenso duas vezes (`Actions.color(Color.RED, 0.1f)` seguido de `Color.WHITE`).

## 5. Arquitetura e Organização
- **Gerenciador de Batalha:** Controla o loop e as checagens (turno do jogador, ações da máquina, validação de vivos/mortos). 
- **Tela de Vitória:** Evitamos apenas alterar labels de estado. A vitória limpa a interface (`uiTable.clear()`) e renderiza uma sobreposição épica ("VITÓRIA!") em amarelo, chamando `getXpGanho()` e `getDinheiroGanho()` diretamente do `GerenciadorDeBatalha`, além de um botão claro para retornar ao menu.

## Lembrete para as futuras IAs
Sempre que for implementar uma nova tela, habilidade ou efeito:
1. Pense em resolução `1920x1080` de base (FitViewport).
2. Lembre-se de utilizar espaçamento nos textos.
3. Não crie componentes visuais travados por Layout se precisar aplicar animações (Scene2D `Actions`).
4. Priorize "Juice" visual — tudo que acontece deve ter um mínimo de feedback (botões responsivos, barras mudando suaves, sprites piscando).
