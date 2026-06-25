# 🏴‍☠️ Tutorial libGDX Passo a Passo: Entendendo o Básico do Zero

Se você nunca programou um jogo na vida, esqueça um pouco o Java tradicional. Fazer jogos funciona de um jeito um pouquinho diferente, mas vamos usar analogias simples para você entender cada peça do quebra-cabeça.

---

## 1. O que é um "Loop" de Jogo? (As classes `Game` e `Screen`)

Em programas normais (como de banco ou sites), o computador fica parado esperando você clicar em algo para ele calcular e responder.
Em um jogo, **não**. O jogo é como uma **animação de flipbook** (aqueles bloquinhos de papel que você desenha um boneco em cada página e folheia rápido com o dedão para parecer que ele está se mexendo).

- O computador "folheia" a tela dezenas de vezes por segundo (normalmente 60 vezes).
- Cada página desse flipbook é chamada de **Frame**.
- É por isso que em jogos você não faz um `while(true) { ... }` para o jogo continuar rodando. A própria biblioteca de jogos já faz isso por você! Ela chama um método específico chamado `render(float delta)` **a cada frame** (60 vezes por segundo). 

No libGDX:
* A classe **`Game`** (no nosso caso, a `JogoPiratas`) é o controle geral. Ela decide qual "tela" estamos vendo (Menu, Jogo, Game Over).
* A classe **`Screen`** (como a nossa `TelaInicial` ou uma futura `TelaBatalha`) é a tela em si.

```java
public class MinhaTelaDeJogo implements Screen {
    
    // O método render roda a ~60 FPS (Frames por segundo)
    @Override
    public void render(float delta) {
        // PASSO 1: Atualizar a lógica (Ex: O pirata andou um passo pra direita?)
        atualizarPosicaoDoPirata();
        
        // PASSO 2: Limpar a página antiga. Pintamos tudo de preto para apagar o desenho anterior.
        ScreenUtils.clear(0, 0, 0, 1);
        
        // PASSO 3: Desenhar o pirata na posição nova na página em branco!
        desenharNovoFrame();
    }
}
```

---

## 2. O que é esse `float delta`?

Imagine que você está fazendo o jogo no seu PC da faculdade, que é um pouco lento, e ele consegue folhear o flipbook a **10 páginas (frames) por segundo**. 
Mas o seu amigo tem um PC Gamer que folheia a **60 páginas (frames) por segundo**.

Se no seu código você disser: `"A cada página que passar, ande 5 passos."`
- No seu PC: O pirata andaria 50 passos em 1 segundo.
- No PC do seu amigo: O pirata andaria 300 passos em 1 segundo! O jogo ficaria super rápido e impossível de jogar.

Para corrigir isso, o libGDX entrega na sua mão a variável **`delta`** (ou Delta Time). O `delta` é um cronômetro de altíssima precisão. Ele te diz exatamente: **"Quantos segundos (ou milissegundos) demorou entre a última página folheada e a página atual?"** (Geralmente é um número minúsculo como `0.016`).

**A Regra Mágica:** Você multiplica a velocidade do personagem por esse tempo.
`posicaoX = posicaoX + (VelocidadePixelsPorSegundo * delta)`
Assim, no PC rápido ou no lento, o pirata vai percorrer a mesma distância real de espaço na tela do monitor.

---

## 3. O que é uma `Texture`? (Como as imagens funcionam)

Uma **Texture** é simplesmente um arquivo de imagem (um arquivo `.png` ou `.jpg` de um pirata) que foi lido do seu disco rígido (HD/SSD) e enviado para a **Placa de Vídeo** guardar na memória.

> [!WARNING]
> **REGRA DE OURO DAS TEXTURAS**
> Ler arquivos do HD é a coisa mais lenta que um computador faz. Como seu método `render()` é chamado 60 vezes por segundo, você **nunca, em hipótese alguma**, deve colocar o código `new Texture("pirata.png")` dentro do `render()`. Se fizer isso, o jogo vai tentar ler o HD 60 vezes por segundo e vai travar a sua máquina.
>
> Você deve sempre carregar a `Texture` UMA VEZ SÓ (quando a tela abrir) e ficar reutilizando ela no `render()`.
> E sempre, sempre guarde suas imagens dentro da pasta `assets/`.

---

## 4. O que é um `SpriteBatch`?

A palavra "Batch" significa **Lote** (fazer as coisas de baciada). "Sprite" é qualquer **imagem 2D** usada em um jogo.

Imagine que a tela do computador é uma parede de tijolos gigante e a placa de vídeo é o Pintor. Você tem 100 figurinhas de piratas para desenhar nessa parede.
- Se você disser: *"Pintor, desenhe o pirata 1. Pintor, agora desenhe o 2. Pintor, agora o 3..."*, o pintor vai ficar louco parando o que está fazendo toda hora para te ouvir. O jogo fica devagar.
- O **`SpriteBatch`** é a ferramenta que resolve isso. Em vez de interromper o pintor toda hora, você diz: *"Pintor, prepare os seus pincéis, vou te passar um lote inteiro! (`batch.begin()`)"*. Aí você joga as 100 figurinhas na mão dele. E no final você diz: *"Terminei! Pode pintar todas elas na parede de uma vez só! (`batch.end()`)"*.

