package com.cst2335.finalproject;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class OCTranspoBusFavourites extends Fragment {

    /**
     * Variables for EditText, Button, ProgressBar, and FrameLayout
     */
    private ListView listView;
    private ArrayList<String[]> list = new ArrayList<>();
    private OCTranspoDatabaseHelper dbh;
    private SQLiteDatabase db;
    private FavouriteAdapter favouriteAdapter;

    /*
     * Empty Constructor
     */
    public OCTranspoBusFavourites() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_octranspo_bus_favourites, container, false);
        dbh = new OCTranspoDatabaseHelper(getActivity());
        db = dbh.getWritableDatabase();
        databaseToList(db, dbh);
        listView = rootView.findViewById(R.id.favourites_list_view);
        favouriteAdapter = new FavouriteAdapter(getActivity());
        listView.setAdapter(favouriteAdapter);
        return rootView;
    }

    /**
     * Setting the database to the list
     * @param db
     * @param mdh
     */
    protected void databaseToList(SQLiteDatabase db, OCTranspoDatabaseHelper mdh){
        Cursor c = db.query(true, OCTranspoDatabaseHelper.TABLE_NAME, new String[]{OCTranspoDatabaseHelper.KEY_STATION_NUMBER, OCTranspoDatabaseHelper.KEY_STATION_NAME}, OCTranspoDatabaseHelper.KEY_STATION_NUMBER + " not null", null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            list.add(new String[] {c.getString(c.getColumnIndex(OCTranspoDatabaseHelper.KEY_STATION_NUMBER)), c.getString(c.getColumnIndex(OCTranspoDatabaseHelper.KEY_STATION_NAME))});
            c.moveToNext();
        }
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        favouriteAdapter.notifyDataSetChanged();
    }

    /* Inner class for OCTranspoBusFavourites */
    private class FavouriteAdapter extends ArrayAdapter<String[]> {

        private FavouriteAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return list.size();
        }

        public String[] getItem(int position) {
            return list.get(position);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            // This will recreate your View that you made in the resource file.
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.bus_fav, null);
            TextView stop = result.findViewById(R.id.fav_stop_number);
            // get the string at position
            stop.setText("Station: "+getItem(position)[0]+ " "+getItem(position)[1]);
            ImageView close = result.findViewById(R.id.bus_close_button);
            close.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    db.execSQL("DELETE FROM " + OCTranspoDatabaseHelper.TABLE_NAME + " WHERE " + OCTranspoDatabaseHelper.KEY_STATION_NUMBER + " = " + getItem(position)[0]);
                    ((OCTranspoBusRouteApp) getActivity()).setFavouritesDetails();
                }
            });
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((OCTranspoBusRouteApp) getActivity()).queryStop(getItem(position)[0]);
                }
            });
            return result;
        }
    }
} // End class OCTranspoBusFavourites