package com.cqupt.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class PlayActivity extends AppCompatActivity {
    ImageButton imageButton;

    private MediaPlayer mediaPlayer;
    private TextView tv_start;
    private TextView tv_end;
    private SeekBar seekbar;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        imageButton = (ImageButton)findViewById(R.id.bt_media);
        Intent intent = getIntent();
        filename = intent.getStringExtra("name");
        //开始时间
        tv_start = (TextView) findViewById(R.id.tv_start);
        //结束时间
        tv_end = (TextView) findViewById(R.id.tv_end);
        //进度条
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        //设置监听
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //获取音乐总时间
                int duration2=mediaPlayer.getDuration()/1000;
                //获取音乐当前播放的位置
                int position=mediaPlayer.getCurrentPosition();
                //开始时间
                tv_start.setText(calculateTime(position/1000));
                //结束时间
                tv_end.setText(calculateTime(duration2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress=seekBar.getProgress();
                //在当前位置播放
                mediaPlayer.seekTo(progress);
            }
        });

    }

    //按钮点击事件
    public void isPlayOrPause(View view){

        //判断音频文件是否为空
        if(mediaPlayer==null){
            //为空则创建音乐文件并播放改变按钮样式

            switch (filename){
                case "a":
                    mediaPlayer = MediaPlayer.create(this, R.raw.a);
                    break;
                case "b":
                    mediaPlayer = MediaPlayer.create(this, R.raw.b);
                    break;

                case "c":
                    mediaPlayer = MediaPlayer.create(this, R.raw.c);
                    break;
            }


            mediaPlayer.start();
            imageButton.setImageResource(android.R.drawable.ic_media_pause);
            //获取音乐总时间
            int duration=mediaPlayer.getDuration();
            //将音乐总时间设置为SeekBar的最大值
            seekbar.setMax(duration);
            //线程修改时间值
            new MyThread().start();
            //音乐文件正在播放，则暂停并改变按钮样式
        }else if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            imageButton.setImageResource(android.R.drawable.ic_media_play);
        }else{
            //启动播放
            mediaPlayer.start();
            imageButton.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    //使用线程控制进度条时间
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(seekbar.getProgress()<=seekbar.getMax()){
                //获取音乐当前播放的位置
                int position=mediaPlayer.getCurrentPosition();
                //放入SeekBar中
                seekbar.setProgress(position);
            }
        }
    }

    //计算播放时间
    public String calculateTime(int time){
        int minute;
        int second;
        if(time>=60){
            minute=time/60;
            second=time%60;
            return minute+":"+second;
        }else if(time<60){
            second=time;
            return "0:"+second;
        }
        return null;
    }


}

