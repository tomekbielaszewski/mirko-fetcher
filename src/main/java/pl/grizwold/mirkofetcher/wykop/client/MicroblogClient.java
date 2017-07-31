package pl.grizwold.mirkofetcher.wykop.client;

import com.crozin.wykop.sdk.Command;
import com.crozin.wykop.sdk.Session;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grizwold.mirkofetcher.model.Entry;

import java.util.List;

@Service
public class MicroblogClient {
    private final Session session;
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    @Autowired
    public MicroblogClient(SessionProvider sessionProvider) {
        this.session = sessionProvider.getSession();
    }

    public List<Entry> firstPage() {
        return page(0);
    }

    public List<Entry> page(int i) {
        return execute(new Command("stream", "index", i));
    }

    public List<Entry> byFirstId(Long id) {
        return execute(new Command("stream", "firstid", id));
    }

    private List<Entry> execute(Command cmd) {
        cmd.setClear(true);
        String json = session.execute(cmd);
        return Lists.newArrayList(gson.fromJson(json, Entry[].class));
    }
}
