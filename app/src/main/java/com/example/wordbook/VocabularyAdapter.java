package com.example.wordbook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class VocabularyAdapter extends ArrayAdapter<Word> {
    int resouceId;
    Context context;
    ArrayList<Word> word;

    public VocabularyAdapter(Context context,int resourceId,ArrayList<Word> object) {
        super(context, resourceId, object);
        this.context = context;
        object = word;
        this.resouceId = resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word w = getItem(position);
        //为子项动态加载布局
        View view = View.inflate(context, resouceId,null);
        TextView title = (TextView) view.findViewById(R.id.textView5);
        TextView text = (TextView) view.findViewById(R.id.textView6);
        title.setText(w.getWord());
        text.setText(w.getMessage());
        return view;
    }

}
