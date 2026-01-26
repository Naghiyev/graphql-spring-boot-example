package com.training.graphql.component.fake.mutation;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.training.graphql.datasource.fake.FakeHelloDataSource;
import com.training.graphql.generated.DgsConstants;
import com.training.graphql.generated.types.Hello;
import com.training.graphql.generated.types.HelloInput;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class FakeHelloMutation {


//      @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddHello)
    @DgsMutation
    public int addHello(@InputArgument(name = "helloInput") HelloInput helloInput) {
        var hello = Hello.newBuilder().text(helloInput.getText())
                .randomNumber(helloInput.getNumber()).build();

        FakeHelloDataSource.HELLOS.add(hello);

        return FakeHelloDataSource.HELLOS.size();
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.ReplaceHelloText)
    public List<Hello> replaceHelloText(@InputArgument(name = "helloInput") HelloInput helloInput) {
        FakeHelloDataSource.HELLOS.stream().filter(
                h -> h.getRandomNumber() == helloInput.getNumber()
        ).forEach(h -> h.setText(helloInput.getText()));


        return  FakeHelloDataSource.HELLOS.stream()
                .filter(hello -> hello.getRandomNumber() == helloInput.getNumber())
                .collect(Collectors.toList());
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.DeleteHello)
    public int deleteHello(int number) {
        FakeHelloDataSource.HELLOS.removeIf(h -> h.getRandomNumber() == number);

        return FakeHelloDataSource.HELLOS.size();
    }
}
