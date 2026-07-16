package factories;

import java.util.Arrays;
import java.util.List;

import entidades.Habilidade;
import entidades.Inimigo;
import entidades.TipoHabilidade;
import progressao.Ilha;
import progressao.IlhaEnum;
import progressao.Recompensa;
import progressao.Rodada;

public class IlhaFactory {

    public static Ilha criar(IlhaEnum ilhaEnum) {
        switch (ilhaEnum) {
            case ORANGE_TOWN:
                return criarOrangeTown();
            case VILA_SYRUP:
                return criarVilaSyrup();
            case BARATIE:
                return criarBaratie();
            case ARLONG_PARK:
                return criarArlongPark();
            case LOGUETOWN:
                return criarLoguetown();
            case LITTLE_GARDEN:
                return criarLittleGarden();
            case ILHA_DRUM:
                return criarIlhaDrum();
            case ALABASTA:
                return criarAlabasta();
            case JAYA:
                return criarJaya();
            case SKYPIEA:
                return criarSkypiea();
            case ENIES_LOBBY:
                return criarEniesLobby();
            case THRILLER_BARK:
                return criarThrillerBark();
            case SABAODY:
                return criarSabaody();
            case IMPEL_DOWN:
                return criarImpelDown();
            case MARINEFORD:
                return criarMarineford();
            case ILHA_HOMENS_PEIXE:
                return criarIlhaHomeisPeixe();
            case PUNK_HAZARD:
                return criarPunkHazard();
            case DRESSROSA:
                return criarDressrosa();
            case WHOLE_CAKE:
                return criarWholeCake();
            case WANO:
                return criarWano();
            case EGGHEAD:
                return criarEgghead();
            case ELBAPH:
                return criarElbaph();
            default:
                throw new IllegalArgumentException("Ilha desconhecida: " + ilhaEnum);
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Helpers de criação
    // ──────────────────────────────────────────────────────────────────────────

    /** Cria um inimigo capanga escalado pelo índice da ilha. */
    private static Inimigo cap(String nome, int idx, String spriteKey) {
        int hp = 60 + idx * 25;
        int def = 2 + idx;
        int inic = 8 + idx;
        int poder = 12 + idx * 5;
        Inimigo e = new Inimigo(nome, hp, def, inic,
                new Recompensa(40 + idx * 20, 20 + idx * 10), spriteKey, false);
        e.adicionarHabilidade(new Habilidade("Ataque", TipoHabilidade.DANO, poder));
        return e;
    }

    /** Cria um miniboss escalado pelo índice da ilha. */
    private static Inimigo mini(String nome, int idx, String spriteKey,
            String habNome, int habPoder) {
        int hp = 130 + idx * 50;
        int def = 5 + idx * 2;
        int inic = 10 + idx;
        Inimigo e = new Inimigo(nome, hp, def, inic,
                new Recompensa(100 + idx * 40, 50 + idx * 20), spriteKey, false);
        e.adicionarHabilidade(new Habilidade(habNome, TipoHabilidade.DANO, habPoder));
        return e;
    }

    /** Cria um boss escalado pelo índice da ilha, com duas habilidades. */
    private static Inimigo boss(String nome, int idx, String spriteKey,
            String hab1, int pod1, String hab2, int pod2) {
        int hp = 250 + idx * 120;
        int def = 8 + idx * 3;
        int inic = 12 + idx * 2;
        Inimigo b = new Inimigo(nome, hp, def, inic,
                new Recompensa(200 + idx * 100, 100 + idx * 50), spriteKey, true);
        b.adicionarHabilidade(new Habilidade(hab1, TipoHabilidade.DANO, pod1));
        b.adicionarHabilidade(new Habilidade(hab2, TipoHabilidade.DANO, pod2));
        return b;
    }

    private static Rodada r(String desc, boolean isBoss, Inimigo... inimigos) {
        return new Rodada(Arrays.asList(inimigos), isBoss, desc);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Ilhas
    // ──────────────────────────────────────────────────────────────────────────

    private static Ilha criarOrangeTown() {
        return new Ilha("Orange Town", "backgrounds/orange_town.png", Arrays.asList(
                r("Rodada 1 — Piratas de Buggy", false,
                        cap("Pirata de Buggy", 0, "inimigos/capangas/capanga_pirata.png"),
                        cap("Pirata de Buggy", 0, "inimigos/capangas/capanga_pirata.png")),
                r("Rodada 2 — Lugartenentes", false,
                        mini("Mohji & Richie", 0, "inimigos/minibosses/mohji.png", "Richie Ataca!", 22),
                        mini("Cabaji", 0, "inimigos/minibosses/cabaji.png", "Acrobacia Letal", 20)),
                r("BOSS — Buggy, o Palhaço!", true,
                        boss("Buggy, o Palhaço", 0, "inimigos/bosses/buggy.png",
                                "Separação das Peças", 35, "Bala de Buggy", 42))));
    }

    private static Ilha criarVilaSyrup() {
        return new Ilha("Vila Syrup", "backgrounds/vila_syrup.png", Arrays.asList(
                r("Rodada 1 — Servos da Mansão", false,
                        cap("Servo de Kuro", 1, "inimigos/capangas/capanga_kuro.png"),
                        cap("Servo de Kuro", 1, "inimigos/capangas/capanga_kuro.png"),
                        cap("Servo de Kuro", 1, "inimigos/capangas/capanga_kuro.png")),
                r("Rodada 2 — Jango", false,
                        mini("Jango", 1, "inimigos/minibosses/jango.png", "Hipnose!", 28)),
                r("BOSS — Capitão Kuro!", true,
                        boss("Capitão Kuro", 1, "inimigos/bosses/kuro.png",
                                "Unhas de Gato", 45, "Tempo de Massacre", 55))));
    }

    private static Ilha criarBaratie() {
        return new Ilha("Baratie", "backgrounds/baratie.png", Arrays.asList(
                r("Rodada 1 — Soldados de Krieg", false,
                        cap("Soldado de Krieg", 2, "inimigos/capangas/capanga_krieg.png"),
                        cap("Soldado de Krieg", 2, "inimigos/capangas/capanga_krieg.png")),
                r("Rodada 2 — Pearl & Gin", false,
                        mini("Pearl", 2, "inimigos/minibosses/pearl.png", "Escudo de Fogo", 32),
                        mini("Gin", 2, "inimigos/minibosses/gin.png", "Tonfa de Ferro", 35)),
                r("Interlúdio — Dracule Mihawk", false,
                        boss("Dracule Mihawk", 2, "inimigos/bosses/mihawk.png",
                                "Corte de Cruz", 70, "Yoru — Pleno Negro", 85)),
                r("BOSS — Don Krieg!", true,
                        boss("Don Krieg", 2, "inimigos/bosses/krieg.png",
                                "Bazuca MH5", 50, "Granada Explosiva", 60))));
    }

    private static Ilha criarArlongPark() {
        return new Ilha("Arlong Park", "backgrounds/arlong_park.png", Arrays.asList(
                r("Rodada 1 — Homens-Peixe", false,
                        cap("Homem-Peixe", 3, "inimigos/capangas/capanga_arlong.png"),
                        cap("Homem-Peixe", 3, "inimigos/capangas/capanga_arlong.png")),
                r("Rodada 2 — Tripulação de Arlong", false,
                        mini("Hatchan", 3, "inimigos/minibosses/hachi.png", "Seis Espadas", 38),
                        mini("Kuroobi", 3, "inimigos/minibosses/kuroobi.png", "Artes Marciais Peixe", 35),
                        mini("Chuu", 3, "inimigos/minibosses/chuu.png", "Pistola d'Água", 30)),
                r("BOSS — Arlong!", true,
                        boss("Arlong", 3, "inimigos/bosses/arlong.png",
                                "Mandíbula de Tubarão", 55, "Nariz Viu?", 65))));
    }

    private static Ilha criarLoguetown() {
        return new Ilha("Loguetown", "backgrounds/loguetown.png", Arrays.asList(
                r("Rodada 1 — Patrulha da Marinha", false,
                        cap("Soldado da Marinha", 4, "inimigos/capangas/capanga_marinha.png"),
                        cap("Soldado da Marinha", 4, "inimigos/capangas/capanga_marinha.png"),
                        cap("Soldado da Marinha", 4, "inimigos/capangas/capanga_marinha.png")),
                r("BOSS — Capitão Smoker!", true,
                        boss("Capitão Smoker", 4, "inimigos/bosses/smoker.png",
                                "Fumaça Branca", 60, "Moku Moku — Pressão Total", 75))));
    }

    private static Ilha criarLittleGarden() {
        return new Ilha("Little Garden", "backgrounds/little_garden.png", Arrays.asList(
                r("Rodada 1 — Agentes Baroque Works", false,
                        cap("Agente Baroque", 5, "inimigos/capangas/capanga_baroque.png"),
                        cap("Agente Baroque", 5, "inimigos/capangas/capanga_baroque.png")),
                r("Rodada 2 — Mr. 5 & Miss Valentine", false,
                        mini("Mr. 5", 5, "inimigos/minibosses/mr5.png", "Bala Explosiva", 48),
                        mini("Miss Valentine", 5, "inimigos/minibosses/mr5.png", "Quilo-Quilo Crush", 44)),
                r("BOSS — Mr. 3 (Galdino)!", true,
                        boss("Mr. 3 — Galdino", 5, "inimigos/bosses/mr3.png",
                                "Castiçal de Cera", 68, "Grande Stalagmite", 78))));
    }

    private static Ilha criarIlhaDrum() {
        return new Ilha("Ilha Drum", "backgrounds/ilha_drum.png", Arrays.asList(
                r("Rodada 1 — Soldados de Wapol", false,
                        cap("Soldado de Wapol", 6, "inimigos/capangas/capanga_baroque.png"),
                        cap("Soldado de Wapol", 6, "inimigos/capangas/capanga_baroque.png")),
                r("Rodada 2 — Chessmarimo", false,
                        mini("Chess", 6, "inimigos/minibosses/mr5.png", "Flecha Precisa", 52),
                        mini("Marimo", 6, "inimigos/minibosses/mr5.png", "Machado Pesado", 55)),
                r("BOSS — Wapol!", true,
                        boss("Wapol", 6, "inimigos/bosses/wapol.png",
                                "Boca-Canhão", 75, "Fusão Wapol", 88))));
    }

    private static Ilha criarAlabasta() {
        return new Ilha("Reino de Alabasta", "backgrounds/alabasta.png", Arrays.asList(
                r("Rodada 1 — Agentes Cobra", false,
                        cap("Agente Baroque", 7, "inimigos/capangas/capanga_baroque.png"),
                        cap("Agente Baroque", 7, "inimigos/capangas/capanga_baroque.png"),
                        cap("Agente Baroque", 7, "inimigos/capangas/capanga_baroque.png")),
                r("Rodada 2 — Mr. 4 & Mr. 2", false,
                        mini("Mr. 4", 7, "inimigos/minibosses/mr4.png", "Bola Explosiva L4", 60),
                        mini("Mr. 2 Bon Clay", 7, "inimigos/minibosses/mr2.png", "Ballet Kenpo", 58)),
                r("Rodada 3 — Mr. 1 (Daz Bonez)", false,
                        mini("Mr. 1 — Daz Bonez", 7, "inimigos/minibosses/mr1.png", "Lâminas de Aço", 70)),
                r("BOSS — Sir Crocodile (Mr. 0)!", true,
                        boss("Sir Crocodile", 7, "inimigos/bosses/crocodile.png",
                                "Desert Spada", 92, "Sables — Tempestade de Areia", 105))));
    }

    private static Ilha criarJaya() {
        return new Ilha("Jaya", "backgrounds/jaya.png", Arrays.asList(
                r("Rodada 1 — Piratas de Bellamy", false,
                        cap("Pirata Believer", 8, "inimigos/capangas/capanga_pirata.png"),
                        cap("Pirata Believer", 8, "inimigos/capangas/capanga_pirata.png")),
                r("Rodada 2 — Bellamy, a Hiena", false,
                        mini("Bellamy", 8, "inimigos/minibosses/bellamy.png",
                                "Spring Hopper", 80)),
                r("BOSS — Marshall D. Teach (Barba Negra)!", true,
                        boss("Marshall D. Teach", 8, "inimigos/bosses/barbanegra_jaya.png",
                                "Yami Yami no Mi", 98, "Trevas Absolutas", 115))));
    }

    private static Ilha criarSkypiea() {
        return new Ilha("Skypiea", "backgrounds/skypiea.png", Arrays.asList(
                r("Rodada 1 — Guerreiros do Céu", false,
                        cap("Guerreiro de Skypiea", 9, "inimigos/capangas/capanga_skypiea.png"),
                        cap("Guerreiro de Skypiea", 9, "inimigos/capangas/capanga_skypiea.png")),
                r("Rodada 2 — Satori & Ohm", false,
                        mini("Satori", 9, "inimigos/minibosses/satori.png", "Nuvem Explosiva", 85),
                        mini("Ohm", 9, "inimigos/minibosses/ohm.png", "Wire of Law", 90)),
                r("BOSS — Enel!", true,
                        boss("Enel", 9, "inimigos/bosses/enel.png",
                                "Raio Divino — El Thor", 118, "Luzifer — 200 Milhões de Volts", 140))));
    }

    private static Ilha criarEniesLobby() {
        return new Ilha("Enies Lobby", "backgrounds/enies_lobby.png", Arrays.asList(
                r("Rodada 1 — Agentes CP9", false,
                        cap("Agente CP9", 10, "inimigos/capangas/capanga_cp9.png"),
                        cap("Agente CP9", 10, "inimigos/capangas/capanga_cp9.png")),
                r("Rodada 2 — Jabura & Kaku", false,
                        mini("Jabura", 10, "inimigos/minibosses/jabura.png", "Rankyaku Lupus", 95),
                        mini("Kaku", 10, "inimigos/minibosses/kaku.png", "Pasta Buster", 100)),
                r("BOSS — Rob Lucci!", true,
                        boss("Rob Lucci", 10, "inimigos/bosses/rob_lucci.png",
                                "Rokuogan", 130, "Seis Poderes — Impacto Supremo", 148))));
    }

    private static Ilha criarThrillerBark() {
        return new Ilha("Thriller Bark", "backgrounds/thriller_bark.png", Arrays.asList(
                r("Rodada 1 — Zumbis de Moria", false,
                        cap("Zumbi Guerreiro", 11, "inimigos/capangas/capanga_moria.png"),
                        cap("Zumbi Guerreiro", 11, "inimigos/capangas/capanga_moria.png"),
                        cap("Zumbi Guerreiro", 11, "inimigos/capangas/capanga_moria.png")),
                r("Rodada 2 — Absalom & Perona", false,
                        mini("Absalom", 11, "inimigos/minibosses/absalom.png", "Carne de Besta", 105),
                        mini("Perona", 11, "inimigos/minibosses/perona.png", "Fantasmas Negativos", 98)),
                r("BOSS — Gecko Moria!", true,
                        boss("Gecko Moria", 11, "inimigos/bosses/moria.png",
                                "Sombra de Batalha — Doppelman", 140, "Shadow Asgard", 162))));
    }

    private static Ilha criarSabaody() {
        return new Ilha("Arquipélago Sabaody", "backgrounds/sabaody.png", Arrays.asList(
                r("Rodada 1 — Guardas dos Dragões Celestiais", false,
                        cap("Guarda Tenryuubito", 12, "inimigos/capangas/capanga_tenryuubito.png"),
                        cap("Guarda Tenryuubito", 12, "inimigos/capangas/capanga_tenryuubito.png")),
                r("Rodada 2 — Sentomaru & Pacifistas", false,
                        mini("Sentomaru", 12, "inimigos/minibosses/sentomaru.png",
                                "Ashigara Dokkoi", 118)),
                r("BOSS — Almirante Kizaru!", true,
                        boss("Almirante Kizaru", 12, "inimigos/bosses/kizaru.png",
                                "Yata no Kagami", 155, "Amaterasu — Luz do Sol", 175))));
    }

    private static Ilha criarImpelDown() {
        return new Ilha("Impel Down", "backgrounds/impel_down.png", Arrays.asList(
                r("Rodada 1 — Guardas da Prisão", false,
                        cap("Guarda Impel Down", 13, "inimigos/capangas/capanga_marinha.png"),
                        cap("Guarda Impel Down", 13, "inimigos/capangas/capanga_marinha.png"),
                        cap("Guarda Impel Down", 13, "inimigos/capangas/capanga_marinha.png")),
                r("Rodada 2 — Sadi-chan & Hannyabal", false,
                        mini("Sadi-chan", 13, "inimigos/minibosses/sadi.png", "Chicote Venenoso", 125),
                        mini("Hannyabal", 13, "inimigos/minibosses/hannyabal.png", "Naginata Hibrida", 120)),
                r("BOSS — Magellan!", true,
                        boss("Magellan", 13, "inimigos/bosses/magellan.png",
                                "Hydra — Veneno Letal", 168, "Venom Demon — Inferno do Veneno", 192))));
    }

    private static Ilha criarMarineford() {
        return new Ilha("Marineford", "backgrounds/marineford.png", Arrays.asList(
                r("Rodada 1 — Tropas da Marinha", false,
                        cap("Vice-Almirante", 14, "inimigos/capangas/capanga_marinha.png"),
                        cap("Vice-Almirante", 14, "inimigos/capangas/capanga_marinha.png")),
                r("Rodada 2 — Barba Negra Pós-Marineford", false,
                        mini("Marshall D. Teach", 14, "inimigos/bosses/barbanegra.png",
                                "Poder de Barba Branca", 145)),
                r("BOSS — Almirante Akainu!", true,
                        boss("Almirante Akainu", 14, "inimigos/bosses/akainu.png",
                                "Ryusei Kazan", 185, "Meigo Boshi — Justiça Absoluta", 210))));
    }

    private static Ilha criarIlhaHomeisPeixe() {
        return new Ilha("Ilha dos Homens-Peixe", "backgrounds/ilha_homens_peixe.png", Arrays.asList(
                r("Rodada 1 — Novos Homens-Peixe", false,
                        cap("Soldado Homem-Peixe", 15, "inimigos/capangas/capanga_peixe.png"),
                        cap("Soldado Homem-Peixe", 15, "inimigos/capangas/capanga_peixe.png"),
                        cap("Soldado Homem-Peixe", 15, "inimigos/capangas/capanga_peixe.png")),
                r("Rodada 2 — Vander Decken IX", false,
                        mini("Vander Decken IX", 15, "inimigos/minibosses/hody_vandeck.png",
                                "Mark Mark Throw", 148)),
                r("BOSS — Hody Jones!", true,
                        boss("Hody Jones", 15, "inimigos/bosses/hody.png",
                                "Yabusame — Chuva de Dentes", 195, "Uchimizu — Impacto Aquático", 218))));
    }

    private static Ilha criarPunkHazard() {
        return new Ilha("Punk Hazard", "backgrounds/punk_hazard.png", Arrays.asList(
                r("Rodada 1 — Criaturas do Lab", false,
                        cap("Monstro do Lab", 16, "inimigos/capangas/capanga_punk.png"),
                        cap("Monstro do Lab", 16, "inimigos/capangas/capanga_punk.png")),
                r("Rodada 2 — Vergo & Monet", false,
                        mini("Vergo", 16, "inimigos/minibosses/vergo.png", "Haki do Peitoral", 158),
                        mini("Monet", 16, "inimigos/minibosses/monet.png", "Blizzard de Neve", 152)),
                r("BOSS — Caesar Clown!", true,
                        boss("Caesar Clown", 16, "inimigos/bosses/caesar.png",
                                "Gastanet — Gás Explosivo", 205, "Shinokuni — Gás da Morte", 235))));
    }

    private static Ilha criarDressrosa() {
        return new Ilha("Dressrosa", "backgrounds/dressrosa.png", Arrays.asList(
                r("Rodada 1 — Membros DONQUIXOTE", false,
                        cap("Membro Donquixote", 17, "inimigos/capangas/capanga_dofla.png"),
                        cap("Membro Donquixote", 17, "inimigos/capangas/capanga_dofla.png")),
                r("Rodada 2 — Trebol, Pica & Diamante", false,
                        mini("Trebol", 17, "inimigos/minibosses/trebol.png", "Beta Beta Mochi", 165),
                        mini("Pica", 17, "inimigos/minibosses/pica.png", "Pedra Colossal", 170),
                        mini("Diamante", 17, "inimigos/minibosses/diamante.png", "Hira Hira Slice", 162)),
                r("BOSS — Donquixote Doflamingo!", true,
                        boss("Donquixote Doflamingo", 17, "inimigos/bosses/doflamingo.png",
                                "Overheat — Fio Ardente", 218, "God Thread — Punição Divina", 248))));
    }

    private static Ilha criarWholeCake() {
        return new Ilha("Whole Cake Island", "backgrounds/whole_cake.png", Arrays.asList(
                r("Rodada 1 — Família Charlotte", false,
                        cap("Soldado Charlotte", 18, "inimigos/capangas/capanga_bigmom.png"),
                        cap("Soldado Charlotte", 18, "inimigos/capangas/capanga_bigmom.png")),
                r("Rodada 2 — Cracker & Smoothie", false,
                        mini("Cracker", 18, "inimigos/minibosses/cracker.png", "Biscuit Soldiers", 175),
                        mini("Smoothie", 18, "inimigos/minibosses/smoothie.png", "Squeeze — Suco Fatal", 170)),
                r("Rodada 3 — Charlotte Katakuri!", false,
                        boss("Charlotte Katakuri", 18, "inimigos/bosses/katakuri.png",
                                "Zan Giri Mochi", 228, "Kaki no Mi — Awakening", 258)),
                r("BOSS — Big Mom (Charlotte Linlin)!", true,
                        boss("Big Mom", 18, "inimigos/bosses/bigmom.png",
                                "Soul Pocus — Roubo de Alma", 235, "Ikoku Sovereignty", 268))));
    }

    private static Ilha criarWano() {
        return new Ilha("Wano Kuni", "backgrounds/wano.png", Arrays.asList(
                r("Rodada 1 — Soldados de Orochi & Kaido", false,
                        cap("Samurai de Orochi", 19, "inimigos/capangas/capanga_wano.png"),
                        cap("Samurai de Orochi", 19, "inimigos/capangas/capanga_wano.png"),
                        cap("Samurai de Orochi", 19, "inimigos/capangas/capanga_wano.png")),
                r("Rodada 2 — Jack, Queen & King", false,
                        mini("Jack", 19, "inimigos/minibosses/jack.png", "Mammoth Charge", 185),
                        mini("Queen", 19, "inimigos/minibosses/queen.png", "Black Coffee Beam", 192),
                        mini("King", 19, "inimigos/minibosses/king.png", "Imperial Flaming Wings", 200)),
                r("Rodada 3 — Kurozumi Orochi", false,
                        boss("Kurozumi Orochi", 19, "inimigos/bosses/orochi.png",
                                "Yamata no Orochi Bite", 210, "Veneno de Serpente", 225)),
                r("BOSS — Kaido das Feras!", true,
                        boss("Kaido das Feras", 19, "inimigos/bosses/kaido.png",
                                "Boro Breath — Sopro de Dragão", 255, "Raimei Hakke — Raio dos Oito Trigamas", 295))));
    }

    private static Ilha criarEgghead() {
        return new Ilha("Egghead — Ilha do Futuro", "backgrounds/egghead.png", Arrays.asList(
                r("Rodada 1 — CP0 & Pacifistas Seraphim", false,
                        cap("Agente CP0", 20, "inimigos/capangas/capanga_gorosei.png"),
                        cap("Seraphim", 20, "inimigos/capangas/capanga_gorosei.png")),
                r("Rodada 2 — Almirante Kizaru (Colapso)", false,
                        mini("Kizaru — Conflito Interior", 20, "inimigos/bosses/kizaru.png",
                                "Laser Absoluto", 228)),
                r("BOSS — Jaygarcia Saturn (Gorosei)!", true,
                        boss("Jaygarcia Saturn", 20, "inimigos/bosses/saturn.png",
                                "Forma Monstruosa dos Cinco Anciãos", 272, "Terror Cósmico — Aniquilação", 312))));
    }

    private static Ilha criarElbaph() {
        return new Ilha("Elbaph — Terra dos Gigantes", "backgrounds/elbaph.png", Arrays.asList(
                r("Rodada 1 — Cavaleiros Sagrados", false,
                        cap("Cavaleiro Sagrado", 21, "inimigos/capangas/capanga_elbaph.png"),
                        cap("Cavaleiro Sagrado", 21, "inimigos/capangas/capanga_elbaph.png")),
                r("Rodada 2 — Cavaleiros Sagrados Élite", false,
                        mini("Cavaleiro Élite", 21, "inimigos/bosses/cavaleiro_sagrado.png",
                                "Espada Divina dos Cavaleiros", 288)),
                r("BOSS FINAL — Imu-sama!", true,
                        boss("Imu-sama", 21, "inimigos/bosses/imu.png",
                                "Ira da Criação — Kamaitachi", 320, "Destruição Total — Fin du Monde", 380))));
    }
}
