package pl.grizwold.mirkofetcher.model;

import java.util.Date;
import java.util.List;

public interface MicroblogContent {
    Long getId();
    String getAuthor();
    UserGroup getAuthorGroup();
    String getAuthorAvatar();
    String getAuthorAvatarBig();
    String getAuthorAvatarMed();
    String getAuthorAvatarLow();
    UserSex getAuthorSex();
    String getApp();
    Date getDateAdded();
    Embed getEmbed();
    String getBody();
    String getUrl();
    int getVoteCount();
    List<User> getVoters();
    EntryType getType();
    boolean isDeleted();
}
