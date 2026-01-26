package com.training.graphql.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@DgsScalar(name = "Date")
public class DateScalar implements Coercing<LocalDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof LocalDate localDate) {
            return FORMATTER.format(localDate);
        }
        throw new CoercingSerializeException(
                "Expected a LocalDate object but was: " + (dataFetcherResult == null ? "null" : dataFetcherResult.getClass())
        );
    }

    @Override
    public LocalDate parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof String s) {
            try {
                return LocalDate.parse(s, FORMATTER);
            } catch (DateTimeParseException ex) {
                throw new CoercingParseValueException("Invalid ISO-8601 date value '" + s + "'", ex);
            }
        }
        throw new CoercingParseValueException(
                "Expected a String for Date scalar but was: " + (input == null ? "null" : input.getClass())
        );
    }

    @Override
    public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue stringValue) {
            try {
                return LocalDate.parse(stringValue.getValue(), FORMATTER);
            } catch (DateTimeParseException ex) {
                throw new CoercingParseLiteralException("Invalid ISO-8601 date literal '" + stringValue.getValue() + "'", ex);
            }
        }
        throw new CoercingParseLiteralException(
                "Expected AST type 'StringValue' for Date scalar but was: " + input
        );
    }
}

