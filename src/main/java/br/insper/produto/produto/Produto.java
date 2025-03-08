package br.insper.produto.produto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Produto {

    @Id
    private String id;
    private String nome;
    private double preco;
    private int quantidadeEmEstoque;

   public Produto(){

   }

   public Produto(String nome, double preco, int quantidadeEmEstoque){
       this.nome = nome;
       this.preco = preco;
       this.quantidadeEmEstoque = quantidadeEmEstoque;
   }

   public String getId(){
       return id;
    }

    public void setId(String id){
       this.id = id;
    }

    public String getNome(){
       return nome;
    }

    public void setNome(String nome){
       this.nome = nome;
    }

    public double getPreco(){
       return preco;
    }

    public void setPreco(double preco){
       this.preco = preco;
    }

    public int getQuantidadeEmEstoque(){
       return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque){
       this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public boolean decrementarEstoque(int quantidade){
       if(this.quantidadeEmEstoque >= quantidade){
           this.quantidadeEmEstoque -= quantidade;
           return true;
       }
       return false;
    }
}
