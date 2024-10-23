package com.cruru.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    private static final String READ_DATASOURCE = "readDataSource";
    private static final String WRITE_DATASOURCE = "writeDataSource";
    private static final String ROUTE_DATASOURCE = "routeDataSource";

    @Bean
    @Primary
    @DependsOn(ROUTE_DATASOURCE)
    public DataSource defaultDataSource() {
        return new LazyConnectionDataSourceProxy(routeDataSource());
    }

    @Bean(name = ROUTE_DATASOURCE)
    @DependsOn({READ_DATASOURCE, WRITE_DATASOURCE})
    public DataSourceRouter routeDataSource() {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        DataSource writeDataSource = writeDataSource();
        DataSource readDataSource = readDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceRouter.READ_DATASOURCE_KEY, readDataSource);
        dataSourceMap.put(DataSourceRouter.WRITE_DATASOURCE_KEY, writeDataSource);
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource());
        return dataSourceRouter;
    }

    @Bean(name = WRITE_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource() {
        HikariDataSource build = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
        build.setMaximumPoolSize(5);
        return build;
    }

    @Bean(name = READ_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readDataSource() {
        HikariDataSource build = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
        build.setMaximumPoolSize(10);
        return build;
    }
}
