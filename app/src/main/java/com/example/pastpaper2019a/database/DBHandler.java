package com.example.pastpaper2019a.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES_USERS = "CREATE TABLE " + DatabaseMaster.Users.TABLE_NAME + " (" +
                DatabaseMaster.Users._ID + " INTEGER PRIMARY KEY," +
                DatabaseMaster.Users.TABLE_COLUMN_USERNAME + " TEXT," +
                DatabaseMaster.Users.TABLE_COLUMN_PASSWORD + " TEXT," +
                DatabaseMaster.Users.TABLE_COLUMN_USER_TYPE + " TEXT)";

        String SQL_CREATE_ENTRIES_MOVIES = "CREATE TABLE " + DatabaseMaster.Movies.TABLE_NAME + " (" +
                DatabaseMaster.Movies._ID + " INTEGER PRIMARY KEY," +
                DatabaseMaster.Movies.TABLE_COLUMN_MOVIE_NAME + " TEXT," +
                DatabaseMaster.Movies.TABLE_COLUMN_MOVIE_YEAR + " INTEGER)";


        String SQL_CREATE_ENTRIES_COMMENTS = "CREATE TABLE " + DatabaseMaster.Comments.TABLE_NAME + " (" +
                DatabaseMaster.Comments._ID + " INTEGER PRIMARY KEY," +
                DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_NAME + " TEXT," +
                DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_RATING + " INTEGER," +
                DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_COMMENTS + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_MOVIES);
        db.execSQL(SQL_CREATE_ENTRIES_COMMENTS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseMaster.Users.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseMaster.Movies.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseMaster.Comments.TABLE_NAME);

    }

    //  register method
    public boolean register(String username, String password) {

        // get data repository in write mode
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // create new map of values where column names are keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Users.TABLE_COLUMN_USERNAME, username);
        values.put(DatabaseMaster.Users.TABLE_COLUMN_PASSWORD, password);

        // insert data to the database assign output value in variable
        long newRowId = sqLiteDatabase.insert(DatabaseMaster.Users.TABLE_NAME, null, values);

        if (newRowId == -1)
            return false;
        else
            return true;
    }

    // login method
    public boolean loginUser(String username, String password) {

        // get data repository in readable mode
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        boolean result = false;

        // projection to select which column data needed to be retrieve
        String projection[] = {
                DatabaseMaster.Users._ID,
                DatabaseMaster.Users.TABLE_COLUMN_USERNAME,
                DatabaseMaster.Users.TABLE_COLUMN_PASSWORD
        };

        // select columns from sql database
        Cursor cursor = sqLiteDatabase.query(
                DatabaseMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String usernameSelected = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Users.TABLE_COLUMN_USERNAME));
            String passwordSelected = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Users.TABLE_COLUMN_PASSWORD));

            if (usernameSelected.equals(username) && passwordSelected.equals(password)) {
                result = true;
            } else if (username.equals("admin"))
                result = true;
            else
                result = false;
        }
        cursor.close();
        return result;


    }

    // add movie method
    public boolean addMovie(String movieName, int movieYear) {

        // get data repository in write mode
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // create a new map of values where columns names are keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Movies.TABLE_COLUMN_MOVIE_NAME, movieName);
        values.put(DatabaseMaster.Movies.TABLE_COLUMN_MOVIE_YEAR, movieYear);

        // insert data in to the database and return the output value
        long newRowId = sqLiteDatabase.insert(DatabaseMaster.Movies.TABLE_NAME, null, values);
        if (newRowId == -1)
            return false;
        else
            return true;

    }

    // view movies method
    public List viewMovies() {

        // get data repository in readable mode
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // create a projection to identify the columns needed to be retrieve
        String projection[] = {DatabaseMaster.Movies._ID, DatabaseMaster.Movies.TABLE_COLUMN_MOVIE_NAME, DatabaseMaster.Movies.TABLE_COLUMN_MOVIE_YEAR};

        // sort order
        String sortOrder = DatabaseMaster.Movies.TABLE_COLUMN_MOVIE_NAME + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                DatabaseMaster.Movies.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder,
                null
        );

        List movieNames = new ArrayList<>();

        while (cursor.moveToNext()) {

            String movieNameFound = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Movies.TABLE_COLUMN_MOVIE_NAME));
            movieNames.add(movieNameFound);
        }
        cursor.close();
        return movieNames;
    }

    // add comment method
    public boolean addComment(String movieName, String comment, int rating) {

        // get data repository in write mode
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // create a new map of values where columns names are keys
        ContentValues values = new ContentValues();
        values.put(DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_NAME, movieName);
        values.put(DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_RATING, rating);
        values.put(DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_COMMENTS, comment);

        // insert data in to the database and return the output value
        long newRowId = sqLiteDatabase.insert(DatabaseMaster.Comments.TABLE_NAME, null, values);
        if (newRowId == -1)
            return false;
        else
            return true;

    }

    // view comments method
    public List viewComments() {

        // get data repository in readable mode
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // create a projection to identify the columns needed to be retrieve
        String projection[] = {DatabaseMaster.Comments._ID, DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_NAME,
                DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_RATING, DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_COMMENTS};

        // sort order
        String sortOrder = DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_NAME + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                DatabaseMaster.Comments.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder,
                null
        );

        List comments = new ArrayList<>();

        while (cursor.moveToNext()) {

            String commentFound = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseMaster.Comments.TABLE_COLUMN_MOVIE_COMMENTS));
            comments.add(commentFound);
        }
        cursor.close();
        return comments;
    }
}
