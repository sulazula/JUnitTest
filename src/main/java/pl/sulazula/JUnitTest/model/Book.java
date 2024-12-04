package pl.sulazula.JUnitTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Book {

    private Long id;
    private String title;
    private String author;
    private int preferredPrice;

    public Book() {
    }

    public Book(Long id, String title, String author, int preferredPrice) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.preferredPrice = preferredPrice;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPreferredPrice() {
        return preferredPrice;
    }
}
