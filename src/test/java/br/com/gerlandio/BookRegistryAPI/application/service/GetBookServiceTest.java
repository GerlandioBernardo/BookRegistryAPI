package br.com.gerlandio.BookRegistryAPI.application.service;

import br.com.gerlandio.BookRegistryAPI.domain.exception.BookNotFoundException;
import br.com.gerlandio.BookRegistryAPI.domain.model.Book;
import br.com.gerlandio.BookRegistryAPI.domain.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private  BookService bookService;

    @Test
    void deveRetornarLivroQuandoIdExistir(){
        Book book = new Book(1L,
                "Dom Casmurro",
                "Machado de Assis",
                "9788503012611",
                1899,
                8);


        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoForEncontrado(){
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getById(2L))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void deveRetornarLivroQuandoIsbnExistir(){
        Book book = new Book(1L,
                "Dom Casmurro",
                "Machado de Assis",
                "9788503012611",
                1899,
                8);

        when(bookRepository.findByIsbn("9788503012611")).thenReturn(Optional.of(book));

        Book result = bookService.getByIsbn("9788503012611");

        assertThat(result).isNotNull();
        assertThat(result.getIsbn()).isEqualTo("9788503012611");

    }

    @Test
    void deveRetornarListaDeLivros(){
        Book book1 = new Book(1L,
                "Dom Casmurro - teste1",
                "Machado de Assis",
                "9788503012612",
                1899,
                8);

        Book book2 = new Book(2L,
                "Dom Casmurro - teste2",
                "Machado de Assis",
                "9788503012613",
                1899,
                5);


        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> resultado = bookService.listAll();

        verify(bookRepository, times(1)).findAll();
    }
}
