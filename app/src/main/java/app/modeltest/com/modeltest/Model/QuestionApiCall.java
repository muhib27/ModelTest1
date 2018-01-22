package app.modeltest.com.modeltest.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RR on 07-Jan-18.
 */

public class QuestionApiCall {
    @SerializedName("level_id")
    String level_id;

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }
}
