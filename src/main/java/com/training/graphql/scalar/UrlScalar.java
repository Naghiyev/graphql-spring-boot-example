package com.training.graphql.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.net.MalformedURLException;
import java.net.URL;

@DgsScalar(name = "Url")
public class UrlScalar implements Coercing<URL, String> {

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof URL url) {
            return url.toString();
        }
        throw new CoercingSerializeException(
                "Expected a URL object but was: " + (dataFetcherResult == null ? "null" : dataFetcherResult.getClass())
        );
    }

    @Override
    public URL parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof String s) {
            try {
                return new URL(s);
            } catch (MalformedURLException e) {
                throw new CoercingParseValueException("Invalid URL value '" + s + "'", e);
            }
        }
        throw new CoercingParseValueException(
                "Expected a String for Url scalar but was: " + (input == null ? "null" : input.getClass())
        );
    }

    @Override
    public URL parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue stringValue) {
            try {
                return new URL(stringValue.getValue());
            } catch (MalformedURLException e) {
                throw new CoercingParseLiteralException("Invalid URL literal '" + stringValue.getValue() + "'", e);
            }
        }
        throw new CoercingParseLiteralException(
                "Expected AST type 'StringValue' for Url scalar but was: " + input
        );
    }
}

