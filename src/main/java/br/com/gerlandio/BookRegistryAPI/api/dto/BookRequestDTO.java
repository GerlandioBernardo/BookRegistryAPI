package br.com.gerlandio.BookRegistryAPI.api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;

public class BookRequestDTO {

    @NotBlank(message = "O titulo é obrigatório")
    private String titulo;

    @NotBlank(message = "O nome do autor é obrigatório")
    private String autor;

    @NotBlank(message = "O ISBN é obrigatório")
    @Size(min = 10, max = 13, message = "O ISBN deve ter entre 10 e 13 caracteres")
    private  String isbn;

    @NotNull(message = "O ano da publicação é obrigatório")
    private  Integer anoPublicacao;

    @NotNull(message = "O ano da publicação é obrigatório")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    private  Integer quantidadeEstoque;

    public  BookRequestDTO(){}

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
}
