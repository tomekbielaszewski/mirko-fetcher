package pl.grizwold.mirkofetcher;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import pl.grizwold.mirkofetcher.config.Configuration;
import pl.grizwold.mirkofetcher.config.ConfigurationParser;
import pl.grizwold.mirkofetcher.service.MicroblogFetcher;

import java.util.concurrent.*;

@Log4j
@SpringBootApplication
public class MicroblogFetcherApplication {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Configuration configuration = new ConfigurationParser().parse(args);

        ConfigurableApplicationContext context = SpringApplication.run(MicroblogFetcherApplication.class, args);
        MicroblogFetcher fetcher = context.getBean(MicroblogFetcher.class);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            executor.submit(() -> fetcher.run(configuration)).get(1, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            log.warn("Service timed out after 10 minutes. Closing");
            System.exit(0);
        }
        executor.shutdown();
    }

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }
}
