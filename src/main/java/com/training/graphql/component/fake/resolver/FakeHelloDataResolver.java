package com.training.graphql.component.fake.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.training.graphql.datasource.fake.FakeHelloDataSource;
import com.training.graphql.generated.types.Hello;

import java.util.List;

@DgsComponent
public class FakeHelloDataResolver {

    @DgsQuery
    public List<Hello> allHelloes() {
        return FakeHelloDataSource.HELLOS;
    }


    @DgsQuery
    public Hello oneHello() {
        return FakeHelloDataSource.HELLOS.stream().findFirst().orElse(null);
    }

    @DgsData(parentType = "Query", field = "oneHelloById")
    public Hello oneHelloById(@InputArgument("id") Integer id) {
        return FakeHelloDataSource.HELLOS.stream().filter(
                hello -> hello.getRandomNumber().equals(id)
        )
                .findFirst().orElse(null);
    }
}
