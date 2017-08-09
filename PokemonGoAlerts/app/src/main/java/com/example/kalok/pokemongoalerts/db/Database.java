package com.example.kalok.pokemongoalerts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kalok.pokemongoalerts.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kalok on 9-8-2017.
 */

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pokemonGoAlerts.db";
    private static final String TABLE_USERS = "users";

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PASSWORD_CONFIRM = "password_confirm";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS "
                + TABLE_USERS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USERNAME + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_PASSWORD + " TEXT, "
                + KEY_PASSWORD_CONFIRM + " TEXT);";
        db.execSQL(createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteUsersTable = "DROP TABLE IF EXISTS "+TABLE_USERS;
        db.execSQL(deleteUsersTable);

        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,user.getId());
        contentValues.put(KEY_USERNAME,user.getUsername());
        contentValues.put(KEY_EMAIL,user.getEmail());
        contentValues.put(KEY_PASSWORD,user.getPassword());
        contentValues.put(KEY_PASSWORD_CONFIRM,user.getPasswordConfirm());

        database.insert(TABLE_USERS, null, contentValues);
        database.close();
    }

    public List<User> getAllUsers(){
        List<User> usersList = new ArrayList<User>();

        String selectUserQuery = "SELECT * FROM " + TABLE_USERS + " ORDER BY " + KEY_ID + "DESC;";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectUserQuery, null);

        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setPasswordConfirm(cursor.getString(4));
            }while(cursor.moveToNext());
        }

        return usersList;
    }
}
