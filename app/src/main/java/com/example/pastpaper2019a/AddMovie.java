package com.example.pastpaper2019a;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pastpaper2019a.database.DBHandler;

public class AddMovie extends AppCompatActivity {

    EditText addMovieName, addMovieYear;
    Button buttonAddMovie;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        // hooks
        addMovieName = findViewById(R.id.addMovieName);
        addMovieYear = findViewById(R.id.addMovieYear);
        buttonAddMovie = findViewById(R.id.buttonAddMovie);
        db = new DBHandler(this);
        SQLiteDatabase movieDb = db.getReadableDatabase();


        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieName = addMovieName.getText().toString();
                String movieYear = addMovieYear.getText().toString();




                if (!TextUtils.isEmpty(movieName) && !TextUtils.isEmpty(movieYear) ){

                    boolean status = db.addMovie(movieName,Integer.parseInt(movieYear));
                    addMovieName.setText("");
                    addMovieYear.setText("");
                    if (status)
                        Toast.makeText(getApplicationContext(), "movie added successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "error occurred", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "all the fields required", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
