package br.com.gerlandio.BookRegistryAPI.domain.repository;

import br.com.gerlandio.BookRegistryAPI.domain.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
    void delete(Long id);
    boolean existsByIsbn(String isbn);
}
