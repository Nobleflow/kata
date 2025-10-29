package com.crafteam.kata;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Basic integration test for the Kata application.
 * 
 * Verifies that the Spring application context loads successfully
 * with all configured beans and dependencies.
 */
@SpringBootTest
@ActiveProfiles("test")
class KataApplicationTests {

    /**
     * Tests that the application context loads without errors.
     */
    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
    }
}
