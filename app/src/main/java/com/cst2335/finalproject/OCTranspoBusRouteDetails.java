package com.cst2335.finalproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

public class OCTranspoBusRouteDetails extends Fragment {

    protected final static String ACTIVITY_NAME = "OCTranspoRouteDetails";
    private TextView routeHeading, routeNumber, routeDirection, routeDirectionID;
    private String rH, rN, rD, rDID;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_octranspo_bus_route_details);
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
        routeHeading = (TextView) rootView.findViewById(R.id.routeHeading);
        routeNumber = (TextView) rootView.findViewById(R.id.routeNumber);
        routeDirection = (TextView) rootView.findViewById(R.id.routeDirection);
        routeDirectionID = (TextView) rootView.findViewById(R.id.routeDirectionID);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        routeHeading.setText(rH);
        routeNumber.setText(rN);
        routeDirection.setText(rD);
        routeDirectionID.setText(rDID);
    }

    public void setStrings(String a, String b, String c, String d){
        rH = a;
        rN = b;
        rD = c;
        rDID = d;
    }



}
