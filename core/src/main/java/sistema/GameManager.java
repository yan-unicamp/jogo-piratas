package sistema;

import progressao.Mapa;
import progressao.NoMapa;
import progressao.NoBatalha;
import progressao.NoBatalhaCanonica;
import progressao.NoBatalhaFinal;
import progressao.NoEvento;
import entidades.Personagem;
import entidades.Inimigo;
import factories.PersonagemFactory;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Gerenciador principal do jogo.
 * Contém a lógica de fluxo do jogo (estados, transições).
 */
public class GameManager {
    private Mapa mapa;
    private Tripulacao tripulacao;
    private GerenciadorDeBatalha gerenciadorDeBatalha;
    private boolean jogoRodando;
    private Scanner scanner;
    private List<String> bossesCap1;
    private List<String> bossesCap2;
    private List<String> bossesCap3;
    private Random random;

    public GameManager() {
        this.mapa = new Mapa();
        this.tripulacao = new Tripulacao();
        
        entidades.Aliado luffy = factories.PersonagemFactory.criarLuffy();
        this.tripulacao.adicionarAliado(luffy);
        this.tripulacao.adicionarAliadoAtivo(luffy);

        this.gerenciadorDeBatalha = new GerenciadorDeBatalha();
        this.jogoRodando = false;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.bossesCap1 = new ArrayList<>(Arrays.asList("kuro", "donKrieg", "arlong"));
        this.bossesCap2 = new ArrayList<>(Arrays.asList("wapol", "crocodile", "robLucci", "moria"));
        this.bossesCap3 = new ArrayList<>(Arrays.asList("doflamingo", "bigMom", "kaido"));
        System.out.println("GameManager inicializado.");
    }

    public void iniciarJogo() { 
        System.out.println("Iniciando o jogo...");
        this.jogoRodando = true;
        
        // Exemplo: adicionando um aliado base
        tripulacao.adicionarAliado(PersonagemFactory.criarLuffy());
        tripulacao.adicionarAliadoAtivo(tripulacao.getAliados().get(0));

        loopPrincipal();
    }

    private Inimigo sortearBossCanonico(int capitulo) {
        String bossName = "";
        int nivel = mapa.getEtapaAtual(); 
        if (capitulo == 1 && !bossesCap1.isEmpty()) {
            int idx = random.nextInt(bossesCap1.size());
            bossName = bossesCap1.remove(idx);
        } else if (capitulo == 2 && !bossesCap2.isEmpty()) {
            int idx = random.nextInt(bossesCap2.size());
            bossName = bossesCap2.remove(idx);
        } else if (capitulo == 3 && !bossesCap3.isEmpty()) {
            int idx = random.nextInt(bossesCap3.size());
            bossName = bossesCap3.remove(idx);
        }

        switch (bossName) {
            case "kuro": return PersonagemFactory.criarKuro(nivel);
            case "donKrieg": return PersonagemFactory.criarDonKrieg(nivel);
            case "arlong": return PersonagemFactory.criarArlong(nivel);
            case "wapol": return PersonagemFactory.criarWapol(nivel);
            case "crocodile": return PersonagemFactory.criarCrocodile(nivel);
            case "robLucci": return PersonagemFactory.criarRobLucci(nivel);
            case "moria": return PersonagemFactory.criarMoria(nivel);
            case "doflamingo": return PersonagemFactory.criarDoflamingo(nivel);
            case "bigMom": return PersonagemFactory.criarBigMom(nivel);
            case "kaido": return PersonagemFactory.criarKaido(nivel);
            default: return PersonagemFactory.criarChefe(nivel); 
        }
    }

    private Inimigo obterBossFinal(int capitulo) {
        int nivel = 10;
        if (capitulo == 1) return PersonagemFactory.criarSmoker(nivel);
        if (capitulo == 2) return PersonagemFactory.criarAkainu(nivel);
        return PersonagemFactory.criarImu(nivel);
    }

