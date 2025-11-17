package br.com.gerlandio.BookRegistryAPI.application.service;

import br.com.gerlandio.BookRegistryAPI.domain.exception.BookNotFoundException;
import br.com.gerlandio.BookRegistryAPI.domain.exception.DuplicateIsbnException;
import br.com.gerlandio.BookRegistryAPI.domain.model.Book;
import br.com.gerlandio.BookRegistryAPI.domain.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
     public Book createBook(Book book){
        if(book.getIsbn() == null || book.getIsbn().trim().isEmpty()){
            throw new IllegalArgumentException("ISBN é obrigatorio");
        }
        if(bookRepository.existsByIsbn(book.getIsbn())){
            throw new DuplicateIsbnException(book.getIsbn());
        }
        if(book.getQuantidadeEstoque() == null){
            book.setQuantidadeEstoque(0);
        }
        return  bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book getById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Book getByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    @Transactional(readOnly = true)
    public List<Book> listAll(){
        return  bookRepository.findAll();
    }

    @Transactional
    public  Book update(Long id,Book update){
        Book existing = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        String newIsbn = update.getIsbn();
        if(newIsbn != null && !newIsbn.equals(existing.getIsbn())){
            if(bookRepository.existsByIsbn(newIsbn)){
                throw new DuplicateIsbnException(newIsbn);
            }
        }
        existing.updateFrom(update);
        return  bookRepository.save(existing);
    }
    @Transactional
    public void delete(Long id){
        Book existing = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(existing.getId());
    }
}
