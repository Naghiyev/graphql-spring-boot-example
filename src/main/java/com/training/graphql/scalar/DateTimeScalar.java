package com.training.graphql.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@DgsScalar(name = "DateTime")
public class DateTimeScalar implements Coercing<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof LocalDateTime dateTime) {
            return FORMATTER.format(dateTime);
        }
        throw new CoercingSerializeException(
                "Expected a LocalDateTime object but was: " + (dataFetcherResult == null ? "null" : dataFetcherResult.getClass())
        );
    }

    @Override
    public LocalDateTime parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof String s) {
            try {
                return LocalDateTime.parse(s, FORMATTER);
            } catch (DateTimeParseException ex) {
                throw new CoercingParseValueException("Invalid ISO-8601 datetime value '" + s + "'", ex);
            }
        }
        throw new CoercingParseValueException(
                "Expected a String for DateTime scalar but was: " + (input == null ? "null" : input.getClass())
        );
    }

    @Override
    public LocalDateTime parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue stringValue) {
            try {
                return LocalDateTime.parse(stringValue.getValue(), FORMATTER);
            } catch (DateTimeParseException ex) {
                throw new CoercingParseLiteralException("Invalid ISO-8601 datetime literal '" + stringValue.getValue() + "'", ex);
            }
        }
        throw new CoercingParseLiteralException(
                "Expected AST type 'StringValue' for DateTime scalar but was: " + input
        );
    }
}

