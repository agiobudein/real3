package real3;


import org.example.FraudDetection;
import org.example.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FraudDetectionTest {
    private FraudDetection fraudDetection;

    @BeforeEach
    void setUp() {
        fraudDetection = new FraudDetection();
    }

    @Test
    void testHighTransactionAmount() {
        // Simulate a user with a consistent average transaction amount
        for (int i = 0; i < 5; i++) {
            fraudDetection.processTransaction(new Transaction(1617906000 + i * 60, 100.00, "userTest", "serviceA"));
        }
        // Test a transaction significantly higher than the average
        assertThrows(RuntimeException.class, () -> {
            fraudDetection.processTransaction(new Transaction(1617906000 + 6 * 60, 600.00, "userTest", "serviceA"));
        });
    }

    @Test
    void testMultiServiceActivity() {
        // Simulate transactions across different services within a short time frame
        fraudDetection.processTransaction(new Transaction(1617906000, 100.00, "userTest2", "serviceA"));
        fraudDetection.processTransaction(new Transaction(1617906010, 200.00, "userTest2", "serviceB"));
        fraudDetection.processTransaction(new Transaction(1617906020, 150.00, "userTest2", "serviceC"));
        fraudDetection.processTransaction(new Transaction(1617906030, 180.00, "userTest2", "serviceD"));

        // Since we're not using a real alerting mechanism, you'd need to mock or check the state directly
        // to verify if an alert would have been generated
    }

    @Test
    void testPingPongActivity() {
        // Simulate ping-pong transactions
        fraudDetection.processTransaction(new Transaction(1617906000, 100.00, "userTest3", "serviceA"));
        fraudDetection.processTransaction(new Transaction(1617906010, 200.00, "userTest3", "serviceB"));
        fraudDetection.processTransaction(new Transaction(1617906020, 250.00, "userTest3", "serviceA"));

        // Similar to the multi-service activity test, verify if an alert would have been generated
    }
}
