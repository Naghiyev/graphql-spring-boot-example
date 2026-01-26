package com.training.graphql.datasource.fake;

import com.training.graphql.generated.types.Stock;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class FakeSubscriptionDataSource {
    private final Faker faker;

    public Stock randomStock(){

        return Stock.newBuilder()
                .price(faker.random().nextInt(100, 1000))
                .symbol(faker.stock().nyseSymbol())
                .lastTradeDateTime(LocalDateTime.now())
                .build();

    }
}
