package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Shera on 22.03.2018.
 */

@RestController
public class ServiceController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private ReviewClient reviewClient;

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException() {
        return "Not found";
    }

    @GetMapping
    public Map<String, String> getEndpoints(HttpServletRequest servletRequest) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("findAllBooks",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/api/findAllBooks//{page}").build().toString());
        map.put("findBookById",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/api/findBookById/{id}").build().toString());
        map.put("findBooksByGenre",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/api/findBooksByGenre/{genre}/{page}").build().toString());
        map.put("addBook",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/api/addBook").build().toString());
        map.put("readersOfThisBookAlsoRead",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/api/usersAlsoRead/{id}").build().toString());
        return map;
    }

    @GetMapping("api/findAllBooks/{page}")
    public List<Book> findAllBooks(@PathVariable("page") int page) {
        return bookService.findAllBooks(page);
    }

    @GetMapping(value ="api/findBookById/{id}")
    public Book finBookById(@PathVariable("id") Long id) {
        Book foundBook = bookService.findBookById(id);
        if(foundBook != null)
        {
            return foundBook;
        }
        else
        {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(value ="api/book/{id}/users")
    public List<User> getUsersByBook(@PathVariable("id") Long id) {
        return userClient.getUsers().stream()
                .filter(user -> user.getBooksRead().contains(id))
                .collect(Collectors.toList());
    }

    @GetMapping(value ="api/book/{id}/reviews")
    public List<Review> getReviewsByBook(@PathVariable("id") Long id) {
        return reviewClient.getReviews().stream()
                .filter(review -> review.getBookId().equals(id))
                .collect(Collectors.toList());
    }

    @GetMapping("api/findBooksByGenre/{genre}/{page}")
    public List<Book> findBooksByGenre(@PathVariable("genre") int genreIndex, @PathVariable("page") int page) {
        Genre genre = Genre.valueOf(genreIndex);
        return bookService.findBooksByGenre(genre, page);
    }

    @PostMapping("api/addBook")
    public ResponseEntity<Void> addBook(@RequestBody Book book) {

        Book newBook = bookService.addBook(book);
        if (newBook == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("api/findBookById/{id}")
                .buildAndExpand(newBook.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value ="api/book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book updatedBook) {
        return ResponseEntity.ok(bookService.updateBook(id, updatedBook));
    }

    @GetMapping("api/usersAlsoRead/{id}")
    public List<Book> usersAlsoRead(@PathVariable Long id) {
        Set<Long> bookIds = new HashSet<>();

        userClient.getUsers().stream()
                .filter(user -> user.getBooksRead().contains(id))
                .forEach(user -> bookIds.addAll(user.getBooksRead()));
        bookIds.remove(id);

        List<Book> books = new ArrayList<>();
        for (Long bookId : bookIds) {
            books.add(bookService.findBookById(bookId));
        }
        return books;
    }

    @GetMapping("/configuration")
    public Map<String, String> getConfiguration(@Value("${api.page.size}") int pageSize) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Page size", String.valueOf(pageSize));
        return map;
    }

}
