# Guia de Assets — Jogo Piratas (One Piece)

Coloque suas imagens nas pastas corretas com os nomes exatos listados abaixo.
O código carrega os arquivos pelo nome — qualquer diferença de grafia vai causar erro.

## Regras gerais de formato

| Tipo de imagem | Formato | Tamanho recomendado |
|---|---|---|
| Sprite de personagem / inimigo | PNG (fundo transparente) | 256×256 px ou 512×512 px |
| Background de ilha | PNG ou JPG | 1280×720 px |

- Nomes: **só minúsculas**, **sem espaços**, use `_` (underline)
- Potência de 2 nos sprites é obrigatória no LibGDX (64, 128, 256, 512...)

---

## 📁 assets/personagens/chapeu_de_palha/

Sprites dos membros jogáveis da tripulação principal.

| Arquivo | Personagem |
|---|---|
| `luffy.png` | Monkey D. Luffy |
| `zoro.png` | Roronoa Zoro |
| `nami.png` | Nami |
| `usopp.png` | Usopp |
| `sanji.png` | Sanji |
| `chopper.png` | Tony Tony Chopper |
| `robin.png` | Nico Robin |
| `franky.png` | Franky |
| `brook.png` | Brook |
| `jinbe.png` | Jinbe |

> Obs: `luffy.png` já existe em `assets/` — pode mover para cá ou manter os dois.

---

## 📁 assets/personagens/aliados_especiais/

Personagens jogáveis que se juntam temporariamente em ilhas específicas.

| Arquivo | Personagem | Ilha |
|---|---|---|
| `coby.png` | Koby | Orange Town |
| `vivi.png` | Nefertari Vivi | Alabasta |
| `karoo.png` | Super Pato Karoo | Alabasta |
| `bon_clay.png` | Bon Clay (Mr. 2) | Impel Down |
| `law.png` | Trafalgar D. Water Law | Punk Hazard |
| `rebecca.png` | Rebecca | Dressrosa |
| `kyros.png` | Kyros / Thunder Soldier | Dressrosa |
| `pedro.png` | Pedro | Whole Cake Island |
| `carrot.png` | Carrot | Whole Cake Island |
| `yamato.png` | Yamato | Wano Kuni |
| `momonosuke.png` | Kozuki Momonosuke | Wano Kuni |
| `vegapunk.png` | Dr. Vegapunk (Stella) | Egghead |
| `ace.png` | Portgas D. Ace | Marineford |
| `loki.png` | Loki | Elbaph |

---

## 📁 assets/backgrounds/

Imagem de fundo exibida durante a batalha de cada ilha.

| Arquivo | Ilha |
|---|---|
| `orange_town.png` | Orange Town |
| `vila_syrup.png` | Vila Syrup |
| `baratie.png` | Baratie |
| `arlong_park.png` | Arlong Park (Ilha Conomi) |
| `loguetown.png` | Loguetown |
| `little_garden.png` | Little Garden |
| `ilha_drum.png` | Ilha Drum |
| `alabasta.png` | Reino de Alabasta |
| `jaya.png` | Jaya |
| `skypiea.png` | Skypiea |
| `enies_lobby.png` | Water 7 / Enies Lobby |
| `thriller_bark.png` | Thriller Bark |
| `sabaody.png` | Arquipélago Sabaody |
| `impel_down.png` | Impel Down |
| `marineford.png` | Marineford |
| `ilha_homens_peixe.png` | Ilha dos Homens-Peixe |
| `punk_hazard.png` | Punk Hazard |
| `dressrosa.png` | Dressrosa |
| `whole_cake.png` | Whole Cake Island |
| `wano.png` | Wano Kuni |
| `egghead.png` | Egghead (Ilha do Futuro) |
| `elbaph.png` | Elbaph (A Terra dos Gigantes) |

---

## 📁 assets/inimigos/capangas/

Inimigos genéricos usados nos encontros comuns de cada ilha.
Cada ilha pode ter seu conjunto de capangas temáticos.

| Arquivo | Tipo / Inimigo | Ilha(s) |
|---|---|---|
| `capanga_pirata.png` | Pirata genérico | Orange Town, Vila Syrup |
| `capanga_marinha.png` | Soldado da Marinha | Loguetown, Marineford |
| `capanga_kuro.png` | Servo da Mansão Kaya | Vila Syrup |
| `capanga_krieg.png` | Soldado de Krieg | Baratie |
| `capanga_arlong.png` | Homem-Peixe guerreiro | Arlong Park |
| `capanga_baroque.png` | Agente da Baroque Works | Alabasta |
| `capanga_skypiea.png` | Guerreiro de Skypiea | Skypiea |
| `capanga_cp9.png` | Agente da CP9 | Enies Lobby |
| `capanga_moria.png` | Zumbi de Thriller Bark | Thriller Bark |
| `capanga_tenryuubito.png` | Guarda dos Dragões Celestiais | Sabaody |
| `capanga_peixe.png` | Soldado dos Homens-Peixe | Ilha dos Homens-Peixe |
| `capanga_punk.png` | Cientista / Monstro de Lab | Punk Hazard |
| `capanga_dofla.png` | Membro do DONQUIXOTE | Dressrosa |
| `capanga_bigmom.png` | Membros da Família Charlotte | Whole Cake |
| `capanga_wano.png` | Soldado de Orochi / Kaido | Wano Kuni |
| `capanga_gorosei.png` | Pacifista / CP0 | Egghead |
| `capanga_elbaph.png` | Cavaleiro Sagrado | Elbaph |

