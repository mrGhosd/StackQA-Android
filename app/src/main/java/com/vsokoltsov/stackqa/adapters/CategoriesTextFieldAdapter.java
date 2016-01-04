package com.vsokoltsov.stackqa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Category;

import java.util.ArrayList;

/**
 * Created by vsokoltsov on 04.01.16.
 */
public class CategoriesTextFieldAdapter extends ArrayAdapter<Category> {
    private ArrayList<Category> items;
    private ArrayList<Category> itemsAll;
    private ArrayList<Category> suggestions;
    private int viewResourceId;

    public CategoriesTextFieldAdapter(Context context, int resource, ArrayList<Category> items) {
        super(context, resource);
        this.items = items;
        this.itemsAll = (ArrayList<Category>) items.clone();
        this.suggestions = new ArrayList<Category>();
        this.viewResourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        Category category = items.get(position);
        if (category != null) {
            TextView customerNameLabel = (TextView) v.findViewById(R.id.categoryTitleLabel);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(category.getTitle());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Category)(resultValue)).getTitle();
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Category customer : itemsAll) {
                    if(customer.getTitle().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Category> filteredList = (ArrayList<Category>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Category c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
