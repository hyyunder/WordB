package com.example.wordbook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Example> {
    int resouceId;
    Context context;
    ArrayList<Example> example;
    public WordAdapter(Context context,int resourceId,ArrayList<Example> object) {
        super(context, resourceId, object);
        this.context = context;
        object = example;
        this.resouceId = resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Example e = getItem(position);
        //为子项动态加载布局
        View view = View.inflate(context, resouceId,null);
        TextView title = (TextView) view.findViewById(R.id.textView5);
        TextView text = (TextView) view.findViewById(R.id.textView6);
        title.setText(e.getExampleE());
        text.setText(e.getExampleC());
        return view;
    }

}
