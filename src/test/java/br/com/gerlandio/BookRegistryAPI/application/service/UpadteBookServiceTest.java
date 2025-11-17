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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpadteBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

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
}
