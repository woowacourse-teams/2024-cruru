package com.cruru.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@DisplayName("Datasource Routing 테스트")
@ActiveProfiles("test")
@SpringBootTest(classes = DataSourceRouter.class)
class DataSourceRouterTest {

    private final DataSourceRouter dataSourceRouter = new DataSourceRouter();

    @Test
    void determineCurrentLookupKeyForReadOnlyTransaction() {
        // given
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(true);

        // when
        Object lookupKey = dataSourceRouter.determineCurrentLookupKey();

        // then
        assertThat(lookupKey).isEqualTo(DataSourceRouter.READ_DATASOURCE_KEY);
    }

    @Test
    void determineCurrentLookupKeyForReadWriteTransaction() {
        // give
        TransactionSynchronizationManager.setCurrentTransactionReadOnly(false);

        // when
        Object lookupKey = dataSourceRouter.determineCurrentLookupKey();

        // then
        assertThat(lookupKey).isEqualTo(DataSourceRouter.WRITE_DATASOURCE_KEY);
    }
}
