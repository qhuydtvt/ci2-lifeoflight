package events;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huynq on 10/9/17.
 */
public class Story {

    @SerializedName("message")
    public String message;
    @SerializedName("time")
    public Time time;
    @SerializedName("ID")
    public String ID;
    @SerializedName("type")
    public String type;
    @SerializedName("answers")
    public List<Answer> answers;

    public boolean isType(String type) {
        return this.type.equalsIgnoreCase(type);
    }

    public Answer checkAnswer(String value) {
        for(Answer answer: answers) {
            if (answer.match(value)) {
                return answer;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Story{" +
                "message='" + message + '\'' +
                ", time=" + time +
                ", ID='" + ID + '\'' +
                ", type='" + type + '\'' +
                ", answers=" + answers +
                '}';
    }
}
