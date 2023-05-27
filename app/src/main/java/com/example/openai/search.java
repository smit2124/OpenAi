package com.example.openai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class search extends AppCompatActivity {
    RecyclerView rec;
    EditText msgbox1;
    ImageView sndbtn,backsearch;
    List<messagemodel> messageList;
    MessgeAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(Color.BLACK);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        messageList = new ArrayList<>();

        rec = findViewById(R.id.rec);
        msgbox1 = findViewById(R.id.msgbox1);
        sndbtn = findViewById(R.id.sndbtn);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);

        backsearch=findViewById(R.id.backsearch);

        backsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String usersearch=getIntent().getStringExtra("search");

        msgbox1.setText(usersearch);

        messageList.add(new messagemodel(msgbox1.getText().toString(),"me"));

//        callAPI(msgbox1.getText().toString());
        try {
            callAPI(getIntent().getStringExtra("editTextVal"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        messageAdapter = new MessgeAdapter(messageList);
        rec.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rec.setLayoutManager(llm);

        sndbtn.setOnClickListener((v) -> {
            String question = msgbox1.getText().toString().trim();

            sndbtn.startAnimation(animation);


            if (vibrator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                    if (!TextUtils.isEmpty(question)) {
                        addToChat(question, messagemodel.SENT_BY_ME);
                        try {
                            callAPI(question);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        // Handle empty question case if needed
                    }
                }
            }
        });
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new messagemodel(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                rec.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        addToChat(response, messagemodel.SENT_BY_BOT);
    }

    void callAPI(String question) throws JSONException {

        Global myApp = (Global) getApplication();
        String value = myApp.getGlobalVariable();
        // okhttp
        messageList.add(new messagemodel("\uD835\uDC95\uD835\uDC9A\uD835\uDC91\uD835\uDC8A\uD835\uDC8F\uD835\uDC88...", messagemodel.SENT_BY_BOT));

        JSONObject jsonBody = new JSONObject();
//        JSONObject jsonData = new JSONObject();
//        jsonData.put("role", "user");
        try {
            jsonBody.put("model", "text-davinci-003");
            jsonBody.put("prompt", question);
            jsonBody.put("max_tokens", 4000);
            jsonBody.put("temperature", 0);
//            jsonBody.put("message", jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.hypere.app/v1/completions")
                .header("Authorization", "Bearer "+value)
                .post(body)
                .build();

        msgbox1.getText().clear();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to " + e.getMessage());
            }


            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    addResponse("Failed to load response due to " + response.body().toString());
                }
            }
        });

    }
}