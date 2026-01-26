package com.training.graphql.component.fake.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.training.graphql.generated.DgsConstants;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@DgsComponent
public class FakeAdditionalOnRequestDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.AdditionalOnRequest)
    public String additionalOnRequest(
            @RequestHeader(name = "optionalHeader", required = false) String optionalHeader,
            @RequestHeader(name = "mandatoryHeader") String mandatoryHeader,
            @RequestParam(name = "optionalParam", required = false) String optionalParam,
            @RequestParam(name = "mandatoryParam") String mandatoryParam
    ) {
        var sb = new StringBuilder();

        sb.append("Optional header : " + optionalHeader);
        sb.append(", ");
        sb.append("Mandatory header : " + mandatoryHeader);
        sb.append(", ");
        sb.append("Optional param : " + optionalParam);
        sb.append(", ");
        sb.append("Mandatory param : " + mandatoryParam);

        return sb.toString();
    }

}
