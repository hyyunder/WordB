package com.example.wordbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class query extends AppCompatActivity {
    String text=null;
    Word detail = new Word();
    private TextView word;
    private TextView britishProDetail;
    private TextView americaProDetail;
    private TextView messageDetail;
    private ListView listView=null ;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        //获取传来的查询单词的参数
        Bundle b=getIntent().getExtras();
        text=b.getString("text");
        String wo=b.getString("word");
        ArrayList<Example> list = new ArrayList<Example>();
        ArrayList<String> eList;
        ArrayList<String> cList;
        word = (TextView)findViewById(R.id.Word);
        back = (Button)findViewById(R.id.back);
        britishProDetail = (TextView)findViewById(R.id.BritishProDetail);
        americaProDetail = (TextView)findViewById(R.id.AmericaProDetail);
        messageDetail = (TextView)findViewById(R.id.MessageDetail);
        listView = (ListView) findViewById(R.id.list_view);

        if(text!=null) {
            //创建子线程
            MyThread m = new MyThread(text);
            m.start();
            try {
                //让主线程进入阻塞状态
                m.join();
                //子线程完成后主线程被唤醒，调用getValue()方法获得子线程计算的值。
                detail.setword(m.getWord());
//            detail.print();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        insertWord(detail);
        if(wo!=null){
            detail.setword(LookVocabularyOne(wo));
        }
        eList = detail.getExampleEList();
        cList = detail.getExampleCList();
        for(int i=0;i<eList.size();i++){
            Example e = new Example(eList.get(i),cList.get(i));
            list.add(e);
        }
        WordAdapter adapter = new WordAdapter(query.this, R.layout.example, list);
        listView.setAdapter(adapter);
        word.setText(detail.getWord());
        britishProDetail.setText(detail.getProB());
        americaProDetail.setText(detail.getProA());
        messageDetail.setText(detail.getMessage());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(query.this , MainActivity.class);
                startActivity(i);
            }
        });
        britishProDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    MediaPlayer mp = new MediaPlayer();
                    Uri uri = Uri.parse(detail.getMusicB());
                    mp.setDataSource(query.this, uri);
                    mp.prepare();
                    mp.start();
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        });
        americaProDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    MediaPlayer mp = new MediaPlayer();
                    Uri uri = Uri.parse(detail.getMusicA());
                    mp.setDataSource(query.this, uri);
                    mp.prepare();
                    mp.start();
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(query.this , MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(query.this , MainActivity.class);
                startActivity(i);
            }
        });
    }
    //根据word在vocabulary表内查找单词并返回
    public Word LookVocabularyOne(String word){
        Word w = null;
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        String sql = "select * from vocabulary where word = '"+word+"'";
        Cursor c;
        c = db.rawQuery(sql,null);
        while (c.moveToNext()) {
            String word1 = c.getString(c.getColumnIndex("word"));
            String proB = c.getString(c.getColumnIndex("proB"));
            String musicB = c.getString(c.getColumnIndex("musicB"));
            String proA = c.getString(c.getColumnIndex("proA"));
            String musicA = c.getString(c.getColumnIndex("musicA"));
            String message = c.getString(c.getColumnIndex("message"));
            String exampleE = c.getString(c.getColumnIndex("exampleE"));
            String exampleC = c.getString(c.getColumnIndex("exampleC"));
            w = new Word(word1,proB,musicB,proA,musicA,message,exampleE,exampleC);
        }
        c.close();
        return w;
    }

    //新建word表并向表内插入数据
    public void insertWord(Word detail){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
//        db.execSQL("DROP TABLE IF EXISTS word");
        //创建note表
//        db.execSQL("CREATE TABLE word (_id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, proB VARCHAR ,musicB VARCHAR,proA VARCHAR,musicA VARCHAR,message VARCHAR,exampleE VARCHAR,exampleC VARCHAR )");
        if(detail!=null){
            db.execSQL("INSERT INTO word VALUES (NULL,?, ?, ?,?,?,?,?,?)", new Object[]{detail.getWord(),detail.getProB(),detail.getMusicB(),detail.getProA(),detail.getMusicA(),detail.getMessage(),detail.getExampleE(),detail.getExampleC()});
        }
    }

    //新建vocabulary表
    public void insertVoc(){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS vocabulary");
        //创建note表
        db.execSQL("CREATE TABLE vocabulary (_id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, proB VARCHAR ,musicB VARCHAR,proA VARCHAR,musicA VARCHAR,message VARCHAR,exampleE VARCHAR,exampleC VARCHAR )");

    }
}
