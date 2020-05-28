package com.example.pastpaper2019a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pastpaper2019a.database.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class MovieOverview extends AppCompatActivity {

    TextView movieName, textViewRatingValue;
    SeekBar movieSeekBar;
    EditText movieComment;
    Button buttonSubmitComment;
    ListView commentList;
    DBHandler db;
    int seekBarValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);


        movieName = findViewById(R.id.movieName);
        textViewRatingValue = findViewById(R.id.textViewRatingValue);
        movieSeekBar = findViewById(R.id.movieSeekBar);
        movieComment = findViewById(R.id.movieComment);
        buttonSubmitComment = findViewById(R.id.buttonSubmitComment);
        commentList = findViewById(R.id.commentList);
        db = new DBHandler(this);
        SQLiteDatabase movieDb = db.getReadableDatabase();





        // seek bar
        movieSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue = progress;
                Toast.makeText(getApplicationContext(),"seek bar progress: "+ progress, Toast.LENGTH_SHORT).show();
                textViewRatingValue.setText(String.valueOf(seekBarValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seek bar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });


        // get data
        List arrItems = new ArrayList<>();
        arrItems = db.viewComments();



        // get values from the intent
        Intent intent = getIntent();
        final String movieNameExtra = intent.getStringExtra("movieNameExtra");
        movieName.setText(movieNameExtra);



        // comment list
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, arrItems);
        commentList.setAdapter(adapter);

        buttonSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = movieComment.getText().toString();
                String rating= textViewRatingValue.getText().toString();



                if (!TextUtils.isEmpty(comment) ){

                    boolean status = db.addComment(movieNameExtra, comment, seekBarValue);
                    movieComment.setText("");

                    if (status)
                        Toast.makeText(getApplicationContext(), "comment added successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "error occurred", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "all the fields required", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
