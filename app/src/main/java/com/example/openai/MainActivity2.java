package com.example.openai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {


    JSONObject jsonGetter() {
        String jsonString = "[\n" +
                "  {\n" +
                "    \"id\": \"topic-explainer\",\n" +
                "    \"title\": \"Topic Explainer\",\n" +

                "    \"prompt\": \"Explain the {topic} to me, i am {year} year old and knows about {knows} and struggles with the {struggles}, provide response in markdown\",\n" +
                "    \"schema\": {\n" +
                "      \"properties\": {\n" +
                "        \"{topic}\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"title\": \"Topic\",\n" +
                "          \"default\": \"AI\",\n" +
                "          \"placeholder\": \"Enter Topic you want to learn\"\n" +
                "        },\n" +
                "        \"{year}\": {\n" +
                "          \"type\": \"number\",\n" +
                "          \"title\": \"Age\",\n" +
                "          \"default\": 5,\n" +
                "          \"placeholder\": \"Enter age\"\n" +
                "        },\n" +
                "        \"{knows}\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"title\": \"Expertise Area\",\n" +
                "          \"default\": \"Computer\",\n" +
                "          \"placeholder\": \"Enter your expert areas, in comma separate format\"\n" +
                "        },\n" +
                "        \"{struggles}\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"title\": \"you struggle with?\",\n" +
                "          \"default\": \"Math\",\n" +
                "          \"placeholder\": \"Enter area that you mostly struggle, in comma separate format\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"compare-topics\",\n" +
                "    \"title\": \"Compare Topics\",\n" +

                "    \"prompt\": \"Compare {topic1} and {topic2} and give me atleast {count} diffrences with pros and cons in markdown format\",\n" +
                "    \"schema\": {\n" +
                "      \"properties\": {\n" +
                "        \"{topic1}\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"title\": \"Topic 1\",\n" +
                "          \"default\": \"algebra\",\n" +
                "          \"placeholder\": \"Enter First Topic\"\n" +
                "        },\n" +
                "        \"{topic2}\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"title\": \"Topic 2\",\n" +
                "          \"default\": \"calculas\",\n" +
                "          \"placeholder\": \"Enter Other Topic\"\n" +
                "        },\n" +
                "        \"{count}\": {\n" +
                "          \"type\": \"number\",\n" +
                "          \"title\": \"No. of difference\",\n" +
                "          \"default\": \"5\",\n" +
                "          \"placeholder\": \"Enter a number\"\n" +
                "        }\n" + "      }\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"summarise-text\",\n" +
                "    \"title\": \"Summarise Text\",\n" +

                "    \"prompt\": \"Summarise the text delimited with triple backticks {text} and output format summary: in {count} words\",\n" +
                "    \"schema\": {\n" +
                "      \"properties\": {\n" +
                "        \"{text}\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"title\": \"Enter Text\",\n" +
                "          \"default\": \"\",\n" +
                "          \"placeholder\": \"Enter text to summarize\"\n" +
                "        },\n" +
                "        \"{count}\": {\n" +
                "          \"type\": \"number\",\n" +
                "          \"title\": \"No. of words\",\n" +
                "          \"default\": \"200\",\n" +
                "          \"placeholder\": \"Enter a number\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"mcq-type-quiz\",\n" +
                "    \"title\": \"Mcq-Type-Quiz\",\n" +

                "    \"prompt\": \"Create MCQ type quiz  from text delimited with triple backticks {text}. output should be in markdown with format : Question , options and correct answer\",\n" +
                "    \"schema\": {\n" +
                "      \"properties\": {\n" +
                "        \"{text}\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"title\": \"Enter Name of the Topic\",\n" +
                "          \"default\": \"Indian history\",\n" +
                "          \"placeholder\": \"Indian history\"\n" +
                "        },\n" +
                "        \"{number}\": {\n" +
                "          \"type\": \"number\",\n" +
                "          \"title\": \"No. of Questions\",\n" +
                "          \"default\": \"5\",\n" +
                "          \"Placeholder\": \"Enter number\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"comprehensive-study-plan\",\n" +
                "    \"title\": \"Comprehensive-Study-Plan\",\n" +

                "    \"prompt\": \"Create study plan for a goal to {topic} in {days} Days\",\n" +
                "    \"schema\": {\n" +
                "      \"properties\": {\n" +
                "        \"{topic}\": {\n" +
                "          \"type\": \"text\",\n" +
                "          \"title\": \"Topic\",\n" +
                "          \"default\": \"algebra\",\n" +
                "          \"placeholder\": \"Enter topic\"\n" +
                "        },\n" +
                "        \"{days}\": {\n" +
                "          \"type\": \"number\",\n" +
                "          \"title\": \"Days\",\n" +
                "          \"default\": 15,\n" +
                "          \"placeholder\": \"Number of Days to Complete Study\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "]";
        try {
            String s = getIntent().getStringExtra("card_id");
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");


                if (id.equals(s)) {
                    return jsonObject;
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONObject();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setStatusBarColor(Color.BLACK);

        Button btn1 = findViewById(R.id.btn1);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Toolbar tool = findViewById(R.id.tool);
        String s = getIntent().getStringExtra("card_id");
        //tool.getNavigationIcon();

        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        JSONObject jsonObject = jsonGetter();
        try {
            tool.setTitle(jsonObject.getString("title"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONObject schema = null;
        try {
            schema = jsonObject.getJSONObject("schema");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONObject properties = null;
        try {
            properties = schema.getJSONObject("properties");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        LinearLayout linearLayout = findViewById(R.id.linear_layout); // replace with your layout ID

        try {
            Iterator<?> keys = properties.keys();
            Map<String, String> editTextValue = new HashMap<>();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject property = properties.getJSONObject(key);
                String title = property.getString("title");
                String placeholder = property.getString("placeholder");

                TextView label = new TextView(this);
                label.setText(title);
                label.setTextSize(18);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                label.setTextColor(getResources().getColor(R.color.black));


                LinearLayout.LayoutParams labelLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                labelLayoutParams.width = 450;
                labelLayoutParams.height = 70;
                labelLayoutParams.setMargins(00, 60, 20, 00);

                label.setLayoutParams(labelLayoutParams);
                linearLayout.addView(label);

                // Create a new EditText for this property


                Drawable borderDrawable = getResources().getDrawable(R.drawable.border_for_card);

                EditText editText = new EditText(this);
                editText.setHint(placeholder);
                editText.setTextSize(18);
                editText.setBackground(borderDrawable);
                editText.setPadding(20, 25, 20, 25);
                editText.setTextColor(getResources().getColor(R.color.black));
                editText.setHintTextColor(getResources().getColor(R.color.black));
                editText.setText(property.getString("default"));


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Save the current value of the EditText
                        String value = s.toString();
                        editTextValue.put(key, value);
                        // Perform any necessary operations with the value
                        // (e.g., store it in a variable, save it to a database, etc.)
                    }


                    @Override
                    public void afterTextChanged(Editable s) {
                        // Not needed for this example
                    }
                });


                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                editText.setId(View.generateViewId()); // Set a unique ID for each EditText
                linearLayout.addView(editText);

                editTextValue.put("prompt", jsonObject.getString("prompt"));

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (vibrator != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                                if (editText.getText().toString().length() == 0) {
                                    editText.setError("Fill the data");

                                    if (TextUtils.isEmpty(editText.getText())) {

                                    }
                                } else {
//                                    callAPI(getIntent().getStringExtra("editTextVal"));

                                    Log.d("mymap", "onClick: " + editTextValue);
                                    Intent i = new Intent(MainActivity2.this, search.class);
                                    i.putExtra("editTextVal", editTextValue.toString());
                                    startActivity(i);
                                }
                            }
                        }
                    }

                    private void callAPI(String editTextVal) {
                    }
                });


                LinearLayout.LayoutParams editTextLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                editTextLayoutParams.setMargins(0, 20, 0, 0);
                editText.setLayoutParams(editTextLayoutParams);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}

