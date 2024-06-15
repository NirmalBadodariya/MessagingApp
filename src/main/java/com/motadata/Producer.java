package com.motadata;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {
    private static final Logger logger = Logger.getLogger(Producer.class.getName());
    private final QueueManager queueManager;

    public Producer(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public void run() {
        // Produces messages through loop
        for (int i = 1; i <= 20; i++) {
            try {
                queueManager.produce("Message-" + i);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.SEVERE, "Producer interrupted", e);
            }
        }
    }
}
