package com.motadata;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

// Class Manages queue
public class QueueManager {
    private static final Logger logger = Logger.getLogger(QueueManager.class.getName());
    // Using LinkedList to demonstrate Threading properly
    public final Queue<String> queue = new LinkedList<>();
    public final int capacity;
    private int successfulMessages = 0;
    private int errorMessages = 0;

    public QueueManager(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(String message) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.offer(message);
        logger.info("Produced: " + message);
        notifyAll();
    }

    public synchronized String consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        String message = queue.poll();
        notifyAll();
        return message;
    }

    public synchronized void incrementSuccessCount() {
        successfulMessages++;
    }

    public synchronized void incrementErrorCount() {
        errorMessages++;
    }

    public void logStats() {
        logger.info("Total messages processed successfully: " + successfulMessages);
        logger.info("Total error messages: " + errorMessages);
    }

    public int getSuccessfulMessages() {
        return successfulMessages;
    }

    public int getErrorMessages() {
        return errorMessages;
    }
}