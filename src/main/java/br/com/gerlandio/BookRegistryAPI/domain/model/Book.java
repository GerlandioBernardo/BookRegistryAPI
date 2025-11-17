package br.com.gerlandio.BookRegistryAPI.domain.model;

import jakarta.persistence.*;

import java.util.Objects;

public class Book {
    private Long id;
    private String titulo;
    private String autor;
    private  String isbn;
    private  Integer anoPublicacao;
    private  Integer quantidadeEstoque;

    public Book(){}

    public Book(Long id, String titulo, String autor, String isbn, Integer anoPublicacao,
                Integer quantidadeEstoque) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void updateFrom(Book book){
        if(book == null) return;
        this.titulo = book.titulo;
        this.autor = book.autor;
        this.isbn = book.isbn;
        this.anoPublicacao = book.anoPublicacao;
        this.quantidadeEstoque = book.quantidadeEstoque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
