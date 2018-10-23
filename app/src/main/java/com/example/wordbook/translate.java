package com.example.wordbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class translate extends AppCompatActivity {

    String text=null;
    Word detail = new Word();
    private TextView sentence;
    private ListView listView=null ;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        //获取传来的查询单词的参数
        Bundle b=getIntent().getExtras();
        text=b.getString("text");
        ArrayList<String> eList;
        ArrayList<String> cList;
        ArrayList<Example> list = new ArrayList<Example>();
        sentence = (TextView)findViewById(R.id.Sentence);
        listView = (ListView) findViewById(R.id.list_view);
        back=(Button)findViewById(R.id.back);
        sentence.setText(text);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(translate.this , MainActivity.class);
                startActivity(i);
            }
        });
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
        eList = detail.getExampleEList();
        cList = detail.getExampleCList();
        for(int i=0;i<eList.size();i++){
            Example e = new Example(eList.get(i),cList.get(i));
            list.add(e);
        }
        WordAdapter adapter = new WordAdapter(translate.this, R.layout.example, list);
        listView.setAdapter(adapter);
    }

}
