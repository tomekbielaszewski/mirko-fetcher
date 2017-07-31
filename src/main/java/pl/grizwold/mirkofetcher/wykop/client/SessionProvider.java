package pl.grizwold.mirkofetcher.wykop.client;

import com.crozin.wykop.sdk.Application;
import com.crozin.wykop.sdk.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SessionProvider {
    @Value("${key.public}")
    private String getterKeyPublic;

    @Value("${key.secret}")
    private String getterKeySecret;

    public Session getSession() {
        return new Application(getterKeyPublic, getterKeySecret).openSession();
    }
}
