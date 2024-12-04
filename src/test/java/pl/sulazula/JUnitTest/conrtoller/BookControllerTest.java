package pl.sulazula.JUnitTest.conrtoller;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sulazula.JUnitTest.controller.BookController;
import pl.sulazula.JUnitTest.model.Book;
import pl.sulazula.JUnitTest.reposiotry.BookRepo;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookRepo bookRepo;

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        book1 = new Book(1L, "test1", "testauthor", 22);
        book2 = new Book(2L, "test2", "testauthor", 23);
        book3 = new Book(3L, "test3", "testauthor", 24);
    }

    @Test
    void shouldReturnAllBooks() throws Exception {
        Mockito.when(bookRepo.getBooks()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("test1"))
                .andExpect(jsonPath("$[1].title").value("test2"));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        Mockito.when(bookRepo.getById(1L)).thenReturn(Optional.of(book1));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void shouldSaveBook() throws Exception {
        Mockito.when(bookRepo.checkClones("test3")).thenReturn(true);
        Mockito.when(bookRepo.save(any(Book.class))).thenReturn(book3);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"test3\", \"author\": \"author3\", \"preferredPrice\": 24}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test3"))
                .andExpect(jsonPath("$.author").value("testauthor"))
                .andExpect(jsonPath("$.preferredPrice").value(24));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        Mockito.when(bookRepo.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }
}
