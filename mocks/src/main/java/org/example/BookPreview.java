package org.example;

import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.FakerInstance.faker;

@Builder
public class BookPreview {

    public final int id;
    public final String title;

    public static BookPreview generateBook(int id){
        return BookPreview.builder()
                .id(id)
                .title(faker.book().title())
                .build();
    }

    public static List<BookPreview> generateBooks() {
        return IntStream.range(0, faker.number().numberBetween(5, 10))
                .mapToObj(BookPreview::generateBook)
                .collect(Collectors.toList());
    }
}
