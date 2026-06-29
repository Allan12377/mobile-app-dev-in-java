package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class handles all database operations like creating the database, creating tables, and CRUD operations.
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 1;

    // Table Name and Columns
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    // Constructor: Creates the database when the helper is initialized
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This method is called when the database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    // This method is called when the database needs to be upgraded (e.g., version change).
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the older table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to add a new user to the database
    public void addUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name); // Put name into the values object

        db.insert(TABLE_USERS, null, values); // Insert the row
        db.close(); // Always close the database connection
    }

    // Method to get all users from the database as a single String
    public String getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase(); // Get readable database
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        
        StringBuilder builder = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                // Get name from the cursor and append to the string
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                builder.append(name).append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return builder.toString();
    }
}
