package com.cst2335.finalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OCTranspoBusRouteApp extends Activity {

    protected static final String ACTIVITY_NAME = "OCTranspoBusRouteApp";
    private String destination;
    private double latitude;
    private double longitude;
    private String startTime;
    private String gpsSpeed;
    private EditText input;
    private Button search;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo_bus_route_app);
        Log.i(ACTIVITY_NAME, "In onCreate");

        input = findViewById(R.id.input_line_number);
        search = findViewById(R.id.get_route_button);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String URL = ("https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="+input.getText());
                new OCTranpoQuery().execute(URL);
            }
        });
        progressBar = findViewById(R.id.progress_bar);
    }

    public void queryRoute(String route, String stop){
        String URL = ("https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="+ route + "&routeNo=" + stop);
    }

    public void setStopDetails(ArrayList<String> route_number){
        OCTranspoBusStopList octList = new OCTranspoBusStopList();
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.bus_frame, octList).addToBackStack(null);
        ft.commit();
        for (String s : route_number){
            if (s != null) {
                octList.addToList(s);
            }
        }
    }

    public class OCTranpoQuery extends AsyncTask<String, Integer, String> {

        private ArrayList<String> route_heading = new ArrayList<>();
        private ArrayList<String> route_number = new ArrayList<>();
        private ArrayList<String> route_direction = new ArrayList<>();
        private ArrayList<String> route_direction_id = new ArrayList<>();
        private final String ns = null;

        @Override
        protected String doInBackground(String... strings) {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            try {
                /* The URL for the web browser */
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
        }

        public void parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                while (parser.getName() == null || !parser.getName().equals("Routes")) {
                    Log.i(ACTIVITY_NAME, ""+parser.getName());
                    parser.next();
                }
                readFeed(parser);
            } finally {
                in.close();
            }
        }

        private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            Log.i(ACTIVITY_NAME, "in ReadFeed()");
            parser.require(XmlPullParser.START_TAG, ns, "Routes");
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == parser.START_TAG) {
                    Log.i(ACTIVITY_NAME, "Iterating the XML tags");
                    System.out.println(parser.getName());
                    if (parser.getName().equals("RouteNo")) {
                        Log.i(ACTIVITY_NAME, ""+parser.);
                        route_number.add(parser.getText());
                        publishProgress(25);
                        Log.i(ACTIVITY_NAME, "Route number is working");
                    }
                    if (parser.getName().equals("DirectionID")) {
                        route_direction_id.add(parser.getAttributeValue(null, "value"));
                        publishProgress(50);
                        Log.i(ACTIVITY_NAME, "Route direction ID is working");
                    }
                    if (parser.getName().equals("Direction")) {
                        route_direction.add(parser.getAttributeValue(null, "value"));
                        publishProgress(75);
                        Log.i(ACTIVITY_NAME, "Route direction is working");
                    }
                    if (parser.getName().equals("RouteHeading")) {
                        route_heading.add(parser.getAttributeValue(null, "value"));
                        publishProgress(100);
                        Log.i(ACTIVITY_NAME, "Route heading is working");
                    }

                }
            }
        }



        public boolean fileExistence(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
            Log.i(ACTIVITY_NAME, "Progress Update");
        }

        protected void onPostExecute(String result) {
            /*routeHeading.setText("Route Heading: " + route_heading);
            routeNumber.setText("Route Number: " + route_number);
            routeDirection.setText("Route Direction: " + route_direction);
            routeDirectionID.setText("Route Direction ID: " + route_direction_id);*/
            setStopDetails(route_number);
            progressBar.setVisibility(View.INVISIBLE);
            Log.i(ACTIVITY_NAME, "Post Execution");
        }
    }

}