É por isso que quase todo código de desenho no libGDX tem um `.begin()` e `.end()`.

Veja como fica tudo junto:

```java
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TelaBatalha implements Screen {
    private SpriteBatch batch; // A Ferramenta do Pintor
    private Texture imagemPirata; // A Imagem carregada do HD e guardada na memória
    private float posicaoX = 100; // Posição horizontal onde o pirata vai ficar

    public TelaBatalha() {
        // Isso roda UMA VEZ quando a tela é criada.
        batch = new SpriteBatch();
        imagemPirata = new Texture("pirata.png"); 
    }

    @Override
    public void render(float delta) { // Isso roda 60 VEZES POR SEGUNDO
        // 1. Limpa a tela inteira (pinta tudo de preto para apagar os rastros do frame anterior)
        ScreenUtils.clear(0, 0, 0, 1);

        // 2. Avisa o pintor que o lote de imagens vai começar!
        batch.begin();
        
        // 3. Manda o pintor preparar a imagemPirata na coordenada X e Y
        // Detalhe: No libGDX, a posição (0,0) é no CANTO INFERIOR ESQUERDO da tela!
        batch.draw(imagemPirata, posicaoX, 200);
        
        // 4. Manda o pintor pintar tudo na tela de fato!
        batch.end(); 
    }
    
    @Override
    public void dispose() {
        // "dispose" significa descartar. Como texturas ocupam memória RAM,
        // quando você for fechar a tela (sair da batalha pro menu, por exemplo),
        // você TEM que destruir elas, senão o jogo dá erro de "Falta de Memória".
        batch.dispose();
        imagemPirata.dispose();
    }
}
```

---

## 5. Input: Fazendo o teclado funcionar

Como lemos o que o jogador está apertando? Não usamos "Listeners" complicados de Java normal para o teclado. O libGDX é focado em performance, então ele nos deixa perguntar DIRETAMENTE a cada frame: "Ei computador, essa tecla tá abaixada neste exato momento?". Usamos a classe `Gdx.input`.

```java
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public void render(float delta) {
    // ---- LÓGICA DE TECLADO ----
    
    // isKeyPressed: Pergunta se a seta DIREITA está pressionada agora.
    if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
        // Move o pirata. 200 é a velocidade em pixels por segundo.
        // Multiplicamos pelo bendito delta para a velocidade ser consistente!
        posicaoX = posicaoX + (200 * delta); 
    }
    
    // isKeyJustPressed: É diferente! Pergunta se a tecla foi pressionada EXATAMENTE NESTE FRAME.
    // Bom para "Atacar" (você não quer que segurar o espaço faça ele atacar 60 vezes por segundo)
    if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
        System.out.println("O pirata deu uma espadada!");
    }
    
    // ---- FIM DA LÓGICA ----

    // ... depois daqui você limpa a tela e chama o batch.begin() e batch.draw() ...
}
```

---

## 6. Interfaces de Usuário (A magia do Scene2D)

Fazer RPG significa ter MUITOS menus e botões (Botão de Atacar, Botão de Curar, Mochila).
Se você fosse fazer isso na mão, teria que desenhar caixas de imagem com o `SpriteBatch` e ficar calculando com matemática se a coordenada do clique do mouse (X e Y) caiu dentro das coordenadas do botão. É um inferno.

Para te salvar disso, o libGDX criou o **Scene2D**. É uma biblioteca extra que já vem embutida e que faz isso automaticamente.
O Scene2D imagina a sua UI como um teatro:
- Existe o **`Stage`** (O Palco)
- Você joga **`Actors`** (Atores) dentro dele. Um Ator pode ser um `TextButton` (Botão de Texto), um `Label` (Texto solto), uma `ProgressBar` (Barra de vida).

Você só cria o Palco, cria o Botão, e diz ao jogo: *"Ei, se alguém clicar, o Palco quem resolve quem foi clicado, beleza?"*.

```java
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TelaBatalha implements Screen {
    private Stage stage; // O Palco do Teatro

    public TelaBatalha() {
        stage = new Stage();
        // AVISO IMPORTANTE: O libGDX precisa saber quem manda nos cliques do mouse.
        // Isso aqui diz para o libGDX mandar todos os cliques pro Palco analisar.
        Gdx.input.setInputProcessor(stage); 

        // Uma Skin é simplesmente um arquivo que diz a cor do botão, qual a fonte, etc.
        // (Você precisa baixar um arquivo de skin pronto na internet e jogar na pasta assets)
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Cria o botão
        TextButton botaoAtaque = new TextButton("Atacar!", skin);
        botaoAtaque.setPosition(100, 100);
        botaoAtaque.setSize(150, 50);

        // O que acontece quando o cara clica com o mouse no botão?
        botaoAtaque.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("BUM! Ataque realizado!");
            }
        });

        // Adiciona o botão no palco para que ele exista
        stage.addActor(botaoAtaque);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Limpa tela
        
        // stage.act() roda as lógicas dos botões (ex: animação de apertar)
        stage.act(delta); 
        
        // stage.draw() pega os pincéis internamente e pinta todos os botões da tela
        stage.draw();     
    }
}
```
