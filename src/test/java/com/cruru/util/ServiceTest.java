package com.cruru.util;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
public class ServiceTest {

    @Autowired
    private DbCleaner dbCleaner;

    @BeforeEach
    void resetDb() {
        dbCleaner.truncateEveryTable();
    }
}
