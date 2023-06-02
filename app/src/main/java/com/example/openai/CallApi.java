package com.example.openai;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CallApi {
    public interface ApiResponseCallback {
        void onResponse(String response);
        void onFailure(Throwable error);
    }

    void callAPI(String message, final ApiResponseCallback callback) {
        Log.d("ques", "callAPI: " + message);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject json = new JSONObject();
        JSONArray messagesArray = new JSONArray();


        JSONObject messageObject = new JSONObject();
        try {
            messageObject.put("role", "user");
            messageObject.put("content", message);
            messagesArray.put(messageObject);
            json.put("model", "gpt-3.5-turbo");
            json.put("messages", messagesArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(mediaType, json.toString());
        Request request = new Request.Builder()
                .url("https://api.hypere.app/v1/chat/completions")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("authorization", "Bearer fg-DEWPD66EUAE3HEP2P5P262IMHTSLLLA3P39LWWAE")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        JSONArray choicesArray = jsonResponse.getJSONArray("choices");
                        JSONObject firstChoice = choicesArray.getJSONObject(0);
                        JSONObject msg = firstChoice.getJSONObject("message");
                        String res = msg.getString("content");
                        callback.onResponse(res);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure(e);
                    }
                } else {
                    callback.onFailure(new Exception("Request failed with code: " + response.code()));
                }
            }
        });
    }
}
