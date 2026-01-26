package com.training.graphql.scalar;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.IntValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.math.BigInteger;

@DgsScalar(name = "NonNegativeInt")
public class NonNegativeIntScalar implements Coercing<Integer, Integer> {

    @Override
    public Integer serialize(Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof Integer i && i >= 0) {
            return i;
        }
        throw new CoercingSerializeException(
                "NonNegativeInt must be a non-negative Integer, but was: " + dataFetcherResult
        );
    }

    @Override
    public Integer parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof Integer i && i >= 0) {
            return i;
        }
        if (input instanceof Number n) {
            int value = n.intValue();
            if (value >= 0) {
                return value;
            }
        }
        throw new CoercingParseValueException(
                "NonNegativeInt must be a non-negative integer value, but was: " + input
        );
    }

    @Override
    public Integer parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof IntValue intValue) {
            BigInteger value = intValue.getValue();
            if (value.signum() >= 0) {
                return value.intValue();
            }
        }
        throw new CoercingParseLiteralException(
                "NonNegativeInt literal must be a non-negative IntValue, but was: " + input
        );
    }
}

