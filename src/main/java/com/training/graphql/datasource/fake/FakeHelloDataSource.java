package com.training.graphql.datasource.fake;

import com.netflix.graphql.dgs.DgsComponent;

import com.training.graphql.generated.types.Hello;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DgsComponent
public class FakeHelloDataSource {

    @Autowired
    private Faker faker;

    public static final List<Hello> HELLOS = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 10; i++) {
            var hello = Hello.newBuilder()
                    .randomNumber(faker.random().nextInt(5000))
                    .text(faker.company().name())
                    .build();

            HELLOS.add(hello);
        }
    }




}
