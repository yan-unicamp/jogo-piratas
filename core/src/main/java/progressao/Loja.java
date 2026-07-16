package progressao;

import entidades.EfeitoTemporario;
import entidades.EfeitoTemporario.Tipo;
import sistema.Tripulacao;
import entidades.Aliado;

public class Loja {

    public enum ItemLoja {
        CARNE_COM_OSSO("Recupera 40 de HP de um aliado", 50),
        ONIGIRI_FRIO("Recupera 80 de HP de um aliado", 120),
        GARRAFA_DE_COLA("Recupera 30 de HP de um aliado", 40),
        SOPA_FRUTOS_DO_MAR("Recupera 60 de HP de toda a equipe", 250),
        RUMBLE_BALL("Aumenta o Ataque em +15% por 3 turnos", 100),
        PIMENTA_ALABASTA("Aumenta o Ataque em +30% por 2 turnos, mas o usuário perde -5 HP no processo", 150),
        SAKE_WANO("Adiciona +15 de Dano Fixo extra no próximo ataque realizado", 80),
        BEBIDA_ENERGETICA("Aumenta o Ataque de toda a equipe em +10% por 3 turnos", 220),
        ESTRELA_DE_FUMACA("Cega o inimigo, reduzindo o Dano dele em -20% por 2 turnos", 90),
        GAS_DO_SONO("Causa letargia, reduzindo o Ataque do inimigo em -15% por 3 turnos", 120),
        FIO_DE_TEIA("Prende o inimigo, reduzindo o dano do próximo golpe dele pela metade (-50%)", 180),
        SAL_PURIFICADOR("Remove buffs do inimigo e reduz o Ataque base dele em -10% por 2 turnos", 150),
        CAPA_DA_MARINHA("Aumenta a Defesa de um aliado em +15 por 3 turnos", 100),
        CASCA_TRITAO("Cria uma barreira que absorve os próximos 50 pontos de dano recebidos", 160),
        ESSENCIA_HAKI("Garante Imunidade (0 de dano) contra o próximo ataque físico recebido por um aliado", 300),
        PO_DE_FERRO("Aumenta a Defesa de toda a equipe em +10 por 3 turnos", 240),
        VIVRE_CARD("Permite fugir automaticamente de 1 batalha comum com 100% de sucesso (não funciona em chefes)", 150),
        LOG_POSE("Aumenta em 1 o número de ilhas possíveis a se navegar por uma vez", 400);

        private final String descricao;
        private final int preco;

        ItemLoja(String descricao, int preco) {
            this.descricao = descricao;
            this.preco = preco;
        }

        public String getDescricao() { return descricao; }
        public int getPreco() { return preco; }

        /** @deprecated Use getDescricao() — renomeado para clareza. */
        @Deprecated
        public String getNome() { return descricao; }
    }

    /** @return array com todos os itens disponíveis na loja. Usado pela TelaLoja. */
    public ItemLoja[] getCatalogo() {
        return ItemLoja.values();
    }

    /**
     * Tenta realizar a compra de um item para um aliado alvo.
     * Debita o dinheiro da tripulação e aplica o efeito no alvo.
     *
     * @param cliente   A tripulação que está comprando.
     * @param item      O item selecionado.
     * @param alvo      O aliado que receberá o efeito (null para itens de equipe).
     * @return true se a compra foi realizada com sucesso; false se saldo insuficiente.
     */
    public boolean comprarMelhoria(Tripulacao cliente, ItemLoja item, Aliado alvo) {
        if (!cliente.gastarDinheiro(item.getPreco())) {
            return false;
        }
        aplicarEfeito(item, alvo, cliente);
        return true;
    }

    /**
     * Mantido por compatibilidade — compra sem alvo específico (itens de equipe).
     */
    public boolean comprarMelhoria(Tripulacao cliente, ItemLoja item) {
        return comprarMelhoria(cliente, item, null);
    }

