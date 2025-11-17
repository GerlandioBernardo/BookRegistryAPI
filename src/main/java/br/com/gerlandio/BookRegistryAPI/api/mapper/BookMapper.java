package br.com.gerlandio.BookRegistryAPI.api.mapper;

import br.com.gerlandio.BookRegistryAPI.api.dto.BookRequestDTO;
import br.com.gerlandio.BookRegistryAPI.api.dto.BookResponseDTO;
import br.com.gerlandio.BookRegistryAPI.domain.model.Book;
import org.springframework.stereotype.Component;


public class BookMapper {

    public static Book toEntity(BookRequestDTO dto){
        Book book = new Book();
        book.setTitulo(dto.getTitulo());
        book.setAutor(dto.getAutor());
        book.setIsbn(dto.getIsbn());
        book.setAnoPublicacao(dto.getAnoPublicacao());
        book.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        return book;
    }

    public static BookResponseDTO toDTO(Book book){
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitulo(book.getTitulo());
        dto.setAutor(book.getAutor());
        dto.setIsbn(book.getIsbn());
        dto.setAnoPublicacao(book.getAnoPublicacao());
        dto.setQuantidadeEstoque(book.getQuantidadeEstoque());
        return dto;
    }
}
