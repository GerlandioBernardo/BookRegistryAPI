package br.com.gerlandio.BookRegistryAPI.application.service;

import br.com.gerlandio.BookRegistryAPI.domain.exception.BookNotFoundException;
import br.com.gerlandio.BookRegistryAPI.domain.model.Book;
import br.com.gerlandio.BookRegistryAPI.domain.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DaleteBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

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
