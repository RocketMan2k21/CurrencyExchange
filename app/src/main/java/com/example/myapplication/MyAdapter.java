package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyAdapter extends ArrayAdapter<CurrencyModel> {
    CurrencyModel[] dataSource;
    LayoutInflater inflater;

    public MyAdapter(@NonNull Context context, int resource, @NonNull CurrencyModel[] objects) {
        super(context, resource, objects);
        dataSource = objects;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.custom_spinner, null, true);
        CurrencyModel model = dataSource[position];
        TextView textView = (TextView) rowView.findViewById(R.id.curencytxt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.countryImg);
        textView.setText(model.getCurrency());
        imageView.setImageResource(model.getCurrencyImg());
        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.custom_spinner, parent, false);
        }
        CurrencyModel model = dataSource[position];
        TextView textView = (TextView) convertView.findViewById(R.id.curencytxt);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.countryImg);
        textView.setText(model.getCurrency());
        imageView.setImageResource(model.getCurrencyImg());
        return convertView;
    }
}
