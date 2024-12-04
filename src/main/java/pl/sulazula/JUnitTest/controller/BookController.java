package pl.sulazula.JUnitTest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sulazula.JUnitTest.model.Book;
import pl.sulazula.JUnitTest.reposiotry.BookRepo;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepo br;

    public BookController(BookRepo br) {
        this.br = br;
    }

    @GetMapping
    public List<Book> getAllBooks() {

        return br.getBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = br.getById(id).orElse(null);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @PostMapping
    public <T> Object saveBook(@RequestBody Book book) {
        if (!br.checkClones(book.getTitle())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book title already exists");
        }

        Book savedBook = br.save(book);

        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (!br.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book id not found or server error");
        }

        return ResponseEntity.ok().build();
    }
}
