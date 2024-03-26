package org.example;
import java.util.*;

public class Transaction {
    long timestamp;
    double amount;
    String userID;
    String serviceID;

    public Transaction(long timestamp, double amount, String userID, String serviceID) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.userID = userID;
        this.serviceID = serviceID;
    }
}






