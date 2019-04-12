package com.cqupt.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {
    ImageButton mStartButton, mStopButton;
    Thread thread = new MyThread();
    boolean isStop = false;
    private MediaPlayer mediaPlayer;
    private TextView tv_start;
    private TextView tv_end;
    private SeekBar seekbar,seekbar_volme;
    String filename;
    AudioManager mAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mStartButton = (ImageButton)findViewById(R.id.bt_media);
        mStopButton = (ImageButton)findViewById(R.id.imageButton_stop);
        mStopButton.setOnClickListener(new mStopClick());
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int actualVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        Intent intent = getIntent();
        filename = intent.getStringExtra("name");
        //开始时间
        tv_start = (TextView) findViewById(R.id.tv_start);
        //结束时间
        tv_end = (TextView) findViewById(R.id.tv_end);
        //进度条
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbar_volme = (SeekBar)findViewById(R.id.seekbar_volme);
        seekbar_volme.setMax(100);
        seekbar_volme.setProgress(actualVolume);

        seekbar_volme.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int volme = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volme = progress;
                Log.w("progress", "onProgressChanged: " + progress);

//                actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                maudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volme, AudioManager.FLAG_SHOW_UI);

                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volme,0);
//                mAudioManager.setStreamVolume(AudioManager.);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.setVolume(volme,volme);
            }
        });



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

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mStartButton.setImageResource(R.drawable.paly);
        }
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
            mStartButton.setImageResource(R.drawable.pause);
            //获取音乐总时间
            int duration=mediaPlayer.getDuration();
            //将音乐总时间设置为SeekBar的最大值
            seekbar.setMax(duration);
            //线程修改时间值

            thread.start();
            //音乐文件正在播放，则暂停并改变按钮样式
        }else if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            mStartButton.setImageResource(R.drawable.paly);
        }else{
            //启动播放
            mediaPlayer.start();
            mStartButton.setImageResource(R.drawable.pause);
        }
    }

    //使用线程控制进度条时间
    class MyThread extends Thread{
        public volatile boolean flag = true;
        @Override
        public void run() {
            super.run();
                while(seekbar.getProgress()<=seekbar.getMax()){
                    {
                        //获取音乐当前播放的位置
                        int position=mediaPlayer.getCurrentPosition();
                        //放入SeekBar中
                        seekbar.setProgress(position);
                    }
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

    class mStopClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mediaPlayer.isPlaying()){
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        }
    }
}

