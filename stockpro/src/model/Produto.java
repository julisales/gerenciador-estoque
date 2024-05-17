package model;

// Criação da classe Produto e suas variáveis
public class Produto {
    private int id;
    private String nome;
    private int quantidade;
    private double preco;

    // Criação do construtor da classe
    public Produto(int id, String nome, int quantidade, double preco) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Retorna o valor do atributo id
    public int getId() {
        return id;
    }

    // Define o valor do atributo id
    public void setId(int id) {
        this.id = id;
    }

    // Retorna o valor do atributo nome
    public String getNome() {
        return nome;
    }

    // Define o valor do atributo nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o valor do atributo quantidade
    public int getQuantidade() {
       return quantidade;
    }

    // Define o valor do atributo quantidade
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Retorna o valor do atributo preco
    public double getPreco() {
        return preco;
    }

    // Define o valor do atributo preco
    public void setPreco(double preco) {
        this.preco = preco;
    }
}
