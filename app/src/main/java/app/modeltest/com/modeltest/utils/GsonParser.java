package app.modeltest.com.modeltest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import app.modeltest.com.modeltest.Model.Wrapper;

/**
 * Created by RR on 31-Dec-17.
 */

public class GsonParser {
    // Singleton Stuffs
    private static GsonParser instance = null;
    private Gson gson;

    private GsonParser() {
        // Exists only to defeat instantiation.
        gson = new Gson();
    }

    public static synchronized GsonParser getInstance() {
        if (instance == null) {
            instance = new GsonParser();
        }
        return instance;
    }

    public Wrapper parseServerResponse2(JsonElement object) {
        Wrapper wrapper = gson.fromJson(object, Wrapper.class);
        return wrapper;
    }
}
