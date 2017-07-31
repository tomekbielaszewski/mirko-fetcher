package pl.grizwold.mirkofetcher.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class User {
    private String author;
    @SerializedName("author_sex")
    private String authorSex;
    @SerializedName("author_group")
    private UserGroup authorGroup;
}
