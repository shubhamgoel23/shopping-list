package com.example.shoppinglist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles({"test", "liquibase"})
class ContainerTest {

    @Test
    void shouldFindAll() {
        assertEquals(1l, 1l);
    }

}
