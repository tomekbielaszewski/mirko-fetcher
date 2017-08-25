package pl.grizwold.mirkofetcher.wykop.client;

import com.crozin.wykop.sdk.Command;
import com.crozin.wykop.sdk.Session;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grizwold.microblog.model.Entry;
import pl.grizwold.microblog.model.serializer.DateTimeSerializer;

import java.util.List;

@Service
public class MicroblogClient {
    private final Session session;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
            .create();

    @Autowired
    public MicroblogClient(SessionProvider sessionProvider) {
        this.session = sessionProvider.getSession();
    }

    public List<Entry> firstPage() {
        return page(0);
    }

    public List<Entry> page(int page) {
        Command command = new Command("stream", "index", page);
        command.addParameter("page", page);
        return queryForEntries(command);
    }

    public List<Entry> byFirstId(Long id) {
        return queryForEntries(new Command("stream", "firstid", id));
    }

    public List<Entry> tagPage(String tag, int page) {
        Command command = new Command("tag", "entries", tag);
        command.addParameter("page", page);
        return queryForTagIndex(command);
    }

    private List<Entry> queryForEntries(Command cmd) {
        cmd.setClear(true);
        String json = session.execute(cmd);
        return Lists.newArrayList(gson.fromJson(json, Entry[].class));
    }

    private List<Entry> queryForTagIndex(Command cmd) {
        cmd.setClear(true);
        String json = session.execute(cmd);
        return gson.fromJson(json, TagIndex.class).items;
    }

    public class TagIndex {
        List<Entry> items;
    }
}
