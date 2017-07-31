package pl.grizwold.mirkofetcher.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grizwold.microblog.model.Entry;
import pl.grizwold.mirkofetcher.model.repo.EntryRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MicroblogFetcher {
    @Autowired
    private EntryProvider entryProvider;

    @Autowired
    private EntryRepository entryRepository;

    public void run(long until) {
        log.info("Getting microblog entries...");
        Set<Entry> entries = entryProvider.getEntries(until);

        Set<Long> ids = entries.stream().map(Entry::getId).collect(Collectors.toSet());
        log.info("Unique IDs: " + ids.size());

        log.info("Saving {} microblog entries in DB...", entries.size());
        entryRepository.save(entries);
    }
}
