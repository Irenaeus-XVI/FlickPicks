package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "UserData.db";
    private static final int DB_VERSION = 2;
    private static final String TABLE_NAME = "Users";
    private static final String NAME = "name";

    private static final String EMAIL = "email";
    private static final String HASHEDPW = "hashedpw";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + NAME + " TEXT ,"
                + EMAIL + " TEXT PRIMARY KEY,"
                + HASHEDPW + " TEXT)";

        db.execSQL(query);
    }

    public void addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME, user.getName());
        values.put(EMAIL, user.getEmail());
        values.put(HASHEDPW, user.getHashedPW());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        NAME, EMAIL, HASHEDPW}, EMAIL + "=?",
                new String[]{String.valueOf(email)}, null, null, null, null);
        User user = null;
        // User not found in database so return null
        if (cursor == null || cursor.getCount() == 0)
            return null;

        // Otherwise move to first row, assign it to user and return it
        cursor.moveToFirst();

        user = new User(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));

        // Close cursor to avoid leakage
        cursor.close();

        return user;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(EMAIL, user.getEmail());
        values.put(HASHEDPW, user.getHashedPW());

        // Updating row
        db.update(TABLE_NAME, values, EMAIL + " = ?",
                new String[]{String.valueOf(user.getEmail())});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
