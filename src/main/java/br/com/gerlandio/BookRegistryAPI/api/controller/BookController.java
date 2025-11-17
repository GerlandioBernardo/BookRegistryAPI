package br.com.gerlandio.BookRegistryAPI.api.controller;

import br.com.gerlandio.BookRegistryAPI.api.dto.BookRequestDTO;
import br.com.gerlandio.BookRegistryAPI.api.dto.BookResponseDTO;
import br.com.gerlandio.BookRegistryAPI.api.mapper.BookMapper;
import br.com.gerlandio.BookRegistryAPI.application.service.BookService;
import br.com.gerlandio.BookRegistryAPI.domain.model.Book;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book")
@Validated
public class BookController {
    private  final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO bookRequestDTO){
        Book created = bookService.createBook(BookMapper.toEntity(bookRequestDTO));
        return  ResponseEntity.status(HttpStatus.CREATED).body(BookMapper.toDTO(created));
    }
    @GetMapping("/{id}")
    public  ResponseEntity<BookResponseDTO> getById(@Valid @PathVariable Long id){
        Book book = bookService.getById(id);
        return  ResponseEntity.ok(BookMapper.toDTO(book));
    }

    @GetMapping
    public  ResponseEntity<List<BookResponseDTO>> listAll(){
        List<BookResponseDTO> listBook = bookService.listAll().stream().map(BookMapper::toDTO).collect(Collectors.toList());
        return  ResponseEntity.ok(listBook);

    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponseDTO> getByIsbn(@PathVariable String isbn){
        Book book = bookService.getByIsbn(isbn);
        return ResponseEntity.ok(BookMapper.toDTO(book));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<BookResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BookRequestDTO book){
        Book updated = bookService.update(id, BookMapper.toEntity(book));
        return ResponseEntity.ok(BookMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
