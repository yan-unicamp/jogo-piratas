# jogo-piratas

Projeto de programação orientada a objetos (MC322) - Implementação de um sistema RPG em Java com Gradle.

## Estrutura do Projeto

```
jogo-piratas/
├── src/
│   ├── main/java/
│   │   ├── entidades/
│   │   │   ├── TipoHabilidade.java    (Enum)
│   │   │   ├── Habilidade.java
│   │   │   ├── Personagem.java        (Abstrata)
│   │   │   ├── Aliado.java
│   │   │   └── Inimigo.java
│   │   ├── progressao/
│   │   │   ├── Recompensa.java
│   │   │   ├── Loja.java
│   │   │   ├── Mapa.java
│   │   │   ├── NoMapa.java            (Interface)
│   │   │   ├── NoBatalha.java
│   │   │   ├── NoEvento.java
│   │   │   └── NoDescanso.java
│   │   └── sistema/
│   │       ├── Tripulacao.java
│   │       ├── FilaDeTurnos.java
│   │       ├── GerenciadorDeBatalha.java
│   │       └── GameManager.java       (main)
│   └── test/java/
│       ├── entidades/
│       ├── progressao/
│       └── sistema/
├── uml/
│   ├── diagrama-rpg.mmd               (Mermaid)
│   └── diagrama-rpg.puml              (PlantUML)
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
└── README.md
```

## Configuração

### Pré-requisitos
- **Java 11+** instalado
- **Git** (para clonar o repositório)

### Primeiro uso

1. Clone o repositório:
```bash
git clone https://github.com/yan-unicamp/jogo-piratas.git
cd jogo-piratas
```

2. Dê permissão de execução ao gradlew (macOS/Linux):
```bash
chmod +x gradlew
```

3. Valide a instalação do Gradle:
```bash
./gradlew --version
```

## Comandos Gradle

| Comando | Descrição |
|---------|-----------|
| `./gradlew build` | Compila o projeto e gera `.jar` em `build/` |
| `./gradlew run` | Executa o programa (main em `GameManager`) |
| `./gradlew test` | Executa os testes unitários |
| `./gradlew clean` | Remove arquivos compilados |
| `./gradlew tasks` | Lista todas as tasks disponíveis |
| `./gradlew javadoc` | Gera documentação JavaDoc em `build/docs/` |

## Desenvolvimento

### Compilar e rodar
```bash
./gradlew run
```

### Desenvolver com IDE

**VS Code:**
1. Instale a extensão "Extension Pack for Java"
2. Abra a pasta do projeto
3. A IDE detecta automaticamente o Gradle

**IntelliJ IDEA:**
1. File → Open → Selecione a pasta `jogo-piratas`
2. Deixe a IDE configurar automaticamente

**Eclipse:**
1. File → Import → Gradle → Existing Gradle Project
2. Selecione a pasta raiz do projeto

### Adicionar dependências

Edite `build.gradle` na seção `dependencies`:
```gradle
dependencies {
    implementation 'org.exemplo:biblioteca:1.0.0'
    testImplementation 'junit:junit:4.13.2'
}
```

Depois execute: `./gradlew refresh` (na IDE) ou `./gradlew build`

## Arquitetura

O projeto segue a organização descrita no UML:

### 3 Pacotes principais (em `src/main/java/`):

1. **`entidades/`** — Personagens e ações
   - `Personagem.java` (abstrata): base para Aliado e Inimigo
   - `Habilidade.java`: ações com tipos (DANO, DEFESA, CURA)
   - `TipoHabilidade.java`: enum que ditam o comportamento
   - `Aliado.java` e `Inimigo.java`: implementações

2. **`progressao/`** — Navegação no mapa
   - `Mapa.java` e `NoMapa.java` (interface): polimorfismo para diferentes nós
   - `NoBatalha.java`, `NoEvento.java`, `NoDescanso.java`: implementações específicas
   - `Loja.java` e `Recompensa.java`: sistema de progresso

3. **`sistema/`** — Controle e fluxo geral
   - `GameManager.java`: maestro principal (contém `main`)
   - `GerenciadorDeBatalha.java`: orquestra combates
   - `FilaDeTurnos.java`: gerencia ordem de ação
   - `Tripulacao.java`: inventário e estado do jogador

## UML

Veja o diagrama de classes em:
- **Mermaid:** `uml/diagrama-rpg.mmd` (abra em VS Code ou GitHub)
- **PlantUML:** `uml/diagrama-rpg.puml` (use online em PlantUML)

Relações:
- **Composição (`*--`)**: Personagem possui Habilidades, GameManager orquestra subsistemas
- **Herança (`--|>`)**: Aliado e Inimigo herdam de Personagem
- **Interface (`<|..`)**: NoBatalha/NoEvento/NoDescanso implementam NoMapa
- **Agregação (`o--`)**: FilaDeTurnos referencia Personagens sem ownership
- **Dependência (`..>`)**: Nós dependem de GerenciadorDeBatalha, Loja, etc.

## Testes

Execute os testes com:
```bash
./gradlew test
```

Adicione testes em `src/test/java/com/unicamp/mc322/jogo/`.

## Construir distribution

Para gerar um `.jar` executável:
```bash
./gradlew build
java -jar build/libs/jogo-piratas-1.0.0.jar
```

## Troubleshooting

| Problema | Solução |
|----------|---------|
| `./gradlew: command not found` | Execute `chmod +x gradlew` |
| `java: command not found` | Instale Java 11+ e configure `JAVA_HOME` |
| Porta já em uso | Modifique a porta no `GameManager` |
| Erro de compilação | Execute `./gradlew clean build` |

## Contribuições

1. Crie uma branch: `git checkout -b feature/sua-feature`
2. Commit as mudanças: `git commit -m "Adiciona sua feature"`
3. Push: `git push origin feature/sua-feature`
4. Abra um Pull Request

## Licença

Projeto acadêmico da Unicamp (MC322).
