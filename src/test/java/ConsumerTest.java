import static org.mockito.Mockito.*;

import com.motadata.Consumer;
import com.motadata.QueueManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ConsumerTest {

    @Mock
    private QueueManager queueManager;

    @Test
    public void testRunSuccess() throws InterruptedException {
        Consumer consumer = new Consumer(queueManager);

        when(queueManager.consume()).thenReturn("Test Message");
        doThrow(new RuntimeException("Simulated processing error")).when(queueManager).incrementErrorCount();

        consumer.run();

        verify(queueManager, times(1)).consume(); // Verify 1 message consumed
        verify(queueManager, times(9)).incrementSuccessCount(); // Verify 9 successful processes (90% rate)
        verify(queueManager, times(1)).incrementErrorCount(); // Verify 1 error handling
    }

    @Test
    public void testRunInterrupted() throws InterruptedException {
        Consumer consumer = new Consumer(queueManager);
        Thread consumerThread = new Thread(consumer);

        when(queueManager.consume()).thenThrow(new InterruptedException());

        consumerThread.start();
        consumerThread.interrupt();
        consumerThread.join();

        verify(queueManager, times(1)).consume(); // Verify 1 attempt to consume before interruption
    }
}
