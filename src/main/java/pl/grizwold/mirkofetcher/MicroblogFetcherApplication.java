package pl.grizwold.mirkofetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
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

    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }
}
