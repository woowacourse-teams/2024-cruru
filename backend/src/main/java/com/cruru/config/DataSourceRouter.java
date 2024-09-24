package com.cruru.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    public static final String READ_DATASOURCE_KEY = "read";
    public static final String WRITE_DATASOURCE_KEY = "write";

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return READ_DATASOURCE_KEY;
        }
        return WRITE_DATASOURCE_KEY;
    }
}