---

## 📁 assets/inimigos/minibosses/

Lugartenentes e oficiais — encontros intermediários mais difíceis que capangas.

| Arquivo | Personagem | Ilha |
|---|---|---|
| `mohji.png` | Mohji e Richie | Orange Town |
| `cabaji.png` | Cabaji, o Acrobata | Orange Town |
| `jango.png` | Jango | Vila Syrup |
| `pearl.png` | Pearl | Baratie |
| `gin.png` | Gin, o Matador | Baratie |
| `hachi.png` | Hatchan (Hachi) | Arlong Park |
| `kuroobi.png` | Kuroobi | Arlong Park |
| `chuu.png` | Chuu | Arlong Park |
| `mr5.png` | Mr. 5 e Miss Valentine | Little Garden |
| `mr4.png` | Mr. 4 e Miss Merry Christmas | Alabasta |
| `mr2.png` | Bon Clay (Mr. 2) — como inimigo | Alabasta |
| `mr1.png` | Mr. 1 (Daz Bonez) | Alabasta |
| `bellamy.png` | Bellamy, o Hiena | Jaya |
| `satori.png` | Satori | Skypiea |
| `ohm.png` | Ohm | Skypiea |
| `jabura.png` | Jabura | Enies Lobby |
| `kaku.png` | Kaku | Enies Lobby |
| `absalom.png` | Absalom | Thriller Bark |
| `perona.png` | Perona | Thriller Bark |
| `sentomaru.png` | Sentomaru | Sabaody |
| `ax_hand.png` | Marinheiro Mão-de-Machado | Sabaody |
| `hannyabal.png` | Hannyabal | Impel Down |
| `sadi.png` | Sadi-chan | Impel Down |
| `hody_vandeck.png` | Vander Decken IX | Ilha dos Homens-Peixe |
| `monet.png` | Monet | Punk Hazard |
| `vergo.png` | Vergo | Punk Hazard |
| `pica.png` | Pica | Dressrosa |
| `diamante.png` | Diamante | Dressrosa |
| `trebol.png` | Trebol | Dressrosa |
| `cracker.png` | Cracker | Whole Cake Island |
| `smoothie.png` | Smoothie | Whole Cake Island |
| `jack.png` | Jack | Wano Kuni |
| `queen.png` | Queen | Wano Kuni |
| `king.png` | King | Wano Kuni |
| `kizaru_sabaody.png` | Almirante Kizaru (Sabaody) | Sabaody |

---

## 📁 assets/inimigos/bosses/

Chefes de cada ilha — o combate final que libera o avanço.

| Arquivo | Boss | Ilha |
|---|---|---|
| `buggy.png` | Buggy, o Palhaço | Orange Town |
| `kuro.png` | Capitão Kuro | Vila Syrup |
| `krieg.png` | Don Krieg | Baratie |
| `mihawk.png` | Dracule Mihawk | Baratie (encontro especial) |
| `arlong.png` | Arlong | Arlong Park |
| `smoker.png` | Capitão Smoker | Loguetown |
| `mr3.png` | Mr. 3 (Galdino) | Little Garden |
| `wapol.png` | Wapol | Ilha Drum |
| `crocodile.png` | Sir Crocodile (Mr. 0) | Alabasta |
| `barbanegra_jaya.png` | Barba Negra (Marshall D. Teach) | Jaya |
| `enel.png` | Enel | Skypiea |
| `rob_lucci.png` | Rob Lucci | Enies Lobby |
| `moria.png` | Gecko Moria | Thriller Bark |
| `kizaru.png` | Almirante Kizaru (Borsalino) | Sabaody / Egghead |
| `magellan.png` | Magellan | Impel Down |
| `akainu.png` | Almirante Akainu (Sakazuki) | Marineford |
| `barbanegra.png` | Barba Negra (pós-Marineford) | Marineford |
| `hody.png` | Hody Jones | Ilha dos Homens-Peixe |
| `caesar.png` | Caesar Clown | Punk Hazard |
| `doflamingo.png` | Donquixote Doflamingo | Dressrosa |
| `bigmom.png` | Big Mom (Charlotte Linlin) | Whole Cake Island |
| `katakuri.png` | Charlotte Katakuri | Whole Cake Island |
| `kaido.png` | Kaido das Feras | Wano Kuni |
| `orochi.png` | Kurozumi Orochi | Wano Kuni |
| `saturn.png` | Jaygarcia Saturn (Gorosei) | Egghead |
| `imu.png` | Imu-sama | Elbaph |
| `cavaleiro_sagrado.png` | Cavaleiros Sagrados | Elbaph |
