package org.example.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiClientPoolTest {
    @Test
    void isSingleton() {
        ApiClientPool instance1 = ApiClientPool.getInstance();
        ApiClientPool instance2 = ApiClientPool.getInstance();
        assertSame(instance1, instance2);
    }
}
