import static org.junit.jupiter.api.Assertions.*;

import com.motadata.MessagingApp;
import org.junit.jupiter.api.Test;

public class MessagingAppTests {

    @Test
    public void testMain() throws InterruptedException {
        // No need to mock objects here as it's a simple integration test
        MessagingApp.main(new String[0]);

        // You can assert on the final statistics logged by the QueueManager here (if needed)
        // However, directly testing the application logic is less recommended for unit testing.
    }
}