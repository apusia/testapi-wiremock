package org.example;

import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.FakerInstance.faker;

@Builder
public class User {
    public final int id;
    public final String name;
    public final String surname;
    public final List<BookPreview> books;

//    public static User generateUser(){
//        return generateUser();
//    }

    public static User generateUser(int id) {
        return User.builder()
                .id(id)
                .name(faker.name().firstName())
                .surname(faker.name().lastName())
                .books(BookPreview.generateBooks())
                .build();
    }

    public static List<User> generateUsers() {
        return IntStream.range(0, faker.number().numberBetween(1, 20))
                .mapToObj(User::generateUser)
                .collect(Collectors.toList());
    }

//    private static int generateBetween(int start, int end) {
//        return (int) (Math.random() * (end - start) + start);
//    }
}
