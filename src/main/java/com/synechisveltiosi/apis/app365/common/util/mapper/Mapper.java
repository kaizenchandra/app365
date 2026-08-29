package com.synechisveltiosi.apis.app365.common.util.mapper;

import java.util.List;

public interface Mapper<From, To> {

    To map(From from);

    List<To> map(List<From> from);

    List<To> map(From[] from);
}
