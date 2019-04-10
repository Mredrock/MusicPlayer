package com.cqupt.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list_view);
        String[] strArr = new String[] { "姜必群 - 围棋少年.mp3", "张杰,张碧晨 - 只要平凡.mp3",
                "张靓颖 - 生如夏花 (Live).mp3" };
        listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1, strArr);
        listView.setAdapter(adapter);
    }
}
