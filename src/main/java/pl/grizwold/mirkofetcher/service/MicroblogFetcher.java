package pl.grizwold.mirkofetcher.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.grizwold.microblog.model.Entry;
import pl.grizwold.mirkofetcher.config.Configuration;
import pl.grizwold.mirkofetcher.model.repo.EntryRepository;
import pl.grizwold.mirkofetcher.wykop.client.MicroblogClient;

import java.util.List;

@Slf4j
@Service
public class MicroblogFetcher {
    @Value("${blacklisted-by-default}")
    private List<String> blackListedTags;

    @Autowired
    private MicroblogClient microblog;

    @Autowired
    private EntryRepository entryRepository;

    public void run(Configuration configuration) {
        DateTime timeBoundary = DateTime.now().minusHours(configuration.getHoursOfHistory());
        log.info("Started Microblog Fetcher and getting entries after {}", timeBoundary);

        runOnDefaultEntryIndex(timeBoundary);
        blackListedTags.forEach(tag -> runOnBlacklistedTagIndex(timeBoundary, tag));
    }

    private void runOnDefaultEntryIndex(DateTime timeBoundary) {
        log.info("Getting and saving first page of default entry index...");
        List<Entry> entries = microblog.firstPage();
        entryRepository.save(entries);

        do {
            Entry lastEntry = getLastEntry(entries);
            log.info("Getting entries of default entry index before {} until {} and saving them", lastEntry.getDateAdded(), timeBoundary);
            entries = microblog.byFirstId(lastEntry.getId());
            entryRepository.save(entries);
        } while (!hasOldEntries(entries, timeBoundary));

        log.info("Reached the desired time offset of default entry index: {}", timeBoundary);
    }

    private void runOnBlacklistedTagIndex(DateTime timeBoundary, String tag) {
        List<Entry> entries;
        int page = 1;

        do {
            log.info("Getting and saving page no.{} of tag[#{}] entry index...", page, tag);
            entries = microblog.tagPage(tag, page);
            entryRepository.save(entries);

            page++;
        } while (!hasOldEntries(entries, timeBoundary) || page > 99);

        log.info("Reached the desired time offset {} of tag[{}] entry index or max page was reached", timeBoundary, tag);
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
