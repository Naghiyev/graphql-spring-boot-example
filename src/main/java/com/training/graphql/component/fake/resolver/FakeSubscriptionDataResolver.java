package com.training.graphql.component.fake.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.training.graphql.datasource.fake.FakeSubscriptionDataSource;
import com.training.graphql.generated.DgsConstants;
import com.training.graphql.generated.types.Stock;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.time.Duration;

@DgsComponent
public class FakeSubscriptionDataResolver {

    @Autowired
    private FakeSubscriptionDataSource fakeSubscriptionDataSource;

    @DgsSubscription(field = DgsConstants.SUBSCRIPTION.RandomStock)
    public Publisher<Stock> randomStock() {
        return Flux.interval(Duration.ofSeconds(3))
                .map(t -> fakeSubscriptionDataSource.randomStock());
    }

}
