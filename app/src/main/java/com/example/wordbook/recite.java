package com.example.wordbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class recite extends AppCompatActivity {

    private Button last;
    private Button next;
    private Button collect;
    private Button back;
    private TextView word;
    private TextView britishProDetail;
    private TextView americaProDetail;
    private TextView messageDetail;
    private ListView listView=null ;
    Word displayWord = new Word();
    int displayId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
        //数据初始化
        last=(Button)findViewById(R.id.button4);
        next=(Button)findViewById(R.id.button5);
        collect=(Button)findViewById(R.id.button6);
        back=(Button)findViewById(R.id.back);
        word = (TextView)findViewById(R.id.Word);
        britishProDetail = (TextView)findViewById(R.id.BritishProDetail);
        americaProDetail = (TextView)findViewById(R.id.AmericaProDetail);
        messageDetail = (TextView)findViewById(R.id.MessageDetail);
        listView = (ListView) findViewById(R.id.list_view);
        ArrayList<Example> list = new ArrayList<Example>();
        ArrayList<String> eList;
        ArrayList<String> cList;
        Bundle b=getIntent().getExtras();
        if(b==null) {
            //当前界面为word表内的第一个单词
            displayId = LookWordFirst();
            displayWord = LookWordOne(displayId);
        }else{
            displayId=b.getInt("id");
            displayWord = LookWordOne(displayId);
        }
        eList = displayWord.getExampleEList();
        cList = displayWord.getExampleCList();
        for(int i=0;i<eList.size();i++){
            Example e = new Example(eList.get(i),cList.get(i));
            list.add(e);
        }
        WordAdapter adapter = new WordAdapter(recite.this, R.layout.example, list);
        listView.setAdapter(adapter);
        word.setText(displayWord.getWord());
        britishProDetail.setText(displayWord.getProB());
        americaProDetail.setText(displayWord.getProA());
        messageDetail.setText(displayWord.getMessage());
        queryWord();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=displayId+1;
                Bundle b = new Bundle();
                b.putInt("id",id);
                System.out.println("id="+id);
                System.out.println("b="+b.getInt("id"));
                Intent i = new Intent(recite.this , recite.class);
                i.putExtras(b);
                finish();
                startActivity(i);
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=displayId-1;
                Bundle b = new Bundle();
                b.putInt("id",id);
                System.out.println("id="+id);
                System.out.println("b="+b.getInt("id"));
                Intent i = new Intent(recite.this , recite.class);
                i.putExtras(b);
                finish();
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(recite.this , MainActivity.class);
                startActivity(i);
            }
        });
        britishProDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    MediaPlayer mp = new MediaPlayer();
                    Uri uri = Uri.parse(displayWord.getMusicB());
                    mp.setDataSource(recite.this, uri);
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
                    Uri uri = Uri.parse(displayWord.getMusicA());
                    mp.setDataSource(recite.this, uri);
                    mp.prepare();
                    mp.start();
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToVoca(displayWord);
            }
        });
        queryVocab();

    }

    //查询单词表内所有单词
    public void queryWord(){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        Cursor c;
        c = db.rawQuery("SELECT * FROM word",null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("_id"));
            String word = c.getString(c.getColumnIndex("word"));
            String proB = c.getString(c.getColumnIndex("proB"));
            String musicB = c.getString(c.getColumnIndex("musicB"));
            String proA = c.getString(c.getColumnIndex("proA"));
            String musicA = c.getString(c.getColumnIndex("musicA"));
            String message = c.getString(c.getColumnIndex("message"));
            String exampleE = c.getString(c.getColumnIndex("exampleE"));
            String exampleC = c.getString(c.getColumnIndex("exampleC"));
            Word w = new Word(word,proB,musicB,proA,musicA,message,exampleE,exampleC);
            System.out.println("id="+id);
            w.print();
        }
        c.close();
    }

    //查询生词本内所有单词
    public void queryVocab(){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        Cursor c;
        c = db.rawQuery("SELECT * FROM vocabulary",null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("_id"));
            String word = c.getString(c.getColumnIndex("word"));
            String proB = c.getString(c.getColumnIndex("proB"));
            String musicB = c.getString(c.getColumnIndex("musicB"));
            String proA = c.getString(c.getColumnIndex("proA"));
            String musicA = c.getString(c.getColumnIndex("musicA"));
            String message = c.getString(c.getColumnIndex("message"));
            String exampleE = c.getString(c.getColumnIndex("exampleE"));
            String exampleC = c.getString(c.getColumnIndex("exampleC"));
            Word w = new Word(word,proB,musicB,proA,musicA,message,exampleE,exampleC);
            System.out.println("Vocabid="+id);
            w.print();
        }
        c.close();
    }

    //向word表内插入数据
    public void insertToWord(Word detail){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        if(detail!=null){
            db.execSQL("INSERT INTO word VALUES (NULL,?, ?, ?,?,?,?,?,?)", new Object[]{detail.getWord(),detail.getProB(),detail.getMusicB(),detail.getProA(),detail.getMusicA(),detail.getMessage(),detail.getExampleE(),detail.getExampleC()});
        }
    }

    //向生词本内插入单词
    public void insertToVoca(Word detail){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        if(detail!=null){
            db.execSQL("INSERT INTO vocabulary VALUES (NULL,?, ?, ?,?,?,?,?,?)", new Object[]{detail.getWord(),detail.getProB(),detail.getMusicB(),detail.getProA(),detail.getMusicA(),detail.getMessage(),detail.getExampleE(),detail.getExampleC()});
        }
    }

    //根据id在word表内查找单词并返回
    public Word LookWordOne(int id){
        Word w = null;
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        String sql = "select * from word where _id = "+id;
        Cursor c;
        c = db.rawQuery(sql,null);
        while (c.moveToNext()) {
            String word = c.getString(c.getColumnIndex("word"));
            String proB = c.getString(c.getColumnIndex("proB"));
            String musicB = c.getString(c.getColumnIndex("musicB"));
            String proA = c.getString(c.getColumnIndex("proA"));
            String musicA = c.getString(c.getColumnIndex("musicA"));
            String message = c.getString(c.getColumnIndex("message"));
            String exampleE = c.getString(c.getColumnIndex("exampleE"));
            String exampleC = c.getString(c.getColumnIndex("exampleC"));
            w = new Word(word,proB,musicB,proA,musicA,message,exampleE,exampleC);
        }
        c.close();
        return w;
    }

    //获取word表内第一个单词的id
    public int LookWordFirst(){
        int idFirst=0;
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        Cursor c;
        c = db.rawQuery("SELECT * FROM word LIMIT 0,1",null);
        while (c.moveToNext()) {
            idFirst = c.getInt(c.getColumnIndex("_id"));
        }
        c.close();
        return idFirst;
    }

    //向word表内插入数据
    public void insertWord(Word detail){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS word");
        //创建note表
        db.execSQL("CREATE TABLE word (_id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, proB VARCHAR ,musicB VARCHAR,proA VARCHAR,musicA VARCHAR,message VARCHAR,exampleE VARCHAR,exampleC VARCHAR )");
        if(detail!=null){
            db.execSQL("INSERT INTO vocabulary VALUES (NULL,?, ?, ?,?,?,?,?,?)", new Object[]{detail.getWord(),detail.getProB(),detail.getMusicB(),detail.getProA(),detail.getMusicA(),detail.getMessage(),detail.getExampleE(),detail.getExampleC()});
        }
    }

    //向vocabulary表内插入数据
    public void insertVoc(){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS vocabulary");
        //创建note表
        db.execSQL("CREATE TABLE vocabulary (_id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, proB VARCHAR ,musicB VARCHAR,proA VARCHAR,musicA VARCHAR,message VARCHAR,exampleE VARCHAR,exampleC VARCHAR )");

    }
}
