package entidades;

public class Item {
    private String nome;
    private String descricao;
    private int preco;
    private int valor;

    public Item(String nome, String descricao, int preco, int valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.valor = valor;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getPreco() { return preco; }
    public int getValor() { return valor; }

    public void usar(Personagem alvo) {
        alvo.curar(this.valor);
    }
}
