package com.cst2335.finalproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

public class OCTranspoBusRouteDetails extends Fragment {

    protected final static String ACTIVITY_NAME = "OCTranspoRouteDetails";
    private ListView listView;
    private ArrayList<String[]> list = new ArrayList<>();
    private BusRouteAdapter busRouteAdapter;
    private Button back;
    private String stop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.movie_landing.activity_octranspo_bus_route_details);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        routeHeading = findViewById(R.id.routeHeading);
        routeNumber = findViewById(R.id.routeNumber);
        routeDirection = findViewById(R.id.routeDirection);
        routeDirectionID = findViewById(R.id.routeDirectionID);

        OCTranpoQuery runQuery = new OCTranpoQuery();
        runQuery.execute(URL);*/
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_octranspo_bus_route_details, container, false);
        listView = (ListView) rootView.findViewById(R.id.bus_route_list);
        back = (Button) rootView.findViewById(R.id.bus_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OCTranspoBusRouteApp) getActivity()).queryStop(stop);
            }
        });
        busRouteAdapter = new BusRouteAdapter(getActivity());
        listView.setAdapter(busRouteAdapter);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        busRouteAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param s
     */
    public void addToList(String[] s){
        list.add(s);
    }

    /**
     *
     * @param s
     */
    public void setStop(String s){
        stop = s;
    }

    private class BusRouteAdapter extends ArrayAdapter<String[]> {

        private BusRouteAdapter(Context ctx){super(ctx, 0); }

        public int getCount(){return list.size();}

        public String[] getItem(int position){return list.get(position);}

        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.bus_trip, null);
            TextView id = result.findViewById(R.id.bus_trip_id);
            id.setText("Trip "+(position+1));

            TextView dest = result.findViewById(R.id.bus_destination);
            // get the string at position
            dest.setText("Destination: "+(getItem(position)[0] != "" ? getItem(position)[0] : "No information available."));

            TextView start = result.findViewById(R.id.bus_start);
            // get the string at position
            start.setText("Trip Start Time: "+(getItem(position)[1] != "" ? getItem(position)[1] : "No information available."));

            TextView speed = result.findViewById(R.id.bus_speed);
            // get the string at position
            speed.setText("GPS Speed: "+(getItem(position)[2] != "" ? getItem(position)[2] : "No information available."));

            TextView sched = result.findViewById(R.id.bus_schedule);
            // get the string at position
            sched.setText("Adjusted Schedule Time: "+(getItem(position)[3] != "" ? getItem(position)[3] : "No information available."));

            TextView longi = result.findViewById(R.id.bus_longitude);
            // get the string at position
            longi.setText("Longitude: "+(getItem(position)[4] != "" ? getItem(position)[4] : "No information available."));

            TextView lat = result.findViewById(R.id.bus_latitude);
            // get the string at position
            lat.setText("Latitude: "+(getItem(position)[5] != "" ? getItem(position)[5] : "No information available."));
            /*result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("oof", "in onclick"+getItem(position)[0]+getItem(position)[1]);
                    ((OCTranspoBusRouteApp) getActivity()).queryRoute(getItem(position)[0], getItem(position)[1]);
                }
            });*/
            return result;
        }
    }



}