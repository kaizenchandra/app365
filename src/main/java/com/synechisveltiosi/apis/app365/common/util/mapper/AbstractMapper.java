package com.synechisveltiosi.apis.app365.common.util.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<From, To> implements Mapper<From, To> {

    @Override
    public List<To> map(List<From> from) {
        if (from == null)
            return null;

        List<To> result = new ArrayList<>();
        for (From item : from)
            result.add(map(item));

        return result;
    }

    @Override
    public List<To> map(From[] from) {
        if (from == null)
            return null;

        List<To> result = new ArrayList<>();
        for (From item : from)
            result.add(map(item));

        return result;
    }
}
