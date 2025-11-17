package br.com.gerlandio.BookRegistryAPI.application.service;


import br.com.gerlandio.BookRegistryAPI.domain.exception.DuplicateIsbnException;
import br.com.gerlandio.BookRegistryAPI.domain.model.Book;
import br.com.gerlandio.BookRegistryAPI.domain.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void deveCriarLivroQuandoIsbnForUnico(){
        Book book = new Book(null,
                "Dom Casmurro",
                "Machado de Assis",
                "9788503012611",
                1899,
                8);
        Book saved = new Book(1L,
                "Dom Casmurro",
                "Machado de Assis",
                "9788503012611",
                1899,
                8);

        when(bookRepository.existsByIsbn("9788503012611")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(saved);

        Book result = bookService.createBook(book);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitulo()).isEqualTo("Dom Casmurro");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void deveLancarExcecaoQuandoIsbnJaExistir(){
        Book input = new Book(null,
                "Dom Casmurro",
                "Machado de Assis",
                "9788503012611",
                1899,
                8);

        when(bookRepository.existsByIsbn("9788503012611")).thenReturn(true);

        assertThatThrownBy(() -> bookService.createBook(input))
                .isInstanceOf(DuplicateIsbnException.class);

        verify(bookRepository, never()).save(any());
    }

}
