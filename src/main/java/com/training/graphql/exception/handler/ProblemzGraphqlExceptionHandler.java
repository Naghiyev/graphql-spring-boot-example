package com.training.graphql.exception.handler;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import com.training.graphql.exception.ProblemzAuthenticationException;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ProblemzGraphqlExceptionHandler implements DataFetcherExceptionHandler {

    private final DefaultDataFetcherExceptionHandler defaultDataFetcherExceptionHandler =
            new DefaultDataFetcherExceptionHandler();

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(
            DataFetcherExceptionHandlerParameters handlerParameters) {

        Throwable exception = handlerParameters.getException();

        // Walk the cause chain to see if a ProblemzAuthenticationException is present
        ProblemzAuthenticationException authEx = findCause(exception, ProblemzAuthenticationException.class);

        if (authEx != null) {
            var graphError = TypedGraphQLError.newBuilder()
                    .message(authEx.getMessage())
                    .errorType(ErrorType.UNAUTHENTICATED)
                    .errorDetail(new ProblemzErrorDetail())
                    .path(handlerParameters.getPath())
                    .build();

            var result = DataFetcherExceptionHandlerResult
                    .newResult()
                    .error(graphError)
                    .build();

            return CompletableFuture.completedFuture(result);
        }

        return defaultDataFetcherExceptionHandler.handleException(handlerParameters);
    }

    private <T extends Throwable> T findCause(Throwable throwable, Class<T> targetType) {
        while (throwable != null) {
            if (targetType.isInstance(throwable)) {
                return targetType.cast(throwable);
            }
            throwable = throwable.getCause();
        }
        return null;
    }
}
