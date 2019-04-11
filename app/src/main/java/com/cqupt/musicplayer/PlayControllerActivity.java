package com.cqupt.musicplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class PlayControllerActivity extends AppCompatActivity {

    private ImageButton  mStopButton;
    private ImageButton mStartButton;
    private ImageButton mPauseButton;
    private CheckBox ch1;
    private CheckBox ch2;
    private TextView txt;

    MediaPlayer mMediaPlayer;
    String sdcard_file;

    int res_file = R.raw.a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_controller);
        sdcard_file = "/sdcard/music/张杰,张碧晨 - 只要平凡.mp3";
                initView();
        mMediaPlayer = new MediaPlayer();
        setRegist();
        mStartButton.setOnClickListener(new mStopClick());
        mPauseButton.setOnClickListener(new mPauseClick());
        mStopButton.setOnClickListener(new mStopClick());

    }

    private void initView() {
        mPauseButton = (ImageButton) findViewById(R.id.Pause);
        mStopButton = (ImageButton) findViewById(R.id.Stop);
        mStartButton = (ImageButton) findViewById(R.id.Start);
        ch1 = (CheckBox) findViewById(R.id.check1);
        ch2 = (CheckBox) findViewById(R.id.check2);
        txt = (TextView) findViewById(R.id.text1);
    }
    private void  playMusic(String path){
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }catch (IOException e){

        }
    }

    private void  setRegist(){
        if (Build.VERSION.SDK_INT>= 23){
            int REQUEST_CODE_CONCAT = 101;
            final int REQUEST_EXTERNAL_STORAGE = 1;
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            for (String str:PERMISSIONS_STORAGE){
                if (this.checkCallingOrSelfPermission(str)!= PackageManager.PERMISSION_GRANTED){
                    this.requestPermissions(PERMISSIONS_STORAGE,REQUEST_CODE_CONCAT);
                    return;
                }
            }
        }
    }

    class mStopClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
        }
    }

    class mStartClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String str = "";
            if (ch1.isChecked()){
                str = str + "\n" + ch1.getText();
                try{
                    mMediaPlayer = MediaPlayer.create(PlayControllerActivity.this,res_file);
                    mMediaPlayer.start();
                }
                catch (Exception e){
                    Log.i("ch1","res err");
                }
                if (ch2.isChecked()){
                    str = str + "\n" + ch2.getText();
                    try {
                        mMediaPlayer = new MediaPlayer();
                        mMediaPlayer.setDataSource(sdcard_file);
                        playMusic(sdcard_file);
                    }catch (Exception e){
                        Log.i("ch2","sdcard err");
                    }
                    txt.setText(str);
                }
            }
        }
    }

    class mPauseClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.pause();
            }
            else {
                mMediaPlayer.start();
            }
        }
    }
}
