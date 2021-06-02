package com.practice.customlistviewandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Hero> heroList;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.heroList = new ArrayList<>();
        this.listView = (ListView) findViewById(R.id.listView);

        this.heroList.add(new Hero(R.drawable.spiderman, "Spiderman", "Avengers"));
        this.heroList.add(new Hero(R.drawable.joker, "Joker", "Injustice Gang"));
        this.heroList.add(new Hero(R.drawable.ironman, "Iron Man", "Avengers"));
        this.heroList.add(new Hero(R.drawable.doctorstrange, "Doctor Strange", "Avengers"));
        this.heroList.add(new Hero(R.drawable.captainamerica, "Captain Amerika", "Avengers"));
        this.heroList.add(new Hero(R.drawable.batman, "Batman", "Injustice Gang"));

        MyListAdapter adapter = new MyListAdapter(this, R.layout.my_costume_list, heroList);

        listView.setAdapter(adapter);
    }
}