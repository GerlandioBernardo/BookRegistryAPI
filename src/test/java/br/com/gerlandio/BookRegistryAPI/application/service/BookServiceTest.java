package br.com.gerlandio.BookRegistryAPI.application.service;

import br.com.gerlandio.BookRegistryAPI.domain.exception.BookNotFoundException;
import br.com.gerlandio.BookRegistryAPI.domain.exception.DuplicateIsbnException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    // Testes de CREATE
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


    // Testes de GET

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

    // Testes de UPDATE

    @Test
    void deveAtualizarLivroQuandoDadosForemValidos(){
        Book existing = new Book(1L,
                "Dom Casmurro",
                "Machado de Assis",
                "9788503012611",
                1899,
                8);
        Book update = new Book(null,
                "Dom Casmurro - Edição Comentada",
                "Machado de Assis",
                "9788503012611",
                1899,
                12);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.update(1L, update);

        assertThat(result.getTitulo()).isEqualTo("Dom Casmurro - Edição Comentada");

        verify(bookRepository, times(1)).save(existing);

    }

    @Test
    void  deveLancarExcecaoAoAtualizarQuandoIdNaoExistir(){
        when(bookRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.update(5L, new Book()))
                .isInstanceOf(BookNotFoundException.class);
    }

    // Testes de DELETE

    @Test
    void  deveExcluirLivroQuandoIdExistir() {
        Book existing = new Book(1L, "Dom Casmurro",
                "Machado de Assis",
                "9788503012611",
                1899,
                12);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));

        bookService.delete(1L);

        verify(bookRepository, times(1)).delete(1L);
    }

    @Test
    void  deveLancarExcecaoAoExcluirQuandoIdNaoExistir(){
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.delete(2L))
                .isInstanceOf(BookNotFoundException.class);
    }

}
