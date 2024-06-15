package com.motadata;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessagingApp {
    public static void main(String[] args) {
        // Create a QueueManager instance with the desired capacity
        QueueManager queueManager = new QueueManager(10);

        // Create Producer and Consumer instances
        Producer producer = new Producer(queueManager);
        Consumer consumer = new Consumer(queueManager);

        // Create threads for Producer and Consumer
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        // Start the threads
        producerThread.start();
        consumerThread.start();

        try {
            // Wait for the threads to finish
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.getLogger(MessagingApp.class.getName()).log(Level.SEVERE, "Thread interrupted", e);
        }

        // Log the final statistics
        queueManager.logStats();
    }
}