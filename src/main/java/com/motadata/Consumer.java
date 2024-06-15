package com.motadata;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {
    private static final Logger logger = Logger.getLogger(Consumer.class.getName());
    private final QueueManager queueManager;

    public Consumer(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = queueManager.consume();
                processMessage(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Consumer interrupted", e);
            }
        }
    }

    private void processMessage(String message) {
        try {
            // Simulate processing with random success and failure
            if (Math.random() > 0.1) {  // 90% success rate
                queueManager.incrementSuccessCount();
                logger.info("Consumed successfully: " + message);
            } else {
                throw new Exception("Processing error");
            }
        } catch (Exception e) {
            queueManager.incrementErrorCount();
            logger.log(Level.SEVERE, "Error processing message: " + message, e);
        }
    }
}