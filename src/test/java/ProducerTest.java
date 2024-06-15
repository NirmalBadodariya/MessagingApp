import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.motadata.Producer;
import com.motadata.QueueManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProducerTest {

    @Mock
    private QueueManager queueManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRunSuccess() throws InterruptedException {
        Producer producer = new Producer(queueManager);

        // Just run the producer without mocking the produce method
        producer.run();

        // Verify 20 messages produced
        verify(queueManager, times(20)).produce(anyString());
    }

    @Test
    public void testRunInterrupted() throws InterruptedException {
        Producer producer = new Producer(queueManager);
        Thread producerThread = new Thread(producer);

        doThrow(new InterruptedException()).when(queueManager).produce(anyString());

        producerThread.start();
        producerThread.interrupt();
        producerThread.join();

        // Verify only 1 attempt before interruption
        verify(queueManager, atLeastOnce()).produce(anyString());
    }
}
