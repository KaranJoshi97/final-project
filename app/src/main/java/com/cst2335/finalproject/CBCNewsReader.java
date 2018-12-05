package com.cst2335.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

interface OnTaskCompleted {
    void onTaskCompleted(int code, int progress, String txt);

}

public class CBCNewsReader extends AppCompatActivity implements OnTaskCompleted{
    final String TAG = "CBC_NEWS_READER";
    String get_result = "";
    final String filename = "rss-world.xml";


    ListView newsListView;
    Button detailsButton;
    ProgressBar progressBar;
    ListViewAdapter adapter;

    private class ListViewAdapter extends ArrayAdapter<String> {

        private List<String> summaryList;

        public ListViewAdapter (Context ctx) {
            super(ctx, 0);
            summaryList = new ArrayList<String>();
        }

        @Override
        public int getCount() {
            return summaryList.size();
        }

        @Override
        public String getItem(int position) {return (String) summaryList.get(position); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = CBCNewsReader.this.getLayoutInflater();
            View result = null ;
            result = inflater.inflate(R.layout.activity_news_details, null);


            TextView message = result.findViewById(R.id.newsListView);
            message.setText( summaryList.get(position) ); // get the string at position

            return result;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void add (String s) {
            summaryList.add(s);
        }





    }




    private InputStream getStream (String filename) {
        AssetManager assManager = getApplicationContext().getAssets();
        InputStream is = null;
        try {
            is = assManager.open(filename);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        InputStream caInput = new BufferedInputStream(is);
        return caInput;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbcnews_reader);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CBCNewsReaderTask newsReaderTask = new CBCNewsReaderTask(CBCNewsReader.this);
        newsReaderTask.execute(null, null, null);

        newsListView = findViewById(R.id.newsListView);
        detailsButton = findViewById(R.id.detailsButton);
        progressBar = findViewById(R.id.progressBar);

        adapter = new ListViewAdapter(this);
        newsListView.setAdapter(adapter);


        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {


                Intent startIntent = new Intent(CBCNewsReader.this, NewsDetails.class);
                Bundle newsBundle = new Bundle();

                newsBundle.putString("title", adapter.getItem(0));
                newsBundle.putString("author", adapter.getItem(1));
                newsBundle.putString("description", adapter.getItem(2));
                //newsBundle.putString("genre", a.genre);
                //String albums = String.valueOf(a.num_albums);
                //newsBundle.putString("albums", albums);
                startIntent.putExtras(newsBundle);

                startActivity(startIntent);


            }
        });





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
                nextScreen = new Intent(CBCNewsReader.this, OCTranspoBusRouteApp.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Movie option
            case (R.id.Movie_menuitem):
                nextScreen = new Intent(CBCNewsReader.this, MovieInformation.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the Food option
            case (R.id.Food_menuitem):
                nextScreen = new Intent(CBCNewsReader.this, FoodNutritionDatabase.class);
                startActivityForResult(nextScreen, 50);
                return true;
            // Selecting the CBC option
            case (R.id.CBC_menuitem):
                nextScreen = new Intent(CBCNewsReader.this, CBCNewsReader.class);
                startActivityForResult(nextScreen, 50);
                return true;
            case (R.id.menuItem):
                //How to use the application
                AlertDialog.Builder builder = new AlertDialog.Builder(CBCNewsReader.this);
                LayoutInflater inflater = CBCNewsReader.this.getLayoutInflater();
                final View newView = inflater.inflate(R.layout.new_dialogue, null);
                builder.setView(newView);
                TextView helpMessage = (TextView)newView.findViewById(R.id.dialogText);
                helpMessage.setText(" Emmanueluche Nwafor\n CBCNewsReader version 12/04/2018");
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

    @Override
    public void onTaskCompleted(int code, int progress, String txt) {

        NewsParser np = new NewsParser();
//        NewsParser.News newsSummary = np.parseNews(null, get_result);
        np.parseNews(null, get_result);

        Log.v(TAG, "Result = " + txt);
        Log.v(TAG, "HTTP reply: " + get_result);
    }




    class CBCNewsReaderTask extends AsyncTask<String, String, String> {
        final String CBC_URL = "https://www.cbc.ca/cmlink/rss-world";

        private OnTaskCompleted listener;

        public CBCNewsReaderTask(OnTaskCompleted listener2) {
            super();
            listener = listener2;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listener.onTaskCompleted(1,1,s);
        }

        @Override
        protected void onProgressUpdate(String ... values) {
            super.onProgressUpdate(values);
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return sb.toString();
        }

        @Override
        protected String doInBackground(String... strings) {

            InputStream DataInputStream = null;
            String ret_string = "ok";
            try {

                URL url = new URL(CBC_URL);

                HttpURLConnection cc = (HttpURLConnection) url.openConnection();
                //set timeout for reading InputStream
                cc.setReadTimeout(15000);
                //set timeout for connection
                cc.setConnectTimeout(15000);
                //set HTTP method to GET
                cc.setRequestMethod("GET");
                //set it to true as we are connecting for input
                cc.setDoInput(true);

                //reading HTTP response code
                int response = cc.getResponseCode();

                //if response code is 200 / OK then read InputStream
                if (response == HttpURLConnection.HTTP_OK) {
                    DataInputStream = cc.getInputStream();
                    get_result = convertStreamToString(DataInputStream);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in GetData", e);
                ret_string = "error";
            }

            return ret_string;
        }
    }
}