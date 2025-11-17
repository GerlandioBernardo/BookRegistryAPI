package br.com.gerlandio.BookRegistryAPI.infrastructure.persistence.repository;

import br.com.gerlandio.BookRegistryAPI.domain.model.Book;
import br.com.gerlandio.BookRegistryAPI.domain.repository.BookRepository;
import br.com.gerlandio.BookRegistryAPI.infrastructure.persistence.jpa.JpaBookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaBookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public  Book toDamain(JpaBookEntity jpaBookEntity){
        if(jpaBookEntity == null){
            return  null;
        }
        return  new Book(jpaBookEntity.getId(), jpaBookEntity.getTitulo(), jpaBookEntity.getAutor(),
                jpaBookEntity.getIsbn(), jpaBookEntity.getAnoPublicacao(),
                jpaBookEntity.getQuantidadeEstoque());

    }
    public  JpaBookEntity toEntity(Book book){
        if(book == null){
            return  null;
        }
        return new JpaBookEntity(book.getId(), book.getTitulo(), book.getAutor(),
                book.getIsbn(), book.getAnoPublicacao(), book.getQuantidadeEstoque());
    }

    @Override
    @Transactional
    public Book save(Book book) {
        if(book.getId() == null){
            JpaBookEntity jpaBookEntity = toEntity(book);
            entityManager.persist(jpaBookEntity);
            entityManager.flush();
            return  toDamain(jpaBookEntity);
        }
        else {
            JpaBookEntity existing = entityManager.find(JpaBookEntity.class, book.getId());
            if(existing == null){
                JpaBookEntity entity = toEntity(book);
                JpaBookEntity merged = entityManager.merge(entity);
                entityManager.flush();
                return  toDamain(merged);
            }else {
                existing.setTitulo(book.getTitulo());
                existing.setAutor(book.getAutor());
                existing.setIsbn(book.getIsbn());
                existing.setAnoPublicacao(book.getAnoPublicacao());
                existing.setQuantidadeEstoque(book.getQuantidadeEstoque());
                JpaBookEntity merged = entityManager.merge(existing);
                entityManager.flush();
                return  toDamain(merged);

            }
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        JpaBookEntity jpaBookEntity = entityManager.find(JpaBookEntity.class, id);
        return  Optional.ofNullable(toDamain(jpaBookEntity));
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        List<JpaBookEntity> list = entityManager.createQuery("SELECT b FROM JpaBookEntity b WHERE b.isbn = :isbn", JpaBookEntity.class)
                .setParameter("isbn", isbn)
                .getResultList();
        if(list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(toDamain(list.get(0)));
    }

    @Override
    public List<Book> findAll() {
        List<JpaBookEntity> list = entityManager.createQuery("SELECT b FROM JpaBookEntity b", JpaBookEntity.class)
                .getResultList();
        return  list.stream().map(this::toDamain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        JpaBookEntity jpaBookEntity = entityManager.find(JpaBookEntity.class, id);
        if(jpaBookEntity != null){
            entityManager.remove(jpaBookEntity);
            entityManager.flush();
        }
    }

    @Override
    public boolean existsByIsbn(String isbn) {
       Long count = entityManager.createQuery("SELECT COUNT(b) FROM JpaBookEntity b WHERE b.isbn = :isbn", Long.class)
               .setParameter("isbn", isbn)
               .getSingleResult();
       return  count != null && count > 0;
    }
}
