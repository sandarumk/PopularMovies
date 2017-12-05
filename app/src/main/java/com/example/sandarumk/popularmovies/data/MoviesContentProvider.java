package com.example.sandarumk.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by dinu on 12/5/17.
 */

public class MoviesContentProvider extends ContentProvider {
    private MovieDBHelper mMoviesDBHelper;
    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;

    private static final UriMatcher sURIMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesContract.AUTHORITY,MoviesContract.PATH_MOVIES,MOVIES);
        uriMatcher.addURI(MoviesContract.AUTHORITY,MoviesContract.PATH_MOVIES +"/#",MOVIES_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mMoviesDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mMoviesDBHelper.getWritableDatabase();
        int match = sURIMatcher.match(uri);
        Cursor returnCursor;
        switch (match){
            case MOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mselection = MoviesContract.FavouriteMovies.COLUMN_NAME_MOVIE_ID+" =?";
                String[] mSelectionArgs = new String[]{id};

                returnCursor=db.query(MoviesContract.FavouriteMovies.TABLE_NAME,
                       projection,
                       mselection,
                       mSelectionArgs,
                       null,
                       null,
                       sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("URI uri"+ uri);

        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mMoviesDBHelper.getWritableDatabase();
        int match = sURIMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case MOVIES:
                long id = db.insert(MoviesContract.FavouriteMovies.TABLE_NAME,null,values);
                if(id > 0){
                    returnUri = ContentUris.withAppendedId(uri,id);

                }else{
                    throw new SQLException("Failed to insert");
                }
                break;
            default:
                throw new UnsupportedOperationException("Uri "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
