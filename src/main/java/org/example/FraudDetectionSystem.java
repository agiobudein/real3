package org.example;

import java.util.List;

public class FraudDetectionSystem {
    public static void main(String[] args) {
        FraudDetection fraudDetection = new FraudDetection();

        // Example transactions that would be streamed in real-time
        List<Transaction> transactions = List.of(
                new Transaction(1617906000, 150.00, "user1", "serviceA"),
                new Transaction(1617906060, 4500.00, "user2", "serviceB"),
                new Transaction(1617906120, 75.00, "user1", "serviceC")
                // Additional transactions for testing...
        );

        for (Transaction transaction : transactions) {
            fraudDetection.processTransaction(transaction);
        }
    }
}
