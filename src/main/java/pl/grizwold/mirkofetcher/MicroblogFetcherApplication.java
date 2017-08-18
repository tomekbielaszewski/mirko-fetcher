package pl.grizwold.mirkofetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.grizwold.mirkofetcher.config.Configuration;
import pl.grizwold.mirkofetcher.config.ConfigurationParser;
import pl.grizwold.mirkofetcher.service.MicroblogFetcher;

@SpringBootApplication
public class MicroblogFetcherApplication {
    public static void main(String[] args) {
        Configuration configuration = new ConfigurationParser().parse(args);

        ConfigurableApplicationContext context = SpringApplication.run(MicroblogFetcherApplication.class, args);
        MicroblogFetcher fetcher = context.getBean(MicroblogFetcher.class);

        fetcher.run(configuration);
    }
}
