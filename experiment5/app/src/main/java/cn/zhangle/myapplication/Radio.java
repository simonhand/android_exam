package cn.zhangle.myapplication;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Locale;

public class Radio extends AppCompatActivity  {
    private TextView start;
    private TextView play;
    private MediaPlayer meadiaplay;
    private MediaRecorder mMediaRecorder=null;
    boolean isRecording;
    boolean isplaying=false;
    String filename;
    String filepath;
    boolean isclock=false;


    final String audioSaveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkNeedPermissions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        start=findViewById(R.id.btn_start);
        play =findViewById(R.id.btn_play);
        String input=Load();
        if(!TextUtils.isEmpty(input))
        {
            Toast.makeText(this,"欢迎回来",Toast.LENGTH_SHORT).show();
        }
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isclock)
                {
                    start.setText("开始");
                    isclock=false;
                    Toast.makeText(Radio.this,"录音结束",Toast.LENGTH_SHORT).show();
                    stopRecord();
                    isRecording=false;
                }
                else{
                    startRecord();
                    isclock=true;
                    start.setText("停止");
                    Toast.makeText(Radio.this,"开始录音",Toast.LENGTH_SHORT).show();
                }
            }

        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isplaying)
                {
                    isplaying=true;
                    play.setText("停止");
                    if(filepath!=null)
                    {
                        meadiaplay=new MediaPlayer();
                        File file=new File(filepath);
                        if(file.exists() && !isclock)
                        {
                            try {
                                meadiaplay.setDataSource(filepath);
                                meadiaplay.prepare();
                                meadiaplay.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            Toast.makeText(Radio.this,"录音未结束",Toast.LENGTH_LONG).show();
                        }
                    }else
                    {
                        Toast.makeText(Radio.this,"还没有开始录音",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    if(meadiaplay!=null)
                    {
                        isplaying=false;
                        play.setText("播放");
                        try{
                            meadiaplay.stop();
                        }catch (IllegalStateException e)
                        {
                            meadiaplay=null;
                            meadiaplay=new MediaPlayer();
                        }
                        meadiaplay.reset();
                        meadiaplay.release();
                        meadiaplay=null;
                    }
                }
            }
        });
    }
    public String Load(){
        FileInputStream in =null;
        BufferedReader reader=null;
        StringBuffer content=new StringBuffer();
        try {
            in=openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
    public  void startRecord()
    {
        if (mMediaRecorder == null)
        {
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            filename = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
            filepath = audioSaveDir +"/" +filename;
            File file=new File(filepath);
            if(!file.exists())
            {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mMediaRecorder.setOutputFile(filepath);
            try {
                mMediaRecorder.prepare();
                mMediaRecorder.start();
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }
        }
    }
    public void stopRecord()
    {
        if(mMediaRecorder!=null)
        {
            try{
                mMediaRecorder.stop();
            }catch (IllegalStateException e)
            {
                mMediaRecorder=null;
                mMediaRecorder=new MediaRecorder();
            }
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder=null;
        }
    }
    private void checkNeedPermissions(){
        //6.0以上需要动态申请权限
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                                != PackageManager.PERMISSION_GRANTED||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            //多个权限一起申请
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
            }, 1);
        }
    }
}