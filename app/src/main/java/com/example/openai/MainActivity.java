package com.example.openai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements LocationListener{

    EditText search1;

    TextView location,looc;

    ImageView searchbtn, key;

    ImageButton drawer_toggle_button;
    private Animation animation;
    private List<CardData> mCardDataList;
    private GridView mgridView;
    DrawerLayout drawerLayout;
    NavigationView navigation_view;

    private AlertDialog alertDialog;

    MenuItem feedback;

    private LocationRequest locationRequest;

    LocationManager locationManager;

    ImageView permission;

    Button getloc1;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_main);

        Global global = (Global) getApplication();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        global.setGlobalVariable(sharedPreferences.getString("key", "defaultValue"));


        search1 = findViewById(R.id.search1);
        searchbtn = findViewById(R.id.searchbtn);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        key = findViewById(R.id.key);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigation_view = findViewById(R.id.navigation_view);
        drawer_toggle_button = findViewById(R.id.drawer_toggle_button);
        permission=findViewById(R.id.permission);





        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }


                int id = item.getItemId();

                if (id == R.id.optshare) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("Text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, "download this app, https://www.goggle.com");
                    startActivity(Intent.createChooser(share, "share"));
                } else if (id == R.id.optrate) {
                    showRateUsDialog();
                } else if (id == R.id.optfeedback) {
                    startActivity(new Intent(MainActivity.this, MainActivity3.class));


                    if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, 100);
                    }

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


            mCardDataList = new ArrayList<>();
            mCardDataList.add(new CardData("topic-explainer", R.drawable.topic, "\uD835\uDC13\uD835\uDC28\uD835\uDC29\uD835\uDC22\uD835\uDC1C \uD835\uDC04\uD835\uDC31\uD835\uDC29\uD835\uDC25\uD835\uDC1A\uD835\uDC22\uD835\uDC27\uD835\uDC1E\uD835\uDC2B", "Explain Like 5 Year Old Child","#9CA1B7"));
            mCardDataList.add(new CardData("compare-topics", R.drawable.compare, "\uD835\uDC02\uD835\uDC28\uD835\uDC26\uD835\uDC29\uD835\uDC1A\uD835\uDC2B\uD835\uDC1E \uD835\uDC13\uD835\uDC28\uD835\uDC29\uD835\uDC22\uD835\uDC1C", "Get Difference with Pros & Cons","#84ACAE"));
            mCardDataList.add(new CardData("summarise-text", R.drawable.summarize, "\uD835\uDC12\uD835\uDC2E\uD835\uDC26\uD835\uDC26\uD835\uDC1A\uD835\uDC2B\uD835\uDC22\uD835\uDC2C\uD835\uDC1E-\uD835\uDC13\uD835\uDC1E\uD835\uDC31\uD835\uDC2D", "Easy & Quick to Understand","#B9A4B7"));
            mCardDataList.add(new CardData("mcq-type-quiz", R.drawable.mcq, "\uD835\uDC0C\uD835\uDC1C\uD835\uDC2A  \uD835\uDC13\uD835\uDC32\uD835\uDC29\uD835\uDC1E \uD835\uDC10\uD835\uDC2E\uD835\uDC22\uD835\uDC33", "Generate MCQs with Answer","#8CAC9D"));
            mCardDataList.add(new CardData("english-dictionary", R.drawable.dict, "\uD835\uDC04\uD835\uDC27\uD835\uDC20\uD835\uDC25\uD835\uDC22\uD835\uDC2C\uD835\uDC21 \uD835\uDC03\uD835\uDC22\uD835\uDC1C\uD835\uDC2D\uD835\uDC22\uD835\uDC28\uD835\uDC27\uD835\uDC1A\uD835\uDC2B\uD835\uDC32", "Find meaning of Words","#B9A59A"));
            mCardDataList.add(new CardData("comprehensive-study-plan", R.drawable.study, "\uD835\uDC02\uD835\uDC2B\uD835\uDC1E\uD835\uDC1A\uD835\uDC2D\uD835\uDC1E \uD835\uDC12\uD835\uDC2D\uD835\uDC2E\uD835\uDC1D\uD835\uDC32 \uD835\uDC0F\uD835\uDC25\uD835\uDC1A\uD835\uDC27", "Comprehensive Study Plan","#A4B8C3"));


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
                        vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));

                        if (search1.getText().toString().length() == 0) {
                            search1.setError("ᴇɴᴛᴇʀ ᴛʜᴇ ᴘʀᴏᴍᴘᴛ");


                        } else {
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

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

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


    private void showRateUsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rate, null);
        builder.setView(dialogView);


        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        TextView maybelater = dialogView.findViewById(R.id.maybelater);
        Button ratesub=dialogView.findViewById(R.id.ratesub);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ratesub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
                float rating = ratingBar.getRating();

                if (rating == 1) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us 1 star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else if (rating == 1.5) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us 1.5 star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else if (rating == 2) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us 2 star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else if (rating == 2.5) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us 2.5 star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else if (rating == 3) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us 3 star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else if (rating == 3.5) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us 3.5 star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else if (rating == 4) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us four star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else if (rating == 4.5) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us 4.5 star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else if (rating == 5) {
                    Toast.makeText(getApplicationContext(), "Thank you for giving us five star ✨", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });


        maybelater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }


    @SuppressLint("ServiceCast")
    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCALE_SERVICE);
        }
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }



    public void toggleDrawer(View view) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(MainActivity.this , Locale.getDefault());
            List<Address> addresses= geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            looc.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}


