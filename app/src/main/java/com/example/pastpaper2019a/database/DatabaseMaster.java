package com.example.pastpaper2019a.database;

import android.provider.BaseColumns;

public final class DatabaseMaster {

    private DatabaseMaster() {
    }

    public class Users implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String TABLE_COLUMN_USERNAME = "username";
        public static final String TABLE_COLUMN_PASSWORD = "password";
        public static final String TABLE_COLUMN_USER_TYPE = "userType";

    }

    public class Movies implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static final String TABLE_COLUMN_MOVIE_NAME = "movieName";
        public static final String TABLE_COLUMN_MOVIE_YEAR = "movieYear";
    }

    public class Comments implements BaseColumns {
        public static final String TABLE_NAME = "comments";
        public static final String TABLE_COLUMN_MOVIE_NAME = "movieName";
        public static final String TABLE_COLUMN_MOVIE_RATING = "movieRating";
        public static final String TABLE_COLUMN_MOVIE_COMMENTS = "movieComments";
    }
}
