package com.cst2335.finalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import java.util.ArrayList;


public class OCTranspoBusStopList extends Fragment {

    private ListView listView;
    private ImageView save;
    /**
     *
     */
    private String stop = "";
    /**
     *
     */
    private String name = "";
    /**
     *
     */
    private ArrayList<String[]> list = new ArrayList<>();
    private BusStopAdapter busStopAdapter;
    private OCTranspoDatabaseHelper dbh;
    private SQLiteDatabase db;


    public OCTranspoBusStopList() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_octranspo_bus_stop_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.bus_stop_list);
        save = (ImageView) rootView.findViewById(R.id.bus_button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cValues = new ContentValues();
                cValues.put(OCTranspoDatabaseHelper.KEY_STATION_NUMBER, stop);
                cValues.put(OCTranspoDatabaseHelper.KEY_STATION_NAME, name);
                db.insert(OCTranspoDatabaseHelper.TABLE_NAME, "NullColumnName", cValues);
                Snackbar snackbar = Snackbar.make(listView, "Added to favourites", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
        busStopAdapter = new BusStopAdapter(getActivity());
        listView.setAdapter(busStopAdapter);
        dbh = new OCTranspoDatabaseHelper(getActivity());
        db = dbh.getWritableDatabase();
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        busStopAdapter.notifyDataSetChanged();
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
    public void setStop(String s){stop = s;}

    /**
     *
     * @param s
     */
    public void setName(String s){name = s;}

    private class BusStopAdapter extends ArrayAdapter<String[]>{

        private BusStopAdapter(Context ctx){super(ctx, 0); }

        public int getCount(){return list.size();}

        public String[] getItem(int position){return list.get(position);}

        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.bus_stop, null);
            TextView stop = result.findViewById(R.id.bus_stop_number);
            stop.setText("Line Number: "+(getItem(position)[1] != "" ? getItem(position)[1] : "Information not found."));
            TextView dir = result.findViewById(R.id.bus_stop_direction);
            dir.setText("Direction: "+(getItem(position)[3] != "" ? getItem(position)[3] : "Information not found."));
            TextView heading = result.findViewById(R.id.bus_stop_heading);
            heading.setText("Heading to: "+(getItem(position)[2] != "" ? getItem(position)[2] : "Information not found."));
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((OCTranspoBusRouteApp) getActivity()).queryRoute(getItem(position)[0], getItem(position)[1]);
                }
            });
            return result;
        }
    }

}
