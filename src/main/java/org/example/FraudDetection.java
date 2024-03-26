package org.example;

import java.util.*;

public class FraudDetection {
    // Stores the last transactions for each user to check for ping-pong activity
    Map<String, LinkedList<Transaction>> userTransactions = new HashMap<>();
    // Stores the sum of transaction amounts and count for each user to calculate the average
    Map<String, double[]> userAmounts = new HashMap<>();
    // Stores the services each user has interacted with in the last 5 minutes
    Map<String, Set<String>> userServices = new HashMap<>();

    public void processTransaction(Transaction transaction) {
        // Update the average transaction amount for the user
        updateAverageAmount(transaction);

        // Check for multi-service activity
        checkMultiServiceActivity(transaction);

        // Check for high transaction amount
        checkHighTransactionAmount(transaction);

        // Check for ping-pong activity
        checkPingPongActivity(transaction);
    }

   public void updateAverageAmount(Transaction transaction) {
        double[] amounts = userAmounts.getOrDefault(transaction.userID, new double[2]);
        amounts[0] += transaction.amount; // Sum of amounts
        amounts[1] += 1; // Count of transactions
        userAmounts.put(transaction.userID, amounts);
    }

   public void checkMultiServiceActivity(Transaction transaction) {
        Set<String> services = userServices.getOrDefault(transaction.userID, new HashSet<>());
        services.add(transaction.serviceID);
        userServices.put(transaction.userID, services);

        if (services.size() > 3) {
            System.out.println("Alert: User " + transaction.userID + " used more than 3 distinct services within 5 minutes.");
        }
    }

   public void checkHighTransactionAmount(Transaction transaction) {
        double[] amounts = userAmounts.get(transaction.userID);
        double averageAmount = amounts[0] / amounts[1];

        if (transaction.amount > 5 * averageAmount) {
            System.out.println("Alert: Transaction amount for user " + transaction.userID + " is significantly higher than their average.");
        }
    }

    public void checkPingPongActivity(Transaction transaction) {
        LinkedList<Transaction> transactions = userTransactions.getOrDefault(transaction.userID, new LinkedList<>());
        if (!transactions.isEmpty() && transactions.getLast().serviceID.equals(transaction.serviceID)) {
            System.out.println("Alert: Ping-pong activity detected for user " + transaction.userID);
        }

        transactions.add(transaction);
        if (transactions.size() > 2) transactions.removeFirst();
        userTransactions.put(transaction.userID, transactions);
    }
}

