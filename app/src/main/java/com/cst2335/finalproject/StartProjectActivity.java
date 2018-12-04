package com.cst2335.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class StartProjectActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_project);
        Log.i(ACTIVITY_NAME, "In onCreate()"); // Step 3 for Lab 3

        Toolbar toolbar =
                (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Button busButton = (Button) findViewById(R.id.button);
        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(StartProjectActivity.this, OCTranspoBusRouteApp.class);
                startActivityForResult(nextScreen, 50);
            }
        });

        Button cbcButton = (Button) findViewById(R.id.button2);
        cbcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent nextScreen = new Intent(StartProjectActivity.this, CBCNewsReader.class);
                startActivityForResult(nextScreen, 50);
            }
        });

        Button foodButton = (Button) findViewById(R.id.button3);
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
                Intent nextScreen = new Intent(StartProjectActivity.this, FoodNutritionDatabase.class);
                startActivityForResult(nextScreen, 50);
            }
        });

        Button movieButton = (Button) findViewById(R.id.button4);
        movieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Test Toolbar");
                Intent nextScreen = new Intent(StartProjectActivity.this, MovieInformation.class);
                startActivityForResult(nextScreen, 50);
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent nextScreen;
        switch (item.getItemId()){
            case (R.id.OCTranspo_menuitem):
                nextScreen = new Intent(StartProjectActivity.this, OCTranspoBusRouteApp.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.Movie_menuitem):
                nextScreen = new Intent(StartProjectActivity.this, MovieInformation.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.Food_menuitem):
                nextScreen = new Intent(StartProjectActivity.this, FoodNutritionDatabase.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.CBC_menuitem):
                nextScreen = new Intent(StartProjectActivity.this, FoodNutritionDatabase.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.menuItem):
                //How to use the application
                return true;
            default:
                return false;
        }
    }

    // Step 6 for Lab 3
    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {

        // Step 11 for Lab 3
        if (requestCode == 50 && responseCode == Activity.RESULT_OK) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    // Steps 2 and 3 for Lab 3
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
