package producer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {

        ScheduledExecutorService kafkaProducerScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        //run producer after every 10 seconds
        kafkaProducerScheduledExecutor.scheduleWithFixedDelay(new Producer(), 5, 10, TimeUnit.SECONDS);
        LOGGER.info("Kafka producer triggered");

    }
}

