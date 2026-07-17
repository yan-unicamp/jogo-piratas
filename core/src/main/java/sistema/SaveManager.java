package sistema;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import entidades.Aliado;
import entidades.Item;
import factories.ItemFactory;
import factories.PersonagemFactory;
import progressao.Ilha;
import progressao.IlhaDescanso;
import progressao.IlhaEnum;
import progressao.IlhaLoja;
import progressao.IlhasGenericasEnum;
import factories.IlhaFactory;

import java.util.ArrayList;

public class SaveManager {
    private static final String SAVE_FILE = "save.json";

    public static void salvar(GameManager gm) {
        // Nao salva se ainda estiver na primeira ilha do jogo (Morgan Mao de Machado) e nao concluiu nada
        if (gm.getMapa().getEtapaAtual() == 0 && gm.getMapa().getCapitulo() == 1 && gm.getMapa().getIlhasConcluidas().isEmpty()) {
            System.out.println("Save ignorado: o jogador ainda nao venceu a primeira ilha.");
            return;
        }
        
        try {
            SaveState state = new SaveState();
            state.capitulo = gm.getMapa().getCapitulo();
            state.etapaAtual = gm.getMapa().getEtapaAtual();
            state.dinheiro = gm.getTripulacao().getDinheiro();

            // Salvar Aliados
            for (Aliado aliado : gm.getTripulacao().getAliados()) {
                SaveState.AliadoSave as = new SaveState.AliadoSave();
                as.nome = aliado.getNome();
                as.nivel = aliado.getNivel();
                as.experiencia = aliado.getExperiencia();
                as.vidaAtual = aliado.getVidaAtual();
                as.ativo = gm.getTripulacao().getAliadosAtivos().contains(aliado);
                state.aliados.add(as);
            }

            // Salvar Itens (pelo nome)
            for (Item item : gm.getTripulacao().getItens()) {
                state.itens.add(item.getNome());
            }

            // Salvar Mapa
            for (Ilha ilha : gm.getMapa().getOpcoesAtuais()) {
                state.opcoesAtuais.add(ilha.getIdSave());
            }

            for (IlhasGenericasEnum ig : gm.getMapa().getIlhasMostradas()) {
                state.ilhasMostradas.add(ig.name());
            }
            
            for (Ilha ilha : gm.getMapa().getIlhasConcluidas()) {
                state.ilhasConcluidas.add(ilha.getIdSave());
            }

            Json json = new Json();
            String jsonStr = json.toJson(state);
            FileHandle file = Gdx.files.local(SAVE_FILE);
            file.writeString(jsonStr, false);
            System.out.println("Jogo salvo com sucesso em " + SAVE_FILE);
        } catch (Exception e) {
            System.err.println("Erro ao salvar o jogo: " + e.getMessage());
        }
    }

    public static boolean temSave() {
        return Gdx.files.local(SAVE_FILE).exists();
    }

