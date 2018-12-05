package com.cst2335.finalproject;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OCTranspoBusRouteApp extends AppCompatActivity {

    /**
     * The Variables for the ProgressBar, Button, EditText, FrameLayout, and String
     */
    protected static final String ACTIVITY_NAME = "OCTranspoBusRouteApp";
    private EditText input;
    private Button search, favourites;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo_bus_route_app);
        Log.i(ACTIVITY_NAME, "In onCreate");

        /**
         * Toolbar variable and setting the support action
         */
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Connecting the backend code with the User Interface elements
         */
        input = findViewById(R.id.input_line_number);
        search = findViewById(R.id.get_route_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryStop("" + input.getText());
            }
        });
        favourites = findViewById(R.id.bus_stop_favourites);
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavouritesDetails();
            }
        });
        progressBar = findViewById(R.id.progress_bar);
        frameLayout = findViewById(R.id.bus_frame);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout landing = (RelativeLayout) inflater.inflate(R.layout.bus_landing, null);
        frameLayout.addView(landing);
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
                nextScreen = new Intent(OCTranspoBusRouteApp.this, OCTranspoBusRouteApp.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Movie option
            case (R.id.Movie_menuitem):
                nextScreen = new Intent(OCTranspoBusRouteApp.this, MovieInformation.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Food option
            case (R.id.Food_menuitem):
                nextScreen = new Intent(OCTranspoBusRouteApp.this, FoodNutritionDatabase.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the CBC option
            case (R.id.CBC_menuitem):
                nextScreen = new Intent(OCTranspoBusRouteApp.this, CBCNewsReader.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.menuItem):
                //How to use the application
                AlertDialog.Builder builder = new AlertDialog.Builder(OCTranspoBusRouteApp.this);
                LayoutInflater inflater = OCTranspoBusRouteApp.this.getLayoutInflater();
                final View newView = inflater.inflate(R.layout.new_dialogue, null);
                builder.setView(newView);
                TextView helpMessage = (TextView)newView.findViewById(R.id.dialogText);
                helpMessage.setText("Author: Karan Joshi\nVersion Number: 1\nTo use this interface, you must enter a stop number and then search it." +
                        " After that, you can click on any line number and it will show you all relevant information" +
                        " such as its destination, trip start, gps speed, adjusted schedule time, longitude, and" +
                        " latitude. The user can go back to the beginning, or the user can save the stop number by clicking on the star icon where" +
                        " the stop number is saved. Click on the 'saved' button and the search stops are present. You can either click" +
                        "on the stop number for all of its info, or simply delete the stop number");
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

    /**
     *
     * @param stop
     * @param route
     *
     */
    public void queryRoute(String stop, String route){
        String URL = ("https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="+stop+"&routeNo="+route);
        new OCTranpoQuery().execute(URL);
    }

    /**
     *
     * @param stop
     */
    public void queryStop(String stop){
        String URL = ("https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="+stop);
        new OCTranpoQuery().execute(URL);
    }

    /**
     *
     * @param stop
     * @param name
     * @param route_number
     * @param route_heading
     * @param route_direction
     * @param route_direction_id
     */
    public void setStopDetails(String stop, String name, ArrayList<String> route_number, ArrayList<String> route_heading, ArrayList<String> route_direction, ArrayList<String> route_direction_id){
        OCTranspoBusStopList octList = new OCTranspoBusStopList();
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.bus_frame, octList).addToBackStack(null);
        frameLayout.removeAllViews();
        ft.commit();
        octList.setStop(stop);
        octList.setName(name);
        for (int i = 0; i < route_number.size(); i++){
            String[] s = {stop, route_number.get(i), route_heading.get(i), route_direction.get(i), route_direction_id.get(i)};
            octList.addToList(s);
        }
    }

    /**
     *
     * @param stop
     * @param trip_destination
     * @param trip_start_time
     * @param trip_gps_speed
     * @param trip_schedule_time
     * @param trip_longitude
     * @param trip_lattitude
     */
    public void setTripDetails(String stop, ArrayList<String> trip_destination, ArrayList<String> trip_start_time, ArrayList<String> trip_gps_speed, ArrayList<String> trip_schedule_time, ArrayList<String> trip_longitude, ArrayList<String> trip_lattitude){
        OCTranspoBusRouteDetails octDetails = new OCTranspoBusRouteDetails();
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.bus_frame, octDetails).addToBackStack(null);
        frameLayout.removeAllViews();
        ft.commit();
        octDetails.setStop(stop);
        for (int i = 0; i < trip_destination.size(); i++){
            String [] s = {trip_destination.get(i), trip_start_time.get(i), trip_gps_speed.get(i), trip_schedule_time.get(i), trip_longitude.get(i), trip_lattitude.get(i)};
            octDetails.addToList(s);
        }
    }

    /**
     *
     */
    public void setFavouritesDetails(){
        OCTranspoBusFavourites octFavs = new OCTranspoBusFavourites();
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.bus_frame, octFavs).addToBackStack(null);
        frameLayout.removeAllViews();
        ft.commit();
    }

    /**
     *
     */
    public void showToast(){
        Toast toast = Toast.makeText(this, "Information not found.", Toast.LENGTH_SHORT);
        toast.show();
    }


    /* Inner class for OCTranspoBusRouteApp */
    public class OCTranpoQuery extends AsyncTask<String, Integer, String> {

        // The Variables in the inner class
        private String stop_number;
        private String stop_name;
        private final String ns = null;
        //Stop Info

        /**
         * The route heading in an arraylist
         */
        private ArrayList<String> route_heading = new ArrayList<>();
        /**
         * The route number in an arraylist
         */
        private ArrayList<String> route_number = new ArrayList<>();
        /**
         * The route direction in an arraylist
         */
        private ArrayList<String> route_direction = new ArrayList<>();
        /**
         * The route direction id in an arraylist
         */
        private ArrayList<String> route_direction_id = new ArrayList<>();

        //Route Info
        /**
         * The trip destination in an arraylist
         */
        private ArrayList<String> trip_destination = new ArrayList<>();
        /**
         * The trip start time in an arraylist
         */
        private ArrayList<String> trip_start_time = new ArrayList<>();
        /**
         * The trip gps speed in an arraylist
         */
        private ArrayList<String> trip_gps_speed = new ArrayList<>();
        /**
         * The trip schedule time in an arraylist
         */
        private ArrayList<String> trip_schedule_time = new ArrayList<>();
        /**
         * The trip longitude in an arraylist
         */
        private ArrayList<String> trip_longitude = new ArrayList<>();
        /**
         * The trip latitude in an arraylist
         */
        private ArrayList<String> trip_lattitude = new ArrayList<>();

        @Override
        protected String doInBackground(String... strings) {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            publishProgress(25);
            try {
                /* The URL for the web browser */
                System.out.println(strings[0]);
                URL url;
                try {
                    url = new URL(strings[0]);
                } catch (MalformedURLException e) {
                    Log.i(ACTIVITY_NAME, "Malformed URL");
                    return null;
                }
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                // Given a string representation of a URL, sets up a connection and gets
                // an input stream.
                urlConnection.setReadTimeout(10000); // milliseconds
                urlConnection.setConnectTimeout(15000); // milliseconds
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                /* The Query is starting */
                urlConnection.connect();

                try {
                    parse(urlConnection.getInputStream());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(urlConnection.getInputStream(), "UTF-8");*/

                /*while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT){
                    if(xmlPullParser.getEventType()== xmlPullParser.START_TAG) {
                        Log.i(ACTIVITY_NAME, "Iterating the XML tags");
                        System.out.println(xmlPullParser.getName());
                        if (xmlPullParser.getName().equals("RouteNo")) {
                            route_number.add(xmlPullParser.getAttributeValue(null, "value"));
                            publishProgress(25);
                            Log.i(ACTIVITY_NAME, "Route number is working");
                        }
                        if (xmlPullParser.getName().equals("DirectionID")) {
                            route_direction_id.add(xmlPullParser.getAttributeValue(null, "value"));
                            publishProgress(50);
                            Log.i(ACTIVITY_NAME, "Route direction ID is working");
                        }
                        if (xmlPullParser.getName().equals("Direction")) {
                            route_direction.add(xmlPullParser.getAttributeValue(null, "value"));
                            publishProgress(75);
                            Log.i(ACTIVITY_NAME, "Route direction is working");
                        }
                        if (xmlPullParser.getName().equals("RouteHeading")) {
                            route_heading.add(xmlPullParser.getAttributeValue(null, "value"));
                            publishProgress(100);
                            Log.i(ACTIVITY_NAME, "Route heading is working");
                        }

                    }
                }*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(ACTIVITY_NAME, "The background has been finished");
            return "Done";
        } // End doInBackground

        public void parse(InputStream in) throws XmlPullParserException, IOException {
            System.out.println("In parse");
            try {
                XmlPullParser parser = Xml.newPullParser();
                //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                publishProgress(50);
                while ((parser.getName() == null || !parser.getName().equals("Route")) && parser.next() !=  XmlPullParser.END_DOCUMENT) {
                    System.out.println(parser.getName());
                    if (parser.getName() != null && parser.getName().equals("StopNo")){
                        stop_number = parser.nextText();
                    } else if (parser.getName() != null && parser.getName().equals("StopDescription")){
                        stop_name = parser.nextText();
                    }
                }
                readFeed(parser);
            } finally {
                in.close();
            }
        }


        private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            Log.i(ACTIVITY_NAME, "in ReadFeed()");
            parser.require(XmlPullParser.START_TAG, ns, "Route");
            publishProgress(75);
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == parser.START_TAG) {
                    Log.i(ACTIVITY_NAME, "Iterating the XML tags");
                    System.out.println(parser.getName());
                    if (parser.getName().equals("RouteNo")) {
                        route_number.add(""+parser.nextText());
                        Log.i(ACTIVITY_NAME, "Route number is working");
                    }
                    if (parser.getName().equals("DirectionID")) {
                        route_direction_id.add(""+parser.nextText());
                        //publishProgress(50);
                        Log.i(ACTIVITY_NAME, "Route direction ID is working");
                    }
                    if (parser.getName().equals("Direction")) {
                        route_direction.add(""+parser.nextText());
                        //publishProgress(75);
                        Log.i(ACTIVITY_NAME, "Route direction is working");
                    }
                    if (parser.getName().equals("RouteHeading")) {
                        route_heading.add(""+parser.nextText());
                        //publishProgress(100);
                        Log.i(ACTIVITY_NAME, "Route heading is working");
                    }
                    if (parser.getName().equals("TripDestination")){
                        trip_destination.add(""+parser.nextText());
                        Log.i(ACTIVITY_NAME, "Trip Destination is working");
                    }
                    if (parser.getName().equals("TripStartTime")){
                        trip_start_time.add(""+parser.nextText());
                        Log.i(ACTIVITY_NAME, "Trip start time is working");
                    }
                    if (parser.getName().equals("GPSSpeed")){
                        trip_gps_speed.add(""+parser.nextText());
                        Log.i(ACTIVITY_NAME, "Trip GPS speed is working");
                    }
                    if (parser.getName().equals("AdjustedScheduleTime")){
                        trip_schedule_time.add(""+parser.nextText());
                        Log.i(ACTIVITY_NAME, "Adjusted schedule is working");
                    }
                    if (parser.getName().equals("Latitude")){
                        trip_lattitude.add(""+parser.nextText());
                        Log.i(ACTIVITY_NAME, "Latitude is working");
                    }
                    if (parser.getName().equals("Longitude")){
                        trip_longitude.add(""+parser.nextText());
                        Log.i(ACTIVITY_NAME, "Longitude is working");
                    }

                }
            }
            publishProgress(100);
        }


        public boolean fileExistence(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        /**
         * onProgressUpdate is there for to update an progress indicators, or information on your GUI
         */
        protected void onProgressUpdate(Integer... value) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
            //Log.i(ACTIVITY_NAME, "Progress Update");
        }

        protected void onPostExecute(String result) {
            //If info on route heading was collected, send to bus stop generator. If info on trip destination was collected, send to bus trips generator.
            if (route_heading.size() > 0) {
                setStopDetails(stop_number, stop_name, route_number, route_heading, route_direction, route_direction_id);
            } else if (trip_destination.size() > 0){
                setTripDetails(stop_number, trip_destination, trip_start_time, trip_gps_speed, trip_schedule_time, trip_longitude, trip_lattitude);
            } else {
                showToast();
            }
            progressBar.setVisibility(View.INVISIBLE);
            Log.i(ACTIVITY_NAME, "Post Execution");
        }
    }

} // End class OCTranspoBusRouteApp
