package pl.grizwold.mirkofetcher.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grizwold.microblog.model.Entry;
import pl.grizwold.mirkofetcher.config.Configuration;
import pl.grizwold.mirkofetcher.model.repo.EntryRepository;
import pl.grizwold.mirkofetcher.wykop.client.MicroblogClient;

import java.util.List;

@Slf4j
@Service
public class MicroblogFetcher {
    @Autowired
    private MicroblogClient microblog;

    @Autowired
    private EntryRepository entryRepository;

    public void run(Configuration configuration) {
        DateTime timeBoundary = DateTime.now().minusHours(configuration.getHoursOfHistory());
        log.info("Started Microblog Fetcher and getting entries after {}", timeBoundary);

        log.info("Getting and saving first page...");
        List<Entry> entries = microblog.firstPage();
        entryRepository.save(entries);

        do {
            Entry lastEntry = getLastEntry(entries);
            log.info("Getting entries before {} until {} and saving them", lastEntry.getDateAdded(), timeBoundary);
            entries = microblog.byFirstId(lastEntry.getId());
            entryRepository.save(entries);
        } while (!hasOldEntries(entries, timeBoundary));

        log.info("Reached the desired time offset {}", timeBoundary);
    }

    private Entry getLastEntry(List<Entry> entries) {
        return entries.get(entries.size() - 1);
    }

    private boolean hasOldEntries(List<Entry> entries, DateTime timeBoundary) {
        return entries.stream().anyMatch(e -> this.isOld(e, timeBoundary));
    }

    private boolean isOld(Entry entry, DateTime timeBoundary) {
        return entry.getDateAdded().isBefore(timeBoundary);
    }
}
