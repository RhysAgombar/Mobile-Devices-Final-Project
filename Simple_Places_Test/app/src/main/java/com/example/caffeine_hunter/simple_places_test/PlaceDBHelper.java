package com.example.caffeine_hunter.simple_places_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 100520095 on 11/13/2016.
 */

public class PlaceDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "places.db";

    private static final String CREATE_STATEMENT = "" +
            "create table places(" +
            "  _id integer primary key," +
            "  name text not null," +
            "  address text not null," +
            "  lat real not null," +
            "  lng real not null," +
            "  photoRef text null," +
            "  iconURL text null," +
            "  visited text null)";

    private static final String DROP_STATEMENT = "" +
            "drop table places";

    public PlaceDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        db.execSQL(CREATE_STATEMENT);
    }

    public void emptyDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("places", "", new String[] {});
    }

    public void deleteElementByID(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("places", "_id = ?", new String[] { ""+id });
    }

    public void addNewElement(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", place.getId());
        values.put("name", place.getName());
        values.put("lat", place.getLat());
        values.put("lng", place.getLng());
        values.put("photoRef", place.getPhotoRef());
        values.put("iconURL", place.getIconURL());
        values.put("visited", place.isVisited());
        db.insert("places", null, values);
    }

    public List<Place> getAllElements() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Place> results = new ArrayList<>();

        String[] columns = new String[] {"_id",
                "name",
                "lat",
                "lng",
                "photoRef",
                "iconURL",
                "visited"};
        String where = "";  // all contacts
        String[] whereArgs = new String[] {};
        String groupBy = "";  // no grouping
        String groupArgs = "";
        String orderBy = "name";

        Cursor cursor = db.query("places", columns, where, whereArgs,
                groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            float lat = cursor.getFloat(3);
            float lng = cursor.getFloat(4);
            String photoRef = cursor.getString(5);
            String iconURL = cursor.getString(6);
            String visited = cursor.getString(7);

            Place place = new Place(id, name, address, lat, lng, photoRef, iconURL, Boolean.valueOf(visited));
            results.add(place);

            cursor.moveToNext();
        }
        return results;
    }

    public boolean updateElement(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("_id", place.getId());
        values.put("name", place.getName());
        values.put("lat", place.getLat());
        values.put("lng", place.getLng());
        values.put("photoRef", place.getPhotoRef());
        values.put("iconURL", place.getIconURL());
        values.put("visited", place.isVisited());

        int numRows = db.update("places",
                values,
                "_id = ?",
                new String[] {""+place.getId()});
        return (numRows == 1);
    }
}