    public void loopPrincipal() { 
        while (jogoRodando) {
            if (mapa.getCapitulo() > 3) {
                acharOnePiece();
                break;
            }

            int etapaAtual = mapa.getEtapaAtual();
            int capituloAtual = mapa.getCapitulo();
            
            System.out.println("\n--- MAPA: Capítulo " + capituloAtual + " - Etapa " + etapaAtual + " ---");
            
            NoMapa proximoNo = null;

            if (etapaAtual == 0) {
                System.out.println("Atenção! O Capitão Morgan Mão de Machado bloqueia o seu caminho!");
                Inimigo chefe = PersonagemFactory.criarMorgan(capituloAtual).comAliado(PersonagemFactory.criarZoro());
                proximoNo = new NoBatalhaCanonica(chefe);
                
                System.out.println("Pressione ENTER para prosseguir.");
                try { System.in.read(); } catch (Exception e) {} 
            } 
            else if (etapaAtual == 3 || etapaAtual == 6) {
                System.out.println("Atenção! Uma batalha formidável se aproxima...");
                Inimigo chefe = sortearBossCanonico(capituloAtual);
                proximoNo = new NoBatalhaCanonica(chefe);
                
                System.out.println("Pressione ENTER para prosseguir.");
                try { System.in.read(); } catch (Exception e) {} 
            } 
            else if (etapaAtual == 10) {
                System.out.println("O CHEFE DO CAPITULO ESTA AQUI!");
                Inimigo chefeFinal = obterBossFinal(capituloAtual);
                proximoNo = new NoBatalhaFinal(chefeFinal);
                
                System.out.println("Pressione ENTER para prosseguir.");
                try { System.in.read(); } catch (Exception e) {} 
            } 
            else {
                List<String> opcoesDesc = Arrays.asList("Batalha Genérica", "Loja", "Descanso");
                List<Inimigo> inimigosBasicos = new ArrayList<>();
                inimigosBasicos.add(PersonagemFactory.criarMarinheiro(etapaAtual));
                List<NoMapa> opcoesObj = Arrays.asList(new NoBatalha(inimigosBasicos), new NoBatalha(inimigosBasicos), new NoBatalha(inimigosBasicos));
                
                List<Integer> indices = Arrays.asList(0, 1, 2);
                Collections.shuffle(indices);
                
                System.out.println("Para qual Nó você quer ir?");
                for (int i = 0; i < 3; i++) {
                    System.out.println((i + 1) + ". " + opcoesDesc.get(indices.get(i)));
                }
                System.out.println("0. Sair do Jogo");
                System.out.print("Escolha: ");
                
                int escolha = -1;
                if (scanner.hasNextInt()) {
                    escolha = scanner.nextInt();
                } else {
                    scanner.next(); 
                }
                
                if (escolha == 0) {
                    this.jogoRodando = false;
                    break;
                }
                
                if (escolha >= 1 && escolha <= 3) {
                    proximoNo = opcoesObj.get(indices.get(escolha - 1));
                } else {
                    System.out.println("Opção inválida!");
                    continue;
                }
            }
            
            mapa.avancarParaNo(proximoNo);
            
            if (proximoNo instanceof NoBatalha) {
                System.out.println("\n[!] ENTRANDO EM COMBATE [!]");
                ArrayList<Personagem> aliados = new ArrayList<>(tripulacao.getAliadosAtivos());
                ArrayList<Personagem> inimigos = new ArrayList<>();
                inimigos.add(PersonagemFactory.criarMarinheiro(mapa.getEtapaAtual()));
                
                gerenciadorDeBatalha.iniciarCombate(aliados, inimigos, tripulacao);
            } else if (proximoNo instanceof NoBatalhaCanonica) {
                System.out.println("\n[!] ENTRANDO EM COMBATE CANÔNICO [!]");
                ArrayList<Personagem> aliados = new ArrayList<>(tripulacao.getAliadosAtivos());
                ArrayList<Personagem> inimigos = new ArrayList<>(((NoBatalhaCanonica) proximoNo).getInimigos());
                
                gerenciadorDeBatalha.iniciarCombate(aliados, inimigos, tripulacao);
            } else if (proximoNo instanceof NoBatalhaFinal) {
                System.out.println("\n[!] ENTRANDO NA BATALHA DO BOSS [!]");
                ArrayList<Personagem> aliados = new ArrayList<>(tripulacao.getAliadosAtivos());
                ArrayList<Personagem> inimigos = new ArrayList<>(((NoBatalhaFinal) proximoNo).getInimigos());
                
                gerenciadorDeBatalha.iniciarCombate(aliados, inimigos, tripulacao);
            } else {
                proximoNo.entrarNo(this);
            }
        }
        encerrarJogo();
    }

    public void encerrarJogo() { 
        System.out.println("Jogo encerrado. Obrigado por jogar!");
        this.jogoRodando = false;
        if (scanner != null) scanner.close();
    }

    private void acharOnePiece() {
        System.out.println("\n=============================================");
        System.out.println("PARABÉNS! VOCÊ ACHOU O ONE PIECE!");
        System.out.println("Você concluiu sua jornada e se tornou o Rei dos Piratas!");
        System.out.println("=============================================\n");
        this.jogoRodando = false;
    }

    public Tripulacao getTripulacao() { return tripulacao; }
    public Mapa getMapa() { return mapa; }

    public void prepararBatalha(List<Inimigo> inimigos) {
        System.out.println("\n[!] PREPARANDO BATALHA [!]");
        ArrayList<Personagem> aliados = new ArrayList<>(tripulacao.getAliadosAtivos());
        ArrayList<Personagem> inimigosCast = new ArrayList<>(inimigos);
        gerenciadorDeBatalha.iniciarCombate(aliados, inimigosCast, tripulacao);
    }

    public void mudarEstado(EstadoJogo novoEstado) {
        System.out.println("Transição de estado: " + novoEstado);
        // Em um sistema puramente terminal, apenas registramos o estado.
        // Quando a GUI voltar, aqui ficaria a troca de Telas.
    }

    public void setTelaAtual(com.badlogic.gdx.Screen screen) {
        // Método de compatibilidade se necessário
    }

    public boolean gastarOuro(int valor) {
        return tripulacao.gastarDinheiro(valor);
    }

    public int getOuro() {
        return tripulacao.getDinheiro();
    }

    public void entrarIlha(progressao.Ilha ilha) {
        progressao.Rodada rodada = ilha.getRodadaAtual();
        if (rodada != null) {
            System.out.println("Entrando na ilha " + ilha.getNome() + " - " + rodada.getDescricao());
            prepararBatalha(rodada.getInimigos());
            mudarEstado(EstadoJogo.BATALHA);
        } else {
            System.out.println("A ilha " + ilha.getNome() + " já está concluída!");
        }
    }
}
