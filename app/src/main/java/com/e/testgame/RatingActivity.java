package com.e.testgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.e.testgame.GameClasses.PlayerStatistic;

public class RatingActivity extends AppCompatActivity {


    private ListView lv;
    private DBHelper _dbHelper;
    private ArrayAdapter arrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rating);
        _dbHelper = new DBHelper(this);
        lv = findViewById(R.id.playersList);
        ShowTopPlayersOnListView(_dbHelper);

    }

    /**
     * In order to update the ListView, you need to create a new adapter (it is needed to correctly display the data array on the screen)
     */
    private void ShowTopPlayersOnListView(DBHelper dbHelper) {
        arrAdapter = new ArrayAdapter<PlayerStatistic>(this, android.R.layout.simple_list_item_1, dbHelper.getEveryPlayer());
        lv.setAdapter(arrAdapter);
    }

}