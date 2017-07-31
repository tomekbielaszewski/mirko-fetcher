package pl.grizwold.mirkofetcher.model;

import com.google.gson.annotations.SerializedName;

public enum EntryType {
    @SerializedName("entry")
    ENTRY,

    @SerializedName("entry_comment")
    COMMENT;
}
