package com.cruru.util;

import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(excludeAutoConfiguration = {FlywayAutoConfiguration.class})
public class RepositoryTest {

}
