package com.example.sqlitedbms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "employee db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "employee";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String NUMBER_COL = "number";


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + NUMBER_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewEmployee(Model model) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, model.getName());
        values.put(NUMBER_COL, model.getPhoneNumber());


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // code to get all contacts in a list view
    public List<Model> getAllDetails() {
        List<Model> modelList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setID(Integer.parseInt(cursor.getString(0)));
                model.setName(cursor.getString(1));
                model.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                modelList.add(model);
            } while (cursor.moveToNext());
        }

        // return model list
        return modelList;
    }

    // code to update the single contact
    public int updateDetails(String id, String name1, String phone1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME_COL, name1);
        values.put(NUMBER_COL, phone1);

        // updating row
        return db.update(TABLE_NAME, values, ID_COL + " = ?",
                new String[] { String.valueOf(id) });
    }

    // Deleting single contact
    public void deleteDetails(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COL + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

}