    /**
     * Aplica o efeito de um item a um alvo ou à equipe inteira.
     * Itens de cura/defesa individual: exigem alvo != null.
     * Itens de equipe: operam sobre a tripulacao.
     *
     * NOTA: Efeitos que dependem de curar() e receberDano() ficarão completos
     * quando o Membro 1 implementar esses métodos em Personagem (Fase 2).
     */
    public void aplicarEfeito(ItemLoja item, Aliado alvo, Tripulacao tripulacao) {
        switch (item) {
            // --- Cura individual ---
            case CARNE_COM_OSSO:
                if (alvo != null) alvo.curar(40);
                break;
            case GARRAFA_DE_COLA:
                if (alvo != null) alvo.curar(30);
                break;
            case ONIGIRI_FRIO:
                if (alvo != null) alvo.curar(80);
                break;

            // --- Cura de equipe ---
            case SOPA_FRUTOS_DO_MAR:
                if (tripulacao != null) {
                    for (Aliado a : tripulacao.getAliadosVivos()) {
                        a.curar(60);
                    }
                }
                break;

            // --- Buffs de ataque ---
            case RUMBLE_BALL:
                if (alvo != null) alvo.adicionarEfeito(new EfeitoTemporario(Tipo.MULTIPLICADOR_DANO, 1.15f, 3));
                break;
            case PIMENTA_ALABASTA:
                if (alvo != null) {
                    alvo.adicionarEfeito(new EfeitoTemporario(Tipo.MULTIPLICADOR_DANO, 1.30f, 2));
                    alvo.receberDano(5); // Perde -5 HP
                }
                break;
            case SAKE_WANO:
                if (alvo != null) alvo.adicionarEfeito(new EfeitoTemporario(Tipo.DANO_FIXO_EXTRA, 15f, 1));
                break;
            case BEBIDA_ENERGETICA:
                if (tripulacao != null) {
                    for (Aliado a : tripulacao.getAliadosVivos()) {
                        a.adicionarEfeito(new EfeitoTemporario(Tipo.MULTIPLICADOR_DANO, 1.10f, 3));
                    }
                }
                break;

            // --- Debuffs (Nota: precisaríamos de alvo inimigo para esses. Na estrutura atual a Loja alvo é Aliado. 
            // Vamos precisar adicionar o efeito no GameManager se ele tiver getInimigosAtivos, ou a Loja não consegue dar debuff no inimigo direto.
            // Para simplificar, como a Loja só tem acesso a alvo (Aliado), não podemos aplicar no inimigo diretamente aqui
            // a não ser que adicionássemos GameManager ou Inimigo. Mas vamos aplicar temporariamente como um "Aura" no Aliado?
            // Não, o tipo REDUCAO_DANO_INIMIGO (Cegar) não faz sentido no Aliado. Vamos apenas logar que precisa de refatoração para selecionar inimigos na loja.
            // Wait, na verdade, a TelaLoja não deixa mirar em inimigos porque inimigos não estão lá.
            case ESTRELA_DE_FUMACA:
            case GAS_DO_SONO:
            case FIO_DE_TEIA:
            case SAL_PURIFICADOR:
                System.out.println("Itens de debuff devem ser usados em batalha, a Loja (Pré-Batalha) não tem inimigos válidos para aplicar.");
                break;

            // --- Buffs de defesa e especiais ---
            case CAPA_DA_MARINHA:
                if (alvo != null) alvo.adicionarEfeito(new EfeitoTemporario(Tipo.BARREIRA, 15f, 3)); // +15 de barreira por 3 turnos (simplificando)
                break;
            case CASCA_TRITAO:
                if (alvo != null) alvo.adicionarEfeito(new EfeitoTemporario(Tipo.BARREIRA, 50f, 999)); // Sem limite de turnos
                break;
            case ESSENCIA_HAKI:
                if (alvo != null) alvo.adicionarEfeito(new EfeitoTemporario(Tipo.IMUNIDADE, 1f, 1));
                break;
            case PO_DE_FERRO:
                if (tripulacao != null) {
                    for (Aliado a : tripulacao.getAliadosVivos()) {
                        a.adicionarEfeito(new EfeitoTemporario(Tipo.BARREIRA, 10f, 3));
                    }
                }
                break;
            case VIVRE_CARD:
                // Permitiria fugir. Requer integração com GameManager/Batalha.
                System.out.println("VIVRE_CARD ativado: Fuga garantida (requer implementação na UI de Batalha).");
                break;
            case LOG_POSE:
                // Aumentaria ilhas. Mapa linear agora, não tem efeito prático além de lore.
                System.out.println("LOG_POSE ativado: Caminhos expandidos.");
                break;

            default:
                break;
        }
    }

    /**
     * Versão de debug — útil durante desenvolvimento.
     * Em produção, a TelaLoja usará getCatalogo() diretamente.
     */
    public void exibirCatalogo() {
        ItemLoja[] itens = getCatalogo();
        System.out.println("=== CATÁLOGO DA LOJA ===");
        for (int i = 0; i < itens.length; i++) {
            System.out.printf("[%2d] %-20s | %s | %d ouro%n",
                    i, itens[i].name(), itens[i].getDescricao(), itens[i].getPreco());
        }
        System.out.println("========================");
    }
}
