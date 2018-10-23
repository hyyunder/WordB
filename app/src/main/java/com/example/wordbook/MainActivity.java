package com.example.wordbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText lookWord;
    private Button look;
    private Button buttonTrans;
    private EditText transText;
    private ImageButton recite;
    private ImageButton vocab;
    private TextView reciteText;
    private TextView vocabText;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    lookWord.setVisibility(View.VISIBLE);
                    look.setVisibility(View.VISIBLE);
                    buttonTrans.setVisibility(View.INVISIBLE);
                    transText.setVisibility(View.INVISIBLE);
                    recite.setVisibility(View.INVISIBLE);
                    vocab.setVisibility(View.INVISIBLE);
                    reciteText.setVisibility(View.INVISIBLE);
                    vocabText.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    recite.setVisibility(View.VISIBLE);
                    vocab.setVisibility(View.VISIBLE);
                    reciteText.setVisibility(View.VISIBLE);
                    vocabText.setVisibility(View.VISIBLE);
                    lookWord.setVisibility(View.INVISIBLE);
                    look.setVisibility(View.INVISIBLE);
                    buttonTrans.setVisibility(View.INVISIBLE);
                    transText.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    buttonTrans.setVisibility(View.VISIBLE);
                    transText.setVisibility(View.VISIBLE);
                    lookWord.setVisibility(View.INVISIBLE);
                    look.setVisibility(View.INVISIBLE);
                    recite.setVisibility(View.INVISIBLE);
                    vocab.setVisibility(View.INVISIBLE);
                    reciteText.setVisibility(View.INVISIBLE);
                    vocabText.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lookWord=(EditText)findViewById(R.id.lookText);
        look=(Button)findViewById(R.id.button);
        transText=(EditText)findViewById(R.id.TransText);
        buttonTrans=(Button)findViewById(R.id.buttonTrans);
        recite =(ImageButton)findViewById(R.id.recite);
        vocab=(ImageButton)findViewById(R.id.vocab);
        reciteText=(TextView)findViewById(R.id.textView2);
        vocabText=(TextView)findViewById(R.id.textView3);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=lookWord.getText().toString();
                Bundle b = new Bundle();
                b.putString("text",text);
                System.out.println("text="+text);
                System.out.println("b="+b.getString("text"));
                Intent i = new Intent(MainActivity.this , query.class);
                i.putExtras(b);
                finish();
                startActivity(i);
            }
        });
        buttonTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=transText.getText().toString();
                Bundle b = new Bundle();
                b.putString("text",text);
                System.out.println("text="+text);
                System.out.println("b="+b.getString("text"));
                Intent i = new Intent(MainActivity.this , translate.class);
                i.putExtras(b);
                finish();
                startActivity(i);
            }
        });
        recite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , recite.class);
                startActivity(i);
            }
        });
        vocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=transText.getText().toString();
                Bundle b = new Bundle();
                b.putString("text",text);
                System.out.println("text="+text);
                System.out.println("b="+b.getString("text"));
                Intent i = new Intent(MainActivity.this , vocabulary.class);
                i.putExtras(b);
                finish();
                startActivity(i);
            }
        });
    }

}
