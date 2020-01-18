package com.example.shoppinglist;

import java.util.ArrayList;

public class ShoppingList {

    private String nome;
    private ArrayList<Product> products;

    public ShoppingList()
    {
        this.nome = null;
        this.products = new ArrayList<>();
    }

    public ShoppingList(String nome, ArrayList<Product> products)
    {
        this.nome = nome;
        this.products = products;
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product)
    {
        this.products.add(product);
    }

    public double getTotalPrice()
    {
        int i;
        double total = 0;
        for (i=0; i<this.products.size(); i++)
        {
            total += this.products.get(i).getPreco();
        }
        return total;
    }
    public int getTotalItems()
    {
        int i;
        int total = 0;
        for (i=0; i<this.products.size();i++)
        {
            total++;
        }
        return total;
    }
}
