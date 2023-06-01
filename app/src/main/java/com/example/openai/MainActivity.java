package com.example.openai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText search1;
    ImageView searchbtn, key;

    ImageButton drawer_toggle_button;
    private Animation animation;
    private List<CardData> mCardDataList;
    private GridView mgridView;
    DrawerLayout drawerLayout;
    NavigationView navigation_view;
    Toolbar toolbar;
    private Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_main);

        Global global = (Global) getApplication();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        global.setGlobalVariable(sharedPreferences.getString("key", "defaultValue"));


        search1 = findViewById(R.id.search1);
        searchbtn = findViewById(R.id.searchbtn);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);


        key = findViewById(R.id.key);

        drawerLayout =findViewById(R.id.drawer_layout);
        navigation_view=findViewById(R.id.navigation_view);
        drawer_toggle_button=findViewById(R.id.drawer_toggle_button);


       // navigation_view.inflateMenu(R.menu.navigation_items);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             int id= item.getItemId();

             if (id==R.id.optshare){
                 Toast.makeText(MainActivity.this, "share", Toast.LENGTH_SHORT).show();
             } else if (id==R.id.optrate) {
                 Toast.makeText(MainActivity.this, "star", Toast.LENGTH_SHORT).show();

             }else {
                 loadfragment(new AFragment());
             }

//             DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
//             drawerLayout.closeDrawer(GravityCompat.START);
             return true;
         }
     });



        mCardDataList = new ArrayList<>();
        mCardDataList.add(new CardData("topic-explainer", R.drawable.topic, "#FAC06e", "\uD835\uDC13\uD835\uDC28\uD835\uDC29\uD835\uDC22\uD835\uDC1C \uD835\uDC04\uD835\uDC31\uD835\uDC29\uD835\uDC25\uD835\uDC1A\uD835\uDC22\uD835\uDC27\uD835\uDC1E\uD835\uDC2B", "Explain Like 5 Year Old Child"));
        mCardDataList.add(new CardData("compare-topics", R.drawable.compare, "#B3EF9F", "\uD835\uDC02\uD835\uDC28\uD835\uDC26\uD835\uDC29\uD835\uDC1A\uD835\uDC2B\uD835\uDC1E \uD835\uDC13\uD835\uDC28\uD835\uDC29\uD835\uDC22\uD835\uDC1C", "Get Difference with Pros & Cons"));
        mCardDataList.add(new CardData("summarise-text", R.drawable.summarize, "#FAB6B6", "\uD835\uDC02\uD835\uDC28\uD835\uDC26\uD835\uDC29\uD835\uDC1A\uD835\uDC2B\uD835\uDC1E \uD835\uDC13\uD835\uDC28\uD835\uDC29\uD835\uDC22\uD835\uDC1C", "Easy & Quick to Understand"));
        mCardDataList.add(new CardData("mcq-type-quiz", R.drawable.mcq, "#A5D3ED", "\uD835\uDC0C\uD835\uDC1C\uD835\uDC2A  \uD835\uDC13\uD835\uDC32\uD835\uDC29\uD835\uDC1E \uD835\uDC10\uD835\uDC2E\uD835\uDC22\uD835\uDC33", "Generate MCQs with Answer"));
        mCardDataList.add(new CardData("comprehensive-study-plan", R.drawable.study, "#F8F8A0", "\uD835\uDC02\uD835\uDC2B\uD835\uDC1E\uD835\uDC1A\uD835\uDC2D\uD835\uDC1E \uD835\uDC12\uD835\uDC2D\uD835\uDC2E\uD835\uDC1D\uD835\uDC32 \uD835\uDC0F\uD835\uDC25\uD835\uDC1A\uD835\uDC27", "Comprehensive Study Plan"));


        mgridView = findViewById(R.id.grid_view);
        CardAdapter cardAdapter = new CardAdapter(this, mCardDataList);
        mgridView.setNumColumns(2);
        mgridView.setAdapter(cardAdapter);


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchbtn.startAnimation(animation);

                String text = search1.getText().toString().trim();
                String usersearch = search1.getText().toString();

                if (vibrator != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));

                        if (search1.getText().toString().length() == 0) {
                            search1.setError("kuch information to dede yar..");


                        } else {
//                            new CallApi().callAPI(usersearch);
                            Intent i = new Intent(MainActivity.this, search.class);

                            i.putExtra("search", usersearch);

                            startActivity(i);
                            search1.getText().clear();
                        }
                    }
                }
            }
        });




        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alert();
            }
      });
    }

    private void loadfragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.container,fragment);
        ft.commit();
    }


    private void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_input, null);
        builder.setView(dialogView);

        Global global = (Global) getApplication();

        EditText inputText = dialogView.findViewById(R.id.input_text);

        inputText.setText(global.getGlobalVariable());

        inputText.getText().clear();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                global.setGlobalVariable(inputText.getText().toString());

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("key", global.getGlobalVariable());
                editor.apply();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void toggleDrawer(View view) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
