package com.cst2335.finalproject;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MovieListFragment extends Fragment {

    private final String ACTIVITY_NAME = "MovieListFragment";
    private ListView listView;
    private ArrayList<String[]> list;
    private MovieDatabaseHelper mdh;
    private SQLiteDatabase db;

    public MovieListFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mdh = new MovieDatabaseHelper(getActivity());
        db = mdh.getWritableDatabase();
        list = new ArrayList<>();
        databaseToList(db, mdh);
        listView = rootView.findViewById(R.id.movielistview);
        MovieAdapter movieAdapter = new MovieAdapter(getActivity());
        listView.setAdapter(movieAdapter);
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    /**
     *
     * @param db
     * @param mdh
     */
    protected void databaseToList(SQLiteDatabase db, MovieDatabaseHelper mdh){
        Log.i(ACTIVITY_NAME, "In databaseToList");
        Cursor c = db.query(false, MovieDatabaseHelper.TABLE_NAME, new String[]{MovieDatabaseHelper.KEY_TITLE, MovieDatabaseHelper.KEY_YEAR, MovieDatabaseHelper.KEY_RATING, MovieDatabaseHelper.KEY_RUNTIME, MovieDatabaseHelper.KEY_ACTORS, MovieDatabaseHelper.KEY_PLOT, MovieDatabaseHelper.KEY_POSTER, MovieDatabaseHelper.KEY_ID}, MovieDatabaseHelper.KEY_TITLE + " not null", null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            list.add(new String[] {c.getString(c.getColumnIndex(MovieDatabaseHelper.KEY_TITLE)), c.getString(c.getColumnIndex(MovieDatabaseHelper.KEY_YEAR)), c.getString(c.getColumnIndex(MovieDatabaseHelper.KEY_RATING)), c.getString(c.getColumnIndex(MovieDatabaseHelper.KEY_RUNTIME)), c.getString(c.getColumnIndex(MovieDatabaseHelper.KEY_ACTORS)), c.getString(c.getColumnIndex(MovieDatabaseHelper.KEY_PLOT)), c.getString(c.getColumnIndex(MovieDatabaseHelper.KEY_POSTER)), c.getString(c.getColumnIndex(MovieDatabaseHelper.KEY_ID))});
            c.moveToNext();
        }
    }

    private class MovieAdapter extends ArrayAdapter<String[]> {

        private MovieAdapter(Context ctx){super(ctx, 0); }

        public int getCount(){return list.size();}

        public String[] getItem(int position){return list.get(position);}

        public View getView(final int position, View convertView, ViewGroup parent){
            Log.i(ACTIVITY_NAME, "In getView");
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.movie_row, null);
            ImageView poster = result.findViewById(R.id.movieposterrow);
            FileInputStream fis = null;
            try {
                fis = getActivity().openFileInput(getItem(position)[0] + ".png");
            } catch (FileNotFoundException e) {
                Log.i(ACTIVITY_NAME, "File not found locally");
            }
            poster.setImageBitmap(BitmapFactory.decodeStream(fis));
            TextView title = result.findViewById(R.id.movietitlerow);
            title.setText(getItem(position)[0] + " (" + getItem(position)[1] + ")");
            ImageView close = result.findViewById(R.id.movieclosebutton);
            close.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    db.execSQL("DELETE FROM " + MovieDatabaseHelper.TABLE_NAME + " WHERE " + MovieDatabaseHelper.KEY_ID + " = " + getItem(position)[7]);
                    ((MovieInformation) getActivity()).setListFragment();
                }
            });
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] s = getItem(position);
                    ((MovieInformation) getActivity()).setDetailsFragment(s[0], s[1], s[2], s[3], s[4], s[5], s[6]);
                    }
            });
            return result;
        }

    }

}

