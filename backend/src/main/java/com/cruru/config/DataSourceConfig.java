package com.cruru.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readeDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @DependsOn({"readDataSource", "writeDataSource"})
    public DataSourceRouter routeDataSource() {
        DataSourceRouter router = new DataSourceRouter();
        DataSource wrtieDataSource = writeDataSource();
        DataSource readeDataSource = readeDataSource();

        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceRouter.READ_DATASOURCE_KEY, readeDataSource);
        dataSourceMap.put(DataSourceRouter.WRITE_DATASOURCE_KEY, wrtieDataSource);
        router.setTargetDataSources(dataSourceMap);
        router.setDefaultTargetDataSource(readeDataSource);
        return router;
    }
}
