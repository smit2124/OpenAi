package com.example.openai;

import android.app.Application;

public class Global extends Application {
    private String apikey;

    public String getGlobalVariable() {
        return apikey;
    }

    public void setGlobalVariable(String value) {
        apikey = value;
    }



}
