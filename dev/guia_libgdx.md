# Guia de libGDX para o Jogo-Piratas 🏴‍☠️

## O que é libGDX?

**libGDX** é um framework Java para desenvolvimento de jogos 2D/3D. Ele fornece:
- Renderização de gráficos (sprites, animações, texto)
- Gerenciamento de input (teclado, mouse, touch)
- Áudio
- Sistema de UI (Scene2D)
- Funciona em Desktop, Android, iOS e Web

Para o seu projeto de RPG por turnos com piratas, libGDX é ideal porque:
- É **Java puro** — combina com seu projeto de POO
- Tem **Scene2D** — perfeito para interfaces de RPG (menus, botões, barras de vida)
- É **leve** — não exige engine pesada para um jogo por turnos

---

## 1. Conceitos Fundamentais

### 1.1 Ciclo de Vida — A Classe `Game`

Tudo em libGDX gira em torno de um **ciclo de vida**. A classe principal do seu jogo estende `Game`:

```java
import com.badlogic.gdx.Game;

public class JogoPiratas extends Game {

    @Override
    public void create() {
        // Chamado UMA vez quando o jogo inicia
        // Aqui você carrega recursos e define a primeira tela
        setScreen(new TelaMenu(this));
    }

    @Override
    public void render() {
        // Chamado a cada frame (~60 fps)
        // Game.render() delega para a Screen ativa
        super.render();
    }

    @Override
    public void dispose() {
        // Chamado quando o jogo é fechado
        // Libere recursos aqui (texturas, sons, etc.)
    }
}
```

> [!IMPORTANT]
> **Analogia com seu projeto:** Seu `GameManager.java` atual é o `main`. Com libGDX, o `GameManager` se torna essa classe `Game`, e o `main` fica num Launcher separado.

### 1.2 Screens (Telas)

Cada "tela" do jogo (menu, mapa, batalha) é uma classe que implementa `Screen`:

```java
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;

public class TelaBatalha implements Screen {

    private JogoPiratas jogo;

    public TelaBatalha(JogoPiratas jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        // Chamado quando esta tela se torna ativa
    }

    @Override
    public void render(float delta) {
        // Chamado a cada frame. "delta" = tempo desde o último frame
        // 1) Limpar a tela
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1); // azul escuro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 2) Desenhar coisas aqui
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
```

> [!TIP]
> **Mapeamento para seu projeto:**
> | Conceito atual | Equivalente libGDX |
> |---|---|
> | `GameManager` (fluxo do jogo) | `Game` + troca de `Screen` |
> | Loop do menu (console) | `TelaMenu` (Screen) |
> | `GerenciadorDeBatalha` | `TelaBatalha` (Screen) |
> | Navegação no `Mapa` | `TelaMapa` (Screen) |
> | `Loja` | `TelaLoja` (Screen) |

### 1.3 SpriteBatch e Textures (Desenhar coisas)

Para desenhar imagens na tela:

```java
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TelaBatalha implements Screen {

    private SpriteBatch batch;
    private Texture fundoBatalha;
    private Texture spritePirata;

    @Override
    public void show() {
        batch = new SpriteBatch();
        fundoBatalha = new Texture("fundo_batalha.png");
        spritePirata = new Texture("pirata.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fundoBatalha, 0, 0);         // fundo
        batch.draw(spritePirata, 100, 200);      // personagem na posição (100, 200)
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        fundoBatalha.dispose();
        spritePirata.dispose();
    }
}
```

> [!NOTE]
> - Imagens ficam em `assets/` (pasta que vamos criar)
> - Sempre chame `dispose()` em Textures e SpriteBatch para evitar memory leaks
> - Coordenada (0,0) é o **canto inferior esquerdo** (diferente de muitos frameworks)

### 1.4 Scene2D — UI para RPGs

Scene2D é o sistema de UI do libGDX. Perfeito para RPGs por turnos:

```java
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class TelaBatalha implements Screen {

    private Stage stage;
    private Skin skin;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);  // Stage recebe inputs

        // Skin = conjunto de estilos visuais (botões, labels, etc.)
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Barra de vida
        ProgressBar barraVida = new ProgressBar(0, 100, 1, false, skin);
        barraVida.setValue(75); // 75% de vida
        barraVida.setPosition(50, 500);
        barraVida.setSize(200, 30);
        stage.addActor(barraVida);

        // Botões de habilidade
        TextButton btnAtaque = new TextButton("Ataque", skin);
        btnAtaque.setPosition(100, 50);
        btnAtaque.setSize(150, 50);
        btnAtaque.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Chamar lógica de ataque do seu GerenciadorDeBatalha
                System.out.println("Atacou!");
            }
        });
        stage.addActor(btnAtaque);

        TextButton btnDefesa = new TextButton("Defesa", skin);
        btnDefesa.setPosition(270, 50);
        btnDefesa.setSize(150, 50);
        stage.addActor(btnDefesa);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);  // Atualiza animações e lógica da UI
        stage.draw();      // Desenha todos os atores
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
```

