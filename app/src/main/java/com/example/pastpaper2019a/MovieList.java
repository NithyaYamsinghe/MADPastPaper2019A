package com.example.pastpaper2019a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pastpaper2019a.database.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class MovieList extends AppCompatActivity {

    TextView movieListItem;
    ListView movieList;
    DBHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // hooks
        movieList = (ListView) findViewById(R.id.movieList);
        movieListItem = (TextView) findViewById(R.id.movieListItem);
        db = new DBHandler(this);
        SQLiteDatabase movieDb = db.getReadableDatabase();

        // get data from view movies method and store in array ;ist
        List arrListItems = new ArrayList<>();
        arrListItems = db.viewMovies();

        // pass the layout of the single item
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_view,arrListItems);
        movieList.setAdapter(adapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=adapter.getItem(position);
                Toast.makeText(getApplicationContext(),value, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MovieOverview.class);
                intent.putExtra("movieNameExtra", value);
                startActivity(intent);
            }
        });



    }
}
