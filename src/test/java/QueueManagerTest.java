import static org.junit.jupiter.api.Assertions.*;

import com.motadata.QueueManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueueManagerTest {

    private QueueManager queueManager;

    @BeforeEach
    public void setup() {
        queueManager = new QueueManager(5);
    }

    @Test
    public void testProduceSuccess() throws InterruptedException {
        String message = "Test Message";
        queueManager.produce(message);

        assertEquals(1, queueManager.queue.size(), "Queue size should be 1 after producing");
        assertTrue(queueManager.queue.contains(message), "Queue should contain the produced message");
    }

    @Test
    public void testProduceFullCapacity() throws InterruptedException {
        for (int i = 0; i < queueManager.capacity; i++) {
            queueManager.produce("Message-" + i);
        }

        assertThrows(InterruptedException.class, () -> queueManager.produce("Overflow Message"));
    }

    @Test
    public void testConsumeSuccess() throws InterruptedException {
        queueManager.produce("Test Message");

        String message = queueManager.consume();

        assertEquals("Test Message", message, "Consumed message should be the same as produced");
        assertEquals(0, queueManager.queue.size(), "Queue should be empty after consuming");
    }

    @Test
    public void testConsumeEmptyQueue() throws InterruptedException {
        assertThrows(InterruptedException.class, queueManager::consume);
    }

    @Test
    public void testIncrementSuccessCount() {
        queueManager.incrementSuccessCount();

        assertEquals(1, queueManager.getSuccessfulMessages(), "Successful message count should be incremented");
    }

    @Test
    public void testIncrementErrorCount() {
        queueManager.incrementErrorCount();

        assertEquals(1, queueManager.getErrorMessages(), "Error message count should be incremented");
    }
}
