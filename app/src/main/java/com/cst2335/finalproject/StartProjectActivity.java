package com.cst2335.finalproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class StartProjectActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_project);
        Log.i(ACTIVITY_NAME, "In onCreate()"); // Step 3 for Lab 3

        /**
         * Toolbar variable and setting the support action
         */
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    /**
     * Inflating the Menu resource
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    /**
     * Handling each item id in onOptionsItemSelected()
     */
    public boolean onOptionsItemSelected(MenuItem item){
        Intent nextScreen;
        switch (item.getItemId()){
            // Selecting the OC Transpo option
            case (R.id.OCTranspo_menuitem):
                nextScreen = new Intent(StartProjectActivity.this, OCTranspoBusRouteApp.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Movie option
            case (R.id.Movie_menuitem):
                nextScreen = new Intent(StartProjectActivity.this, MovieInformation.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Food option
            case (R.id.Food_menuitem):
                nextScreen = new Intent(StartProjectActivity.this, FoodNutritionDatabase.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the CBC option
            case (R.id.CBC_menuitem):
                nextScreen = new Intent(StartProjectActivity.this, CBCNewsReader.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.menuItem):
                //How to use the application
                AlertDialog.Builder builder = new AlertDialog.Builder(StartProjectActivity.this);
                LayoutInflater inflater = StartProjectActivity.this.getLayoutInflater();
                final View newView = inflater.inflate(R.layout.new_dialogue, null);
                builder.setView(newView);
                TextView helpMessage = (TextView)newView.findViewById(R.id.dialogText);
                helpMessage.setText(" Dylan Sherwood\n Karan Joshi\n Emmanueluche Nwafor\n Jack Loveday\n Final Project Version 12/05/2018");
                /*builder.setPositiveButton(R.string.positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText newMessage = (EditText)newView.findViewById(R.id.new_message);
                        currentMessage = newMessage.getText().toString();
                        Toast.makeText(MovieInformation.this,"Message saved in item 1", Toast.LENGTH_SHORT).show();
                    }
                });*/
                builder.setNegativeButton(R.string.negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.create().show();
                return true;
            default:
                return false;
        } // End switch case
    } // End function onOptionsItemSelected

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {

        if (requestCode == 50 && responseCode == Activity.RESULT_OK) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_LONG);
            toast.show();
        }
    }

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

} // End Class StartProjectActivity


