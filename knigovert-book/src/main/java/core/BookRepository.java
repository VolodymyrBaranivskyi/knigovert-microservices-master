package core;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

/**
 * Created by Shera on 29.03.2018.
 */
public interface BookRepository extends Repository<Book, Long> {

    Book findById(Long id);
    Page<Book> findAll(Pageable pageable);
    Page<Book> findByTitle(String title, Pageable pageable);
    Page<Book> findByAuthorSurname(String AuthorSurname, Pageable pageable);
    Page<Book> findByGenre(Genre genre, Pageable pageable);
    Page<Book> findByGenreAndRate(Genre genre, Integer rate, Pageable pageable);
    void deleteById(Long id);
    Book save(Book book);

}
