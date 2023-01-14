package com.e.testgame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.testgame.GameEngine.GameView;

public class MainActivity extends AppCompatActivity {
    private BroadCastBattery broadCastBattery;
   private ImageView ivLoader;
   private boolean isMusicPlaying;
   private String strLink;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isMusicPlaying = false;
        serviceIntent = new Intent(this, RadioPlayService.class);
        broadCastBattery=new BroadCastBattery();
        findViewById(R.id.imageView);
        ivLoader = (ImageView) findViewById(R.id.imageView);
        ivLoader.setBackgroundResource(R.drawable.animation_loader);
        playGif();

    }
    /**
     * Plays gif at main screen
     */
    private void playGif()
    {
        AnimationDrawable starsAnim = (AnimationDrawable) findViewById(R.id.imageView).getBackground();
        starsAnim.start();
    }
    public void startGame(View view)
    {
        getSupportActionBar().hide();
        setContentView(new GameView(this));
    }
    public void checkRating(View view)
    {
        Intent intent = new Intent(this, RatingActivity.class);
        startActivity(intent);
    }

    public void startGameOverMenu()
    {
        Intent intent = new Intent(this, RestartActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.dots_menu, menu);
        return true;
    }

    /**
     * Shows notifications that the user has moved to another screen.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
        if(id==R.id.item_help){
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
            Toast.makeText(this, "you have selected help", Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.item_about){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            Toast.makeText(this, "you have selected about", Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.item_radio){
            if(!isMusicPlaying)
            {
                isMusicPlaying = true;
                playAudio();
            }else
            {
                isMusicPlaying = false;
                stopAudio();
            }


            Toast.makeText(this, "you have selected radio", Toast.LENGTH_LONG).show();
        }
        return true;
    }
    /**
     * Stops radio
     */
    private void stopAudio() {
        try{
            stopService(serviceIntent);
            Toast.makeText(this,"Audio stopped",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Starts radio
     */
    private void playAudio() {
        // strLink = "http://www.appschool.co.il/fightnIncastel.mp3";
          strLink = "https://wwfm.streamguys1.com/live-mp3";
        //strLink ="https://jazzlounge.ice.infomaniak.ch/jazzlounge-high.mp3";
        // strLink=  "https://stream.srg-ssr.ch/rsc_de/mp3_128.m3u";
        serviceIntent.putExtra("link",strLink);
        // Toast.makeText(this,"Audio started",Toast.LENGTH_LONG).show();
        try{
            startService(serviceIntent);
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }
    private  class BroadCastBattery extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            int battery = intent.getIntExtra("level",0);
            Toast.makeText(context, String.valueOf(battery) + " %", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadCastBattery,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadCastBattery);
    }


}