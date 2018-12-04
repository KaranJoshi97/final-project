package com.cst2335.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MovieDetailsFragment extends Fragment {
    private final String ACTIVITY_NAME = "MovieDetailsFragment";
    private Button save, back;
    private TextView title, year, rating, runtime, actors, plot;
    private String t, y, ra, ru, a, p, po;
    private Bitmap pbm;
    private ImageView poster;
    private MovieDatabaseHelper mdh;
    private SQLiteDatabase db;


    public MovieDetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        title = (TextView) rootView.findViewById(R.id.movietitle);
        year = (TextView) rootView.findViewById(R.id.movieyear);
        rating = (TextView) rootView.findViewById(R.id.movierating);
        runtime = (TextView) rootView.findViewById(R.id.movieruntime);
        actors = (TextView) rootView.findViewById(R.id.movieactors);
        plot = (TextView) rootView.findViewById(R.id.movieplot);
        poster = (ImageView) rootView.findViewById(R.id.movieposter);
        save = (Button) rootView.findViewById(R.id.moviesave);
        save.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 ContentValues cValues = new ContentValues();
                 cValues.put(MovieDatabaseHelper.KEY_TITLE, t);
                 cValues.put(MovieDatabaseHelper.KEY_YEAR, y);
                 cValues.put(MovieDatabaseHelper.KEY_RATING, ra);
                 cValues.put(MovieDatabaseHelper.KEY_RUNTIME, ru);
                 cValues.put(MovieDatabaseHelper.KEY_ACTORS, a);
                 cValues.put(MovieDatabaseHelper.KEY_PLOT, p);
                 cValues.put(MovieDatabaseHelper.KEY_POSTER, po);
                 db.insert(MovieDatabaseHelper.TABLE_NAME, "NullColumnName", cValues);
                 Snackbar snackbar = Snackbar.make(save, "Added to favourites", Snackbar.LENGTH_SHORT);
                 snackbar.show();
             }

        });
        back = (Button) rootView.findViewById(R.id.movieback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ((MovieInformation) getActivity()).setLanding();
            }
        });
        mdh = new MovieDatabaseHelper(getActivity());
        db = mdh.getWritableDatabase();
        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.title.setText(t+"("+y+")");
        //this.year.setText(y);
        this.rating.setText(ra);
        this.runtime.setText(ru);
        this.actors.setText(a);
        this.plot.setText(p);
        new RetrievePosterTask().execute(po);
    }

    public void setInfo(String title, String year, String rating, String runtime, String actors, String plot, String poster) {
        t = title;
        y = year;
        ra = ("Rated: "+rating);
        ru = ("Runtime: "+runtime);
        a = actors;
        p = plot;
        po = poster;
    }

    class RetrievePosterTask extends AsyncTask<String, Integer, Bitmap> {

        protected Bitmap doInBackground (String... url) {
            FileInputStream fis = null;
            try {
                fis = getActivity().openFileInput(t + ".png");
            } catch (FileNotFoundException e) {
                Log.i(ACTIVITY_NAME, "File not found locally");
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);
            if (bm == null) {
                Log.i(ACTIVITY_NAME, "Downloading image");
                try {
                    bm = getImage(url[0]);
                    FileOutputStream outputStream = getActivity().openFileOutput(t + ".png", Context.MODE_PRIVATE);
                    bm.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (NullPointerException e) {
                    return null;
                } catch (FileNotFoundException e){
                    return null;
                } catch (IOException e){
                    return null;
                }
                pbm = bm;
            } else {
                Log.i(ACTIVITY_NAME, "Found image locally");
                pbm = bm;
            }
            return bm;
        }

        protected void onPostExecute(Bitmap result){
            poster.setImageBitmap(pbm);
        }

        private Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        private Bitmap getImage(String urlString) {
            try {
                Log.i(ACTIVITY_NAME, urlString);
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
