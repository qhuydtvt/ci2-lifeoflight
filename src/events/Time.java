package events;

import com.google.gson.annotations.SerializedName;

public class Time {
    @SerializedName("to")
    public String to;
    @SerializedName("value")
    public String value;

    @Override
    public String toString() {
        return "Time{" +
                "to='" + to + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
