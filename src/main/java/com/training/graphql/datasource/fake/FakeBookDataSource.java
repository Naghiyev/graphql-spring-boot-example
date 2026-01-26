package com.training.graphql.datasource.fake;

import com.training.graphql.generated.types.Address;
import com.training.graphql.generated.types.Author;
import com.training.graphql.generated.types.Book;
import com.training.graphql.generated.types.ReleaseHistory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@RequiredArgsConstructor
public class FakeBookDataSource {
    private final Faker faker;
    public static  final List<Book> BOOKS = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 20; i++) {
            var addresses = new ArrayList<Address>();
            var author = Author.newBuilder().addresses(addresses)
                    .name(faker.book().author())
                    .originCountry(faker.country().name())
                    .build();
            var released = ReleaseHistory.newBuilder()
                    .printEdition(faker.bool().bool())
                    .releasedCountry(faker.country().name())
                    .year(faker.number().numberBetween(2019, 2021))
                    .build();
            var book = Book.newBuilder().author(author)
                    .publisher(faker.book().publisher())
                    .title(faker.book().title())
                    .released(released)
                    .build();

            for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, 3); j++) {
                var address = Address.newBuilder()
                        .country(faker.address().country())
                        .city(faker.address().cityName())
                        .country(faker.address().country())
                        .street(faker.address().streetAddress())
                        .zipCode(faker.address().zipCode())
                        .build();

                addresses.add(address);
            }

            BOOKS.add(book);
        }
    }



}
