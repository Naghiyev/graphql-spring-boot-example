package com.training.graphql.component.fake.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.training.graphql.datasource.fake.FakeBookDataSource;
import com.training.graphql.generated.DgsConstants;
import com.training.graphql.generated.types.Book;
import com.training.graphql.generated.types.ReleaseHistory;
import com.training.graphql.generated.types.ReleaseHistoryInput;
import graphql.schema.DataFetchingEnvironment;
import io.micrometer.common.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DgsComponent
public class FakeBookDataResolver {

    @DgsData(parentType = "Query", field = "books")
    public List<Book> booksWrittenBy(@InputArgument(name = "author") String authorName){

        if(StringUtils.isBlank(authorName)) return FakeBookDataSource.BOOKS;

        return FakeBookDataSource.BOOKS.stream()
                .filter(book -> book.getAuthor().getName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
    }

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.BooksByReleased
    )
    public List<Book> getBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {

        var argumentMap =(Map<String, Object>) dataFetchingEnvironment.getArgument(DgsConstants.QUERY.BOOKSBYRELEASED_INPUT_ARGUMENT.ReleasedInput);
        var releasedInput = ReleaseHistoryInput.newBuilder().
                printEdition((Boolean) argumentMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintEdition))
                .year((Integer) argumentMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                .build();

        return FakeBookDataSource.BOOKS.stream().filter(book -> matchReleaseHistory(releasedInput,book.getReleased())).collect(Collectors.toList());
    }

    private boolean matchReleaseHistory(ReleaseHistoryInput releaseHistoryInput, ReleaseHistory releaseHistory){
        return releaseHistoryInput.getYear() == releaseHistory.getYear() && releaseHistoryInput.getPrintEdition() == releaseHistory.getPrintEdition();
    }

}
