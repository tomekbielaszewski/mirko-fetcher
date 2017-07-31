package pl.grizwold.mirkofetcher.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grizwold.mirkofetcher.model.Entry;
import pl.grizwold.mirkofetcher.wykop.client.MicroblogClient;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EntryProvider {
    @Autowired
    private MicroblogClient microblog;

    public Set<Entry> getEntries(long untilTimestamp) {
        log.info("Getting first page...");
        List<Entry> firstPageEntries = microblog.firstPage();
        List<Entry> entries = appendNextEntriesUntil(firstPageEntries, untilTimestamp);

        log.info("Fetched {} entries. Now filtering old ones...", entries.size());
        entries = filterOutOldEntries(entries, untilTimestamp);
        log.info("{} entries after filtering!", entries.size());

        return Sets.newHashSet(entries);
    }

    private List<Entry> appendNextEntriesUntil(List<Entry> entries, long untilTimestamp) {
        List<Entry> nextEntries;

        do {
            Long idOfLastEntry = getIdOfLastEntry(entries);
            log.info("Getting entries behind entry with id {}", idOfLastEntry);
            nextEntries = microblog.byFirstId(idOfLastEntry);
            entries.addAll(nextEntries);
        } while(!hasOldEntries(nextEntries, untilTimestamp));

        log.info("Found entry which is too old! Returning...");
        return entries;
    }

    private List<Entry> filterOutOldEntries(List<Entry> entries, long untilTimestamp) {
        return entries.stream().filter(e -> !this.isOld(e, untilTimestamp)).collect(Collectors.toList());
    }

    private boolean hasOldEntries(List<Entry> entries, long untilTimestamp) {
        return entries.stream().anyMatch(e -> this.isOld(e, untilTimestamp));
    }

    private boolean isOld(Entry entry, long untilTimestamp) {
        return entry.getDateAdded().getTime() < untilTimestamp;
    }

    private Long getIdOfLastEntry(List<Entry> entries) {
        return entries.get(entries.size() - 1).getId();
    }
}