    public static boolean temSaveAvancado() {
        if (!temSave()) return false;
        try {
            FileHandle file = Gdx.files.local(SAVE_FILE);
            String jsonStr = file.readString();
            Json json = new Json();
            SaveState state = json.fromJson(SaveState.class, jsonStr);
            return state.etapaAtual > 0 || state.capitulo > 1 || !state.ilhasConcluidas.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public static void deletarSave() {
        FileHandle file = Gdx.files.local(SAVE_FILE);
        if (file.exists()) {
            file.delete();
            System.out.println("Save deletado para Novo Jogo.");
        }
    }

    public static void carregar(GameManager gm) {
        if (!temSave()) return;

        try {
            FileHandle file = Gdx.files.local(SAVE_FILE);
            String jsonStr = file.readString();
            Json json = new Json();
            SaveState state = json.fromJson(SaveState.class, jsonStr);

            // Restaurar Tripulacao
            gm.getTripulacao().getAliados().clear();
            gm.getTripulacao().getAliadosAtivos().clear();
            gm.getTripulacao().getItens().clear();
            
            // É preciso usar um método que gaste o dinheiro atual ou apenas resetar e dar dinheiro.
            // A Tripulacao não tem setDinheiro, então:
            int dinheiroAtual = gm.getTripulacao().getDinheiro();
            gm.getTripulacao().gastarDinheiro(dinheiroAtual); // zera
            gm.getTripulacao().receberDinheiro(state.dinheiro);

            for (SaveState.AliadoSave as : state.aliados) {
                Aliado aliado = recriarAliado(as.nome);
                if (aliado != null) {
                    // Simular subidas de nível
                    int levelsToGain = as.nivel - 1;
                    for (int i = 0; i < levelsToGain; i++) {
                        aliado.subirStatus();
                        // Destravar habilidades do nivel simulado
                        if (aliado.temHabilidadeDesbloqueavelNoNivel(i + 2)) {
                            // Não precisamos chamar o método de ganhar xp completo,
                            // podemos apenas injetar as habilidades se fosse necessário.
                            // Mas ganharExperiencia com valor exato de subida resolve:
                        }
                    }
                    // A melhor forma de forçar o nível/xp:
                    // Mas ganharExperiencia sobe status, o que afeta vida maxima e defesa
                    // Vamos apenas restaurar a vida.
                    // O nivel no Aliado já está 1 ao criar, vamos apenas forcar o XP e ver as habilidades?
                    // PersonagemFactory.criarLuffy() já retorna lvl 1. 
                    // Se chamarmos ganharExperiencia até as.experiencia ele sobe sozinho!
                    int xpRestante = as.experiencia;
                    if (xpRestante > 0) {
                        aliado.ganharExperiencia(xpRestante);
                    }
                    
                    // Ajusta a vida
                    aliado.receberDano(aliado.getVidaMaxima() - as.vidaAtual);
                    if (as.vidaAtual <= 0) {
                        aliado.receberDano(999999);
                    }

                    gm.getTripulacao().adicionarAliado(aliado);
                    if (as.ativo) {
                        gm.getTripulacao().adicionarAliadoAtivo(aliado);
                    }
                }
            }

            for (String itemName : state.itens) {
                Item item = recriarItem(itemName);
                if (item != null) gm.getTripulacao().receberItem(item);
            }

            // Restaurar Mapa
            gm.getMapa().restaurarEstado(state.capitulo, state.etapaAtual, state.opcoesAtuais, state.ilhasMostradas, state.ilhasConcluidas);
            
            System.out.println("Jogo carregado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao carregar o jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Aliado recriarAliado(String nome) {
        switch (nome) {
            case "Luffy": return PersonagemFactory.criarLuffy();
            case "Zoro": return PersonagemFactory.criarZoro();
            case "Nami": return PersonagemFactory.criarNami();
            case "Sanji": return PersonagemFactory.criarSanji();
            case "Nico Robin": return PersonagemFactory.criarRobin();
            case "Usopp": return PersonagemFactory.criarUsopp();
            case "Chopper": return PersonagemFactory.criarChopper();
            case "Brook": return PersonagemFactory.criarBrook();
            case "Franky": return PersonagemFactory.criarFranky();
            case "Jinbe": return PersonagemFactory.criarJinbe();
            case "Vivi": return PersonagemFactory.criarVivi();
            case "Carrot": return PersonagemFactory.criarCarrot();
            case "Yamato": return PersonagemFactory.criarYamato();
            case "Law": return PersonagemFactory.criarLaw();
            case "Rebecca": return PersonagemFactory.criarRebecca();
            case "Vegapunk": return PersonagemFactory.criarVegapunk();
            case "Momonosuke": return PersonagemFactory.criarMomonosuke();
            case "Bon Clay": return PersonagemFactory.criarBonClay();
            case "Koby": return PersonagemFactory.criarKoby();
            case "Carue": return PersonagemFactory.criarCarue();
            case "Ace": return PersonagemFactory.criarAce();
            case "Loki": return PersonagemFactory.criarLoki();
            default: return null;
        }
    }

    private static Item recriarItem(String nome) {
        if (nome.equals("Bandagem Simples")) return ItemFactory.criarBandagemSimples();
        if (nome.equals("Carne com Osso")) return ItemFactory.criarCarneComOsso();
        if (nome.equals("Kit Medico do Chopper")) return ItemFactory.criarKitMedico();
        return null;
    }

    public static Ilha recriarIlhaPeloIdSave(String idSave) {
        if (idSave == null) return null;
        if (idSave.equals("LOJA")) return new IlhaLoja();
        if (idSave.equals("DESCANSO")) return new IlhaDescanso();
        if (idSave.startsWith("ENUM:")) {
            String enumName = idSave.replace("ENUM:", "");
            try {
                return IlhaFactory.criar(IlhaEnum.valueOf(enumName));
            } catch (Exception e) {}
        }
        if (idSave.startsWith("GENERIC:")) {
            String enumName = idSave.replace("GENERIC:", "");
            try {
                return IlhaFactory.criarGenerica(IlhasGenericasEnum.valueOf(enumName));
            } catch (Exception e) {}
        }
        return null;
    }
}
