# TODO: Antes de Continuar

Para avançar com a geração dinâmica de batalhas sem sobrecarregar a `TelaBatalha`, precisamos das seguintes fundações:

- [ ] **Criar a entidade `Ilha` / `Fase`**: Estrutura que guardará o caminho de fundo (`caminhoFundo`), o nome do local (`tituloArco`) e as listas ou pools de possíveis inimigos.
- [ ] **Criar a estrutura do `Mapa` / `Nó`**: Definir se o jogo usará uma árvore de nós (estilo Slay the Spire) ou estágios lineares, permitindo selecionar o próximo combate.
- [ ] **Criar a Factory de Inimigos/Ilhas**: Uma classe para gerar instâncias de batalhas aleatórias dependendo do nível ou do tipo do nó de combate (Ex: `BatalhaFactory.gerarCombate(Ilha atual, int dificuldade)`).
- [ ] **Tornar a `Tripulacao` Global**: Garantir que a lista de aliados selecionados (e seus HPs/Status) persistam após a batalha, e que não precisemos instanciar o Luffy, Zoro e Chopper no `JogoPiratas.create()` toda vez.
