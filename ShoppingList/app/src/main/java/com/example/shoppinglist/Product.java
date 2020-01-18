package com.example.shoppinglist;

public class Product {

    private String nome;
    private double preco;
    private String unidade;

    public Product ()
    {
        this.nome = "";
        this.preco = 0;
        this.unidade = "";
    }

    public Product (String nome, double preco, String unidade)
    {
        this.nome = nome;
        this.preco = preco;
        this.unidade = unidade;
    }

    public String getNome()
    {
        return this.nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public double getPreco()
    {
        return this.preco;
    }
    public void setPreco(double preco)
    {
        this.preco = preco;
    }

    public String getUnidade()
    {
        return this.unidade;
    }
    public void setUnidade(String unidade)
    {
        this.unidade = unidade;
    }
}
