package com.training.graphql.resolver;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
                "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
public class FakeHelloDataResolver {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;


    @Test
    public void testHello(){
        var graphqlQuery = """
                {
                  oneHello {
                    text
                    randomNumber
                  }
                }
                """;

        String text = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery,"data.oneHello.text");
        Integer randomNumber = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery,"data.oneHello.randomNumber");

        assertFalse(StringUtils.isBlank(text));
        assertNotNull(randomNumber);

    }


    @Test
    public void testAllHello(){
        var graphqlQuery = """
                {
                    allHelloes{
                    randomNumber
                    text
                  }
                }
                """;

        List<String> texts = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery,"data.allHelloes[*].text");
        List<Integer> randomNumbers = dgsQueryExecutor.executeAndExtractJsonPath(graphqlQuery,"data.allHelloes[*].randomNumber");

        assertNotNull(texts);
        assertFalse(texts.isEmpty());

        assertNotNull(randomNumbers);
        assertFalse(randomNumbers.isEmpty());

    }
}
