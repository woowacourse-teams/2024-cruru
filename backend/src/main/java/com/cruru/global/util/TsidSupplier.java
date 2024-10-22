package com.cruru.global.util;

import io.hypersistence.tsid.TSID.Factory;
import java.util.function.Supplier;

public class TsidSupplier implements Supplier<Factory> {

    @Override
    public Factory get() {
        return Factory.builder()
                .withNodeBits(10)
                .build();
    }
}
