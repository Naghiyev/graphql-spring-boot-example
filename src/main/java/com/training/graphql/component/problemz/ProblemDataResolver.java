package com.training.graphql.component.problemz;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.training.graphql.exception.ProblemzAuthenticationException;
import com.training.graphql.generated.DgsConstants;
import com.training.graphql.generated.types.Problem;
import com.training.graphql.generated.types.ProblemCreateInput;
import com.training.graphql.generated.types.ProblemResponse;
import com.training.graphql.service.command.ProblemzCommandService;
import com.training.graphql.service.query.ProblemzQueryService;
import com.training.graphql.service.query.UserzQueryService;
import com.training.graphql.util.GraphqlBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
public class ProblemDataResolver {

    @Autowired
    private ProblemzQueryService queryService;

    @Autowired
    private ProblemzCommandService commandService;

    @Autowired
    private UserzQueryService userzQueryService;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ProblemLatestList)
    public List<Problem> getProblemLatestList() {
        return queryService.problemzLatestList().stream().map(GraphqlBeanMapper::mapToGraphql)
                .collect(Collectors.toList());
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ProblemDetail)
    public Problem getProblemDetail(@InputArgument(name = "id") String problemId) {
        var problemzId = UUID.fromString(problemId);
        var problemz = queryService.problemzDetail(problemzId)
                .orElseThrow(RuntimeException::new);

        return GraphqlBeanMapper.mapToGraphql(problemz);
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.ProblemCreate)
    public ProblemResponse createProblem(
            @RequestHeader(name = "authToken", required = true) String authToken,
            @InputArgument(name = "problem") ProblemCreateInput problemCreateInput) {
        var userz = userzQueryService.findUserzByAuthToken(authToken)
                .orElseThrow(ProblemzAuthenticationException::new);
        var problemz = GraphqlBeanMapper.mapToEntity(problemCreateInput, userz);
        var created = commandService.createProblemz(problemz);

        return ProblemResponse.newBuilder().problem(GraphqlBeanMapper.mapToGraphql(created)).build();
    }

    @DgsData(parentType = DgsConstants.SUBSCRIPTION_TYPE, field = DgsConstants.SUBSCRIPTION.ProblemAdded)
    public Flux<Problem> subscribeProblemAdded() {
        return commandService.problemzFlux().map(GraphqlBeanMapper::mapToGraphql);
    }

}
