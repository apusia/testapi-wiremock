package org.example;

import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.FakerInstance.faker;

@Builder()
public class BookDetailed {

    public final int id;
    public final String title;
    public final String author;
    public final String genre;
    public final String publisher;

    public static BookDetailed generateBook(int id) {
        return BookDetailed.builder()
                .id(id)
                .title(faker.book().title())
                .author(faker.book().author())
                .genre(faker.book().genre())
                .publisher(faker.book().publisher())
                .build();
    }

    public static List<BookDetailed> generateBooks() {
        return IntStream.range(0, faker.number().numberBetween(1, 10))
                .mapToObj(BookDetailed::generateBook)
                .collect(Collectors.toList());
    }

}
