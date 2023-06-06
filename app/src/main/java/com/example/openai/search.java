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
import android.util.Log;
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
    ImageView sndbtn, backsearch;
    ImageButton clearchat;
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

        backsearch = findViewById(R.id.backsearch);
        clearchat=findViewById(R.id.clearchat);


        backsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        clearchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageList!=null && messageList.size()>0){
                    messageList.clear();
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });






        String usersearch = getIntent().getStringExtra("search");
        if (usersearch != null) {

//            addToChat(usersearch, "me");
            messageList.add(new messagemodel(usersearch, "me"));
            messageList.add(new messagemodel("\uD835\uDC95\uD835\uDC9A\uD835\uDC91\uD835\uDC8A\uD835\uDC8F\uD835\uDC88...", messagemodel.SENT_BY_BOT));


            CallApi callApi = new CallApi();
            callApi.callAPI(usersearch, new CallApi.ApiResponseCallback() {
                @Override
                public void onResponse(String response) {
                    // Handle the response here
                    addResponse(response);
                    // The 'response' parameter contains the API response
                }

                @Override
                public void onFailure(Throwable error) {
                    // Handle the failure here
                    // The 'error' parameter contains the encountered error
                }
            });
        }
        String editTextVal = getIntent().getStringExtra("editTextVal");
        if (editTextVal != null){
        editTextVal = editTextVal.substring(1, editTextVal.length()-1);


            messageList.add(new messagemodel("\uD835\uDC95\uD835\uDC9A\uD835\uDC91\uD835\uDC8A\uD835\uDC8F\uD835\uDC88...", messagemodel.SENT_BY_BOT));
            Log.d("editText", "onCreate: "+ editTextVal);
            CallApi callApi = new CallApi();
            callApi.callAPI(editTextVal, new CallApi.ApiResponseCallback() {
                @Override
                public void onResponse(String response) {
                    Log.d("moosa3", "onResponse: "+ response);
                    addResponse(response);
                }

                @Override
                public void onFailure(Throwable error) {
                }
            });
        }
        messageAdapter = new MessgeAdapter(messageList);
        rec.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rec.setLayoutManager(llm);

        sndbtn.setOnClickListener((v) -> {
            String question = msgbox1.getText().toString().trim();
            msgbox1.getText().clear();

            sndbtn.startAnimation(animation);


            if (vibrator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                    if (!TextUtils.isEmpty(question)) {
                        addToChat(question, messagemodel.SENT_BY_ME);
                        messageList.add(new messagemodel("\uD835\uDC95\uD835\uDC9A\uD835\uDC91\uD835\uDC8A\uD835\uDC8F\uD835\uDC88...", messagemodel.SENT_BY_BOT));
                        CallApi callApi = new CallApi();
                        callApi.callAPI(question, new CallApi.ApiResponseCallback() {
                            @Override
                            public void onResponse(String response) {
                                // Handle the response here
                                addResponse(response);
                                // The 'response' parameter contains the API response
                            }

                            @Override
                            public void onFailure(Throwable error) {

                            }
                        });

                    } else {
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
        if(messageList.size()>0) {
            messageList.remove(messageList.size() - 1);
            addToChat(response, messagemodel.SENT_BY_BOT);

        }
    }


}