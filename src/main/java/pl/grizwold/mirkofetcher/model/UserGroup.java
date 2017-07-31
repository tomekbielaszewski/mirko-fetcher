package pl.grizwold.mirkofetcher.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public enum UserGroup {
    @SerializedName("0")
    GREEN(0),

    @SerializedName("1")
    ORANGE(1),

    @SerializedName("2")
    RED(2),

    @SerializedName("1001")
    SILVER(1001),

    @SerializedName("5")
    BLACK(5),

    @SerializedName("2001")
    BLUE(2001),

    @SerializedName("1002")
    DELETED(1002);

    private int value;

    UserGroup(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserGroup byValue(int value) {
        return Arrays.stream(values())
                .filter(ug -> ug.getValue() == value)
                .findFirst()
                .orElseGet(null);
    }
}
