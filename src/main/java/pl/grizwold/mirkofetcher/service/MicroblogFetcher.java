package pl.grizwold.mirkofetcher.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grizwold.microblog.model.Entry;
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

    public void run(long untilTimestamp) {
        DateTime timeOffset = new DateTime(untilTimestamp);
        log.info("Started Microblog Fetcher and getting entries until {}", timeOffset);

        log.info("Getting and saving first page...");
        List<Entry> entries = microblog.firstPage();
        entryRepository.save(entries);

        do {
            Entry lastEntry = getLastEntry(entries);
            log.info("Getting entries before {} until {} and saving them", lastEntry.getDateAdded(), timeOffset);
            entries = microblog.byFirstId(lastEntry.getId());
            entryRepository.save(entries);
        } while (!hasOldEntries(entries, untilTimestamp));

        log.info("Reached the desired time offset {}", timeOffset);
    }

    private Entry getLastEntry(List<Entry> entries) {
        return entries.get(entries.size() - 1);
    }

    private boolean hasOldEntries(List<Entry> entries, long untilTimestamp) {
        return entries.stream().anyMatch(e -> this.isOld(e, untilTimestamp));
    }

    private boolean isOld(Entry entry, long untilTimestamp) {
        return entry.getDateAdded().getMillis() < untilTimestamp;
    }
}
