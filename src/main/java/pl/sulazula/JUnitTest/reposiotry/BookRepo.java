package pl.sulazula.JUnitTest.reposiotry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.sulazula.JUnitTest.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepo {
    @Autowired
    private List<Book> books;

    public List<Book> getBooks() {

        return books;
    }

    public Optional<Book> getById(Long id) {

        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    public Book save(Book book) {
        books.add(book);

        return book;
    }

    public boolean deleteById(Long id) {

        return books.removeIf(b -> b.getId().equals(id));
    }

    public boolean checkClones(String name) {
        for (Book book : books) {
            if (book.getTitle().equals(name)) {
                return true;
            }
        }

        return false;
    }
}

