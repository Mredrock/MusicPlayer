package com.cqupt.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list_view);
        list.add( "姜必群 - 围棋少年.mp3");
        list.add("张杰,张碧晨 - 只要平凡.mp3");
        list.add("张靓颖 - 生如夏花 (Live).mp3");

        listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);


        //获取当前ListView点击的行数，并且得到该数据
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (list.get(position)){
                    case "姜必群 - 围棋少年.mp3":
                        Intent intent0 = new Intent(MainActivity.this, PlayActivity.class);
                        intent0.putExtra("name","a");
                        startActivity(intent0);
                        break;
                    case "张杰,张碧晨 - 只要平凡.mp3":
                        Intent intent1 = new Intent(MainActivity.this, PlayActivity.class);
                        intent1.putExtra("name","b");
                        startActivity(intent1);
                        break;
                    case "张靓颖 - 生如夏花 (Live).mp3":
                        Intent intent2 = new Intent(MainActivity.this, PlayActivity.class);
                        intent2.putExtra("name","c");
                        startActivity(intent2);
                        break;
                }

            }
        });
    }

}
