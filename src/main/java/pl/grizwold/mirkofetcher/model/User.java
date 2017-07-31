package pl.grizwold.mirkofetcher.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class User {
    private String author;
    @SerializedName("author_sex")
    private UserSex authorSex;

    @SerializedName("author_group")
    private UserGroup authorGroup;

    @SerializedName("author_avatar")
    private String authorAvatar;

    @SerializedName("author_avatar_big")
    private String authorAvatarBig;

    @SerializedName("author_avatar_med")
    private String authorAvatarMed;

    @SerializedName("author_avatar_lo")
    private String authorAvatarLow;
}
