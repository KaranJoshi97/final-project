package com.cst2335.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class MovieInformation extends AppCompatActivity {

    /**
     * The Variables for the ProgressBar, Button, EditText, FrameLayout, and String
     */
    private ProgressBar progress;
    private Button send, saved;
    private EditText search;
    private FrameLayout frame;
    private String ACTIVITY_NAME = "MovieInformation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        Log.i(ACTIVITY_NAME, "in onCreate()");

        /**
         * Toolbar variable and setting the support action
         */
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Connecting the backend code with the User Interface elements
         */
        progress = findViewById(R.id.movieprogress);
        send = findViewById(R.id.moviesearch);
        search = findViewById(R.id.movieedit);
        frame = findViewById(R.id.movieframe);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The link for the movies
                String url = formatSearch("http://www.omdbapi.com/?apikey=6c9862c2&r=xml&t="+search.getText());
                new MovieQuery().execute(url);
            }
        });
        saved = findViewById(R.id.moviesaved);
        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListFragment();
            }
        });
        setLanding();
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
                nextScreen = new Intent(MovieInformation.this, OCTranspoBusRouteApp.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Movie option
            case (R.id.Movie_menuitem):
                nextScreen = new Intent(MovieInformation.this, MovieInformation.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Food option
            case (R.id.Food_menuitem):
                nextScreen = new Intent(MovieInformation.this, FoodNutritionDatabase.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the CBC option
            case (R.id.CBC_menuitem):
                nextScreen = new Intent(MovieInformation.this, CBCNewsReader.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.menuItem):
                //How to use the application
                AlertDialog.Builder builder = new AlertDialog.Builder(MovieInformation.this);
                LayoutInflater inflater = MovieInformation.this.getLayoutInflater();
                final View newView = inflater.inflate(R.layout.new_dialogue, null);
                builder.setView(newView);
                TextView helpMessage = (TextView)newView.findViewById(R.id.dialogText);
                helpMessage.setText(" Dylan Sherwood\n Movie Information Version 12/05/2018\n\n Enter the title of a movie of interest in the search bar. Click the search button, and information on the movie will be displayed to you. You may need to scroll down to see all of the information. If you wish to save these details to view offline, hit the save button on the details page. Saved movie descriptions may be viewed by selecting the \"Saved\" button.");
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

    /**
     * Removes spaces from search parameter and replaces them with "+" for compatibility with omd URL.
     * @param s Search parameter to be formatted.
     * @return Formatted parameter.
     */
    protected String formatSearch(String s){
        String f = "";
        String[] arr = s.toLowerCase().split(" ");
        f = arr[0];
        for (int i = 1; i < arr.length; i++){
            f += ("+"+arr[i]);
        }
        return f;
    }

    /**
     *
     * @param title
     * @param year
     * @param rating
     * @param runtime
     * @param actors
     * @param plot
     * @param poster
     */
    public void setDetailsFragment(String title, String year, String rating, String runtime, String actors, String plot, String poster){
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.movieframe, fragment).addToBackStack(null);
        frame.removeAllViews();
        ft.commit();
        fragment.setInfo(title, year, rating, runtime, actors, plot, poster);
        search.clearFocus();
        //https://stackoverflow.com/a/1109108
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        //
    }

    /**
     * Setting the fragment for the Movie list
     */
    public void setListFragment(){
        MovieListFragment fragment = new MovieListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.movieframe, fragment).addToBackStack(null);
        frame.removeAllViews();
        ft.commit();
        //https://stackoverflow.com/a/1109108
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     *
     */
    public void setLanding(){
        Fragment fragment = new Fragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.movieframe, fragment).addToBackStack(null);
        ft.commit();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout landing = (RelativeLayout) inflater.inflate(R.layout.movie_landing, null);
        frame.addView(landing);
    }

    /**
     * Showing the toast and making the texts
     */
    public void showToast(){
        Toast toast = Toast.makeText(this, "Information not found.", Toast.LENGTH_SHORT);
        toast.show();
    }

    /* Inner class for MovieInformation */
    public class MovieQuery extends AsyncTask<String, Integer, String> {

        // The variables in the inner class
        private final String ns = null;
        private String title, year, rating, runtime, actors, plot, poster;

        protected String doInBackground (String... args) {

                String info = "";
                URL url;
                publishProgress(25);
                try {
                    url = new URL(args[0]);
                } catch (MalformedURLException e) {
                    Log.i(ACTIVITY_NAME, "Malformed URL");
                    return null;
                }
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    Log.i(ACTIVITY_NAME, "url.openConnection threw IOException");
                    return null;
                }
                conn.setReadTimeout(1000 /* milliseconds */);
                conn.setConnectTimeout(1500 /* milliseconds */);
                try {
                    conn.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    Log.i(ACTIVITY_NAME, "conn.setRequestMethod threw ProtocolException");
                    return null;
                }
                conn.setDoInput(true);
                // Starts the query
                try {
                    conn.connect();
                } catch (IOException e) {
                    Log.i(ACTIVITY_NAME, "conn.connect threw IOException");
                    e.printStackTrace();
                    return null;
                }

                try {
                    parse(conn.getInputStream());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(ACTIVITY_NAME, info);
                return info;
        }

        @Override
        /*
         * onProgressUpdate is there for to update an progress indicators, or information on your GUI
         */
        protected void onProgressUpdate(Integer... prog) {
            progress.setProgress(prog[0]);
            progress.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(String result){
            Log.i(ACTIVITY_NAME, "In onPostExecute");
            progress.setVisibility(View.INVISIBLE);
            if (title != null) {
                setDetailsFragment(title, year, rating, runtime, actors, plot, poster);
            } else {
                showToast();
            }
        }

        public void parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.next();
                readFeed(parser);
            } finally {
                in.close();
            }
        }

        private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            Log.i(ACTIVITY_NAME, "in ReadFeed()");

            parser.require(XmlPullParser.START_TAG, ns, "root");
            publishProgress(50);
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag

                if (name.equals("movie")) {
                    publishProgress(75);
                    // All of the relevant info for the movie
                    title = parser.getAttributeValue(null, "title");
                    year = parser.getAttributeValue(null, "year");
                    rating = parser.getAttributeValue(null, "rated");
                    runtime = parser.getAttributeValue(null, "runtime");
                    actors = parser.getAttributeValue(null, "actors");
                    plot = parser.getAttributeValue(null, "plot");
                    poster = parser.getAttributeValue(null, "poster");
                    parser.nextTag();
                } else {
                    skip(parser);
                }
            }
            publishProgress(100);
        }

        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }

    }
} // End class MovieInformation