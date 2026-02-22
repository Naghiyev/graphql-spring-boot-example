package com.training.graphql.exception;

public class ProblemzAuthenticationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE =
            "User validation failed. Check that username & password combination match " +
                    "(both are case sensitive).";

    public ProblemzAuthenticationException() {
        super(DEFAULT_MESSAGE);
    }

    public ProblemzAuthenticationException(String message) {
        super(message);
    }

    public ProblemzAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