> [!TIP]
> Scene2D usa o padrão **Observer** — `addListener()` é como registrar um callback. Isso é POO pura!

### 1.5 BitmapFont — Texto na tela

```java
import com.badlogic.gdx.graphics.g2d.BitmapFont;

BitmapFont fonte = new BitmapFont(); // fonte padrão
fonte.setColor(Color.WHITE);

batch.begin();
fonte.draw(batch, "Capitão Barba Negra - HP: 85/100", 50, 550);
fonte.draw(batch, "Turno: Aliado", 50, 520);
batch.end();
```

---

## 2. Como Integrar libGDX ao Seu Projeto

### 2.1 Opção A: Usar o Setup Tool (Recomendado)

A forma oficial de criar um projeto libGDX é com o **gdx-liftoff** (substituto do setup antigo):

1. Acesse: https://libgdx.com/wiki/start/project-generation
2. Baixe o **gdx-liftoff**
3. Configure:
   - **Project Name:** jogo-piratas
   - **Package:** sistema
   - **Platforms:** Desktop (LWJGL3)
   - **Extensions:** nenhuma por enquanto
4. Gere o projeto e copie suas classes existentes para dentro

> [!WARNING]
> O gdx-liftoff gera uma estrutura multi-módulo (core, desktop, android...). Seu projeto atual é single-module. A migração vai exigir reorganizar os arquivos.

### 2.2 Opção B: Adicionar libGDX ao Gradle Existente (Mais Simples para Começar)

Se quiser manter sua estrutura atual e apenas adicionar libGDX como dependência:

```gradle
// build.gradle (modificado)
plugins {
    id 'java'
    id 'application'
}

group = 'jogo-piratas'
version = '1.0.0'

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

// Versão do libGDX
ext {
    gdxVersion = '1.12.1'
}

dependencies {
    // libGDX Core
    implementation "com.badlogicgames.gdx:gdx:$gdxVersion"

    // libGDX Desktop Backend (LWJGL3)
    implementation "com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion"

    // libGDX Platform Natives (Windows, Mac, Linux)
    implementation "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop"

    // JUnit para testes
    testImplementation 'junit:junit:4.13.2'
}

tasks.named('test') {
    useJUnit()
}

application {
    mainClass = 'sistema.DesktopLauncher'
}

// Configurar assets
sourceSets.main.resources.srcDirs += ['assets']
```

Depois crie o launcher:

```java
// src/main/java/sistema/DesktopLauncher.java
package sistema;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Jogo Piratas");
        config.setWindowedMode(1280, 720);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new JogoPiratas(), config);
    }
}
```

E a classe principal do jogo:

```java
// src/main/java/sistema/JogoPiratas.java
package sistema;

import com.badlogic.gdx.Game;

public class JogoPiratas extends Game {
    @Override
    public void create() {
        // Por enquanto, só mostrar uma tela simples
        setScreen(new TelaMenu(this));
    }
}
```

> [!IMPORTANT]
> Crie a pasta `assets/` na raiz do projeto (`jogo-piratas/assets/`). É lá que ficam imagens, sons e arquivos de UI.

---

## 3. Roadmap de Aprendizado 🗺️

Siga esta ordem para aprender libGDX construindo seu jogo:

### Fase 1: "Olá Mundo" Gráfico ⬜
- [ ] Configurar libGDX no projeto (Opção A ou B acima)
- [ ] Criar `DesktopLauncher` + `JogoPiratas extends Game`
- [ ] Criar uma `Screen` simples que limpa a tela com uma cor
- [ ] Desenhar texto na tela com `BitmapFont`
- [ ] **Meta:** Janela abre com "Jogo Piratas" escrito

### Fase 2: Tela de Menu ⬜
- [ ] Aprender Scene2D (`Stage`, `Skin`, `TextButton`)
- [ ] Criar `TelaMenu` com botões: "Novo Jogo", "Sair"
- [ ] Fazer os botões funcionarem (trocar de Screen / fechar)
- [ ] **Meta:** Menu funcional com botões clicáveis

