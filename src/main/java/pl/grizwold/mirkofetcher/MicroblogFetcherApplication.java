package pl.grizwold.mirkofetcher;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.grizwold.mirkofetcher.service.MicroblogFetcher;

import java.util.Date;
import java.util.Optional;

@SpringBootApplication
public class MicroblogFetcherApplication {
	private final static long HOUR = 1000 * 60 * 60;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MicroblogFetcherApplication.class, args);
		MicroblogFetcher fetcher = context.getBean(MicroblogFetcher.class);

		long timeOffset = getUntilTimestamp(args);

		fetcher.run(System.currentTimeMillis() - timeOffset);
	}

	private static long getUntilTimestamp(String[] args) {
		if(args == null || args.length == 0 || !StringUtils.isNumeric(args[0])) {
			throw new IllegalArgumentException("How old entries you want? Provide amount of hours of Microblog history to fetch");
		}
		String timeOffsetStr = args[0];
		return Long.valueOf(timeOffsetStr) * HOUR;
	}

}
