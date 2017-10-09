package events;

import com.google.gson.annotations.SerializedName;

public class Answer {
    @SerializedName("to")
    public String to;
    @SerializedName("value")
    public String value;

    @Override
    public String toString() {
        return "Answer{" +
                "to='" + to + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public boolean match(String value) {
        return this.value.equalsIgnoreCase(value) || this.value.equalsIgnoreCase("other");
    }
}
