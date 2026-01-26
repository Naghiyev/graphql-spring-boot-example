package com.training.graphql.component.fake.resolver;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.training.graphql.datasource.fake.FakeBookDataSource;
import com.training.graphql.datasource.fake.FakeHelloDataSource;
import com.training.graphql.generated.DgsConstants;
import com.training.graphql.generated.types.SmartSearchResult;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakeSmartSearchDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.SmartSearch)
    public List<SmartSearchResult> getSmartSearch(@InputArgument(name = "keyword") Optional<String> keyword) {
        var smartSearchList = new ArrayList<SmartSearchResult>();

        if (keyword.isEmpty()) {
            smartSearchList.addAll(FakeHelloDataSource.HELLOS);
            smartSearchList.addAll(FakeBookDataSource.BOOKS);
        } else {
            var keywordString = keyword.get();

            FakeHelloDataSource.HELLOS.stream().filter(
                    h -> StringUtils.containsIgnoreCase(h.getText(), keywordString)
            ).forEach(smartSearchList::add);

            FakeBookDataSource.BOOKS.stream().filter(
                    b -> StringUtils.containsIgnoreCase(b.getTitle(), keywordString)
            ).forEach(smartSearchList::add);
        }

        return smartSearchList;
    }

}
