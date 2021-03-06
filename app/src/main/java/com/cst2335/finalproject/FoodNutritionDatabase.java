package com.cst2335.finalproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class FoodNutritionDatabase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_nutrition_database);
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
                nextScreen = new Intent(FoodNutritionDatabase.this, OCTranspoBusRouteApp.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Movie option
            case (R.id.Movie_menuitem):
                nextScreen = new Intent(FoodNutritionDatabase.this, MovieInformation.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Food option
            case (R.id.Food_menuitem):
                nextScreen = new Intent(FoodNutritionDatabase.this, FoodNutritionDatabase.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the CBC option
            case (R.id.CBC_menuitem):
                nextScreen = new Intent(FoodNutritionDatabase.this, CBCNewsReader.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.menuItem):
                //How to use the application
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodNutritionDatabase.this);
                LayoutInflater inflater = FoodNutritionDatabase.this.getLayoutInflater();
                final View newView = inflater.inflate(R.layout.new_dialogue, null);
                builder.setView(newView);
                TextView helpMessage = (TextView)newView.findViewById(R.id.dialogText);
                helpMessage.setText(" Jack Loveday\n Food Nutrition Database");
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
}
