package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;
    private int pageSize;

    @Autowired
    public BookService(BookRepository bookRepository, @Value("${api.page.size: #{10}}") int pageSize) {
        this.bookRepository = bookRepository;
        this.pageSize = pageSize;
    }

    public List<Book> findAllBooks(int page) {
        Page<Book> booksPerPage = bookRepository.findAll(
                PageRequest.of(page, pageSize));
        return booksPerPage.getContent();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findBooksByGenre(Genre genre, int page) {
        Page<Book> booksPerPage = bookRepository.findByGenre(genre, PageRequest.of(page, pageSize));
        return booksPerPage.getContent();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id);
        Optional.ofNullable(updatedBook.getAuthorName()).ifPresent(book::setAuthorName);
        Optional.ofNullable(updatedBook.getAuthorSurname()).ifPresent(book::setAuthorSurname);
        Optional.ofNullable(updatedBook.getGenre()).ifPresent(book::setGenre);
        Optional.ofNullable(updatedBook.getPublicationYear()).ifPresent(book::setPublicationYear);
        Optional.ofNullable(updatedBook.getRate()).ifPresent(book::setRate);
        Optional.ofNullable(updatedBook.getSummary()).ifPresent(book::setSummary);
        Optional.ofNullable(updatedBook.getTitle()).ifPresent(book::setTitle);
        return bookRepository.save(book);
    }
}
