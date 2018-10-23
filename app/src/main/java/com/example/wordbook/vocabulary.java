package com.example.wordbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class vocabulary extends AppCompatActivity {

    private Button back;
    private ListView listview;
    private ArrayList<Word> wordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        wordList=queryVocab();
        listview=(ListView)findViewById(R.id.list_view);
        back=(Button)findViewById(R.id.button3);
        VocabularyAdapter vocabularyAdapter=new VocabularyAdapter(vocabulary.this,R.layout.vocabularyintro,wordList);
        listview.setAdapter(vocabularyAdapter);
        //listview的点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Word info;
                final ArrayList<Word> Looknotes=queryVocab();
                info = Looknotes.get(position);
                Bundle bundle = new Bundle();

                bundle.putString("word", info.getWord());
//                bundle.putString("proB", info.getProB());
//                bundle.putString("proA",info.getProA());
//                bundle.putString("musicB", info.getMusicB());
//                bundle.putString("musicA", info.getMusicA());
//                bundle.putString("message", info.getMessage());
//                bundle.putString("exampleE", info.getExampleE());
//                bundle.putString("exampleC", info.getExampleC());

                Intent intent = new Intent(vocabulary.this,query.class);
                intent.putExtras(bundle);
                finish();
                startActivity(intent);
            }
        });
        //listview 添加长按点击弹出选择菜单
        listview.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
        {
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 1, 0, "删除");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(vocabulary.this , MainActivity.class);
                startActivity(i);
            }
        });

    }

    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //info.id得到listview中选择的条目绑定的id
        String _id = String.valueOf(info.position);
        int id1=Integer.parseInt(_id);
        ArrayList<Word> notes = queryVocab();
        Word note = notes.get(id1);
        String word=note.getWord();
        switch (item.getItemId()) {
            case 1:
                deleteVocab(word);
                Intent i = new Intent(vocabulary.this, vocabulary.class);
                startActivity(i);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //查询生词本内所有单词
    public ArrayList<Word> queryVocab(){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        ArrayList<Word> wl = new ArrayList<Word>();
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
            wl.add(w);
            System.out.println("Vocabid="+id);
            w.print();
        }
        c.close();
        return wl;
    }

    //删除生词本内的单词
    public void deleteVocab(String word){
        SQLiteDatabase db = openOrCreateDatabase("test1.db", Context.MODE_PRIVATE, null);
        db.delete("vocabulary","word = ?",new String[]{word});
        Cursor c;
        c = db.rawQuery("SELECT * FROM vocabulary",null);
        while (c.moveToNext()) {
            int id1 = c.getInt(c.getColumnIndex("_id"));
            String word1 = c.getString(c.getColumnIndex("word"));
            String proB = c.getString(c.getColumnIndex("proB"));
            String musicB = c.getString(c.getColumnIndex("musicB"));
            String proA = c.getString(c.getColumnIndex("proA"));
            String musicA = c.getString(c.getColumnIndex("musicA"));
            String message = c.getString(c.getColumnIndex("message"));
            String exampleE = c.getString(c.getColumnIndex("exampleE"));
            String exampleC = c.getString(c.getColumnIndex("exampleC"));
            Word w = new Word(word1,proB,musicB,proA,musicA,message,exampleE,exampleC);
            System.out.println("Vocabid="+id1);
            w.print();
        }
        c.close();
    }

}
