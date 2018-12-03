package com.cst2335.finalproject;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class OCTranspoBusStopList extends Fragment {

    private ListView listView;
    private ArrayList<String> list;
    private BusStopAdapter busStopAdapter;


    public OCTranspoBusStopList() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_octranspo_bus_stop_list, container, false);
        list = new ArrayList<>();
        listView = (ListView) rootView.findViewById(R.id.bus_stop_list);
        busStopAdapter = new BusStopAdapter(getActivity());
        listView.setAdapter(busStopAdapter);
        return rootView;
    }

    public void addToList(String s){
        list.add(s);
        busStopAdapter.notifyDataSetChanged();
    }

    private class BusStopAdapter extends ArrayAdapter<String>{

        private BusStopAdapter(Context ctx){super(ctx, 0); }

        public int getCount(){return list.size();}

        public String getItem(int position){return list.get(position);}

        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View result = inflater.inflate(R.layout.bus_stop, null);
            TextView stop = result.findViewById(R.id.bus_stop_number);
            stop.setText(getItem(position));
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return result;
        }
    }

}