### Fase 3: Tela de Batalha (o coração do RPG) ⬜
- [ ] Criar `TelaBatalha` usando sua lógica existente (`GerenciadorDeBatalha`, `FilaDeTurnos`)
- [ ] Mostrar sprites dos personagens (aliados e inimigos)
- [ ] Barras de vida com `ProgressBar`
- [ ] Botões de habilidades (Ataque, Defesa, Cura) que chamam `Habilidade.usar()`
- [ ] Indicador de turno (quem está agindo)
- [ ] **Meta:** Batalha jogável com interface gráfica

### Fase 4: Tela de Mapa ⬜
- [ ] Visualizar o `Mapa` graficamente (nós conectados)
- [ ] Clicar em nós para navegar (`NoBatalha`, `NoEvento`, `NoDescanso`)
- [ ] Transições entre telas (mapa → batalha → mapa)
- [ ] **Meta:** Navegação pelo mapa funcional

### Fase 5: Polimento ⬜
- [ ] Animações (ataque, dano, cura)
- [ ] Efeitos sonoros
- [ ] Tela de loja (`Loja.java` integrada)
- [ ] Tela de game over / vitória

---

## 4. Conceitos de POO no libGDX

Como este é um projeto de Programação Orientada a Objetos, vale destacar os padrões:

| Padrão POO | Onde aparece no libGDX | Seu projeto |
|---|---|---|
| **Herança** | `Game` → sua classe `JogoPiratas` | `Personagem` → `Aliado`/`Inimigo` |
| **Interface** | `Screen` (implementada por cada tela) | `NoMapa` (implementada por `NoBatalha`, etc.) |
| **Polimorfismo** | `setScreen(screen)` aceita qualquer `Screen` | `NoMapa` diferentes no `Mapa` |
| **Composição** | `Stage` contém `Actor`s | `Personagem` contém `Habilidade`s |
| **Observer** | `addListener()` nos botões | Eventos de batalha |
| **State** | Troca de `Screen`s | Estados do jogo (menu, batalha, mapa) |

---

## 5. Recursos para Aprender Mais

### Documentação Oficial
- **Wiki:** https://libgdx.com/wiki/
- **API Javadoc:** https://javadoc.io/doc/com.badlogicgames.gdx/gdx

### Tutoriais Recomendados
- **libGDX Wiki - Getting Started:** Passo a passo oficial
- **Scene2D UI:** https://libgdx.com/wiki/graphics/2d/scene2d/scene2d-ui — essencial para seu RPG
- **Texture Packing:** https://libgdx.com/wiki/tools/texture-packer — otimizar sprites

### Para o Skin (UI)
- Baixe um skin pronto em: https://github.com/czyzby/gdx-skins
- O skin padrão do libGDX já serve para prototipar

---

## 6. Dica de Arquitetura para Seu Projeto

A grande vantagem do seu código atual é que a **lógica do jogo está separada da apresentação**. Mantenha isso!

```
┌─────────────────────────────────────────────┐
│              APRESENTAÇÃO (libGDX)           │
│  TelaMenu  │  TelaBatalha  │  TelaMapa      │
│  (Screen)  │   (Screen)    │  (Screen)      │
├─────────────────────────────────────────────┤
│              LÓGICA DO JOGO (seu código)     │
│ Personagem │ GerenciadorDeBatalha │ Mapa     │
│ Habilidade │ FilaDeTurnos         │ NoMapa   │
│ Aliado     │ Tripulacao           │ Loja     │
│ Inimigo    │                      │Recompensa│
└─────────────────────────────────────────────┘
```

As `Screen`s do libGDX devem **chamar** seus métodos existentes, não substituí-los. Exemplo:

```java
// Na TelaBatalha, quando o jogador clica "Atacar":
btnAtaque.addListener(new ClickListener() {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        // Chama a lógica que você JÁ escreveu:
        Habilidade hab = aliado.getHabilidades().get(0);
        gerenciadorDeBatalha.executarAcao(aliado, inimigo, hab);

        // Atualiza a UI:
        barraVidaInimigo.setValue(inimigo.getVida());
    }
});
```

> [!CAUTION]
> Não misture lógica de jogo com código de renderização. Suas classes em `entidades/` e `progressao/` devem continuar **sem nenhuma dependência do libGDX**. Só o pacote `sistema/` (as Screens) deve importar libGDX.
