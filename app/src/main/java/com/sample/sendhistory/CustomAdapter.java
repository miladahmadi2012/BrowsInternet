package com.sample.sendhistory;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter implements Filterable
{
    Context context;
    List<SearchModel> searchModelList=new ArrayList<>();
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<SearchModel> searchModelListImport)
    {
        this.context = context;
        this.searchModelList = searchModelListImport;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount()
    {
        return searchModelList.toArray().length;
    }

    @Override
    public Object getItem(int i)
    {
        return searchModelList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return searchModelList.get(i).getIndex();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView searchAddress = view.findViewById(R.id.searchAddress);
        TextView userRate = view.findViewById(R.id.userRate);

        searchAddress.setText(searchModelList.get(i).address);
        userRate.setText("User rate : "+searchModelList.get(i).rate+"");

        return view;
    }


    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                searchModelList = ( List<SearchModel>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<SearchModel> FilteredArrayNames = new ArrayList<SearchModel>();

                // perform your search here using the searchConstraint String.
                ArrayList<SearchModel> listOfSearchModel= Utils.getListOfSearchModel();
                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < listOfSearchModel.size(); i++) {
                    SearchModel dataNames = listOfSearchModel.get(i);
                    if (dataNames.address.toLowerCase().contains(constraint.toString()))  {
                        FilteredArrayNames.add(dataNames);
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };

        return filter;
    }

}