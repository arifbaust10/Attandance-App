package com.example.attandanceapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String CLASS_TABLE_NAME = "CLASS_TABLE";
    public static final String C_ID = "_CID";
    public static final String CLASS_NAME_KEY = "CLASS_NAME";
    public static final String SUBJECT_NAME_KEY = "SUBJECT_NAME";

    //class table
    private static final String CREATE_CLASS_TABLE = "CREATE TABLE " + CLASS_TABLE_NAME + " ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + CLASS_NAME_KEY + " TEXT NOT NULL, "
            + SUBJECT_NAME_KEY + " TEXT NOT NULL, "
            + "UNIQUE (" + CLASS_NAME_KEY + ", " + SUBJECT_NAME_KEY + "));";
    private static final String DROP_CLASS_TABLE = "DROP TABLE IF EXISTS " + CLASS_TABLE_NAME;
    private static final String SELECT_CLASS_TABLE = "SELECT * FROM " + CLASS_TABLE_NAME;




    // Student table
    private static final String STUDENT_TABLE_NAME = "STUDENT_TABLE";
    public static final String S_ID = "_SID";
    public static final String STUDENT_NAME_KEY = "STUDENT_NAME";
    public static final String STUDENT_ROLL_KEY = "ROLL";

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE " + STUDENT_TABLE_NAME + " (" +
            S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            C_ID + " INTEGER NOT NULL, " +
            STUDENT_NAME_KEY + " TEXT NOT NULL, " +
            STUDENT_ROLL_KEY + " INTEGER, " +
            "FOREIGN KEY (" + C_ID + ") REFERENCES " + CLASS_TABLE_NAME + "(" + C_ID + ")" +
            ");";


    private static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME;

    private static final String SELECT_STUDENT_TABLE = "SELECT * FROM " + STUDENT_TABLE_NAME;


    // STATUS TABLE
    private static final String STATUS_TABLE_NAME = "STATUS_TABLE";
    public static final String STATUS_ID = "_STATUS_ID";
    public static final String DATE_KEY = "STATUS_DATE";
    public static final String STATUS_KEY = "STATUS";


    private static final String CREATE_STATUS_TABLE =
            "CREATE TABLE " + STATUS_TABLE_NAME + "("
            + STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + S_ID + " INTEGER NOT NULL, "
            + DATE_KEY + " DATE NOT NULL, "
            + STATUS_KEY + " TEXT NOT NULL, "
            + "UNIQUE (" + S_ID + ", " + DATE_KEY + "), "
            + "FOREIGN KEY (" + S_ID + ") REFERENCES "+ STUDENT_TABLE_NAME + "( " + S_ID + ")" + ");";

    private static final String DROP_STATUS_TABLE = "DROP TABLE IF EXISTS " + STATUS_TABLE_NAME;
    private static final String SELECT_STATUS_TABLE = "SELECT * FROM " + STATUS_TABLE_NAME + ";";




    public DbHelper(@Nullable Context context) {
        super(context, "Attendance.db", null, VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CLASS_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STATUS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try{
            db.execSQL(DROP_CLASS_TABLE);
            db.execSQL(DROP_STUDENT_TABLE);
            db.execSQL(DROP_STATUS_TABLE);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    long addClass(String className, String subjectName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME_KEY, className);
        values.put(SUBJECT_NAME_KEY, subjectName);
        return database.insert(CLASS_TABLE_NAME, null, values);
    }

    Cursor getClassTable() {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_CLASS_TABLE,null);
    }

    int deleteClass(long cid){
        SQLiteDatabase database = this.getReadableDatabase();
       return database.delete(CLASS_TABLE_NAME, C_ID + "=?", new String[]{String.valueOf(cid)});

    }

    long updateClass(long cid, String className, String subjectName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_NAME_KEY, className);
        values.put(SUBJECT_NAME_KEY, subjectName);
        return database.update(CLASS_TABLE_NAME, values, C_ID + "=?", new String[]{String.valueOf(cid)});
    }


    long addStudent(long cid, int roll, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_ID, cid);
        values.put(STUDENT_ROLL_KEY, roll);
        values.put(STUDENT_NAME_KEY, name);
        return database.insert(STUDENT_TABLE_NAME, null, values);
    }

    // Method to get student data from the database
    Cursor getStudentTable(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(
                STUDENT_TABLE_NAME,    // Table name
                null,                      // Columns (null to select all columns)
                 C_ID+"=?",                // Selection (where clause)
                new String[]{String.valueOf(cid)}, // Selection arguments
                null,                      // Group by
                null,                      // Having
                STUDENT_ROLL_KEY                 // Order by (if required)
        );
    }

    // Method to delete a student record from the database
    int deleteStudent(long sid) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(
                STUDENT_TABLE_NAME,        // Table name
                S_ID +"=?",                  // Where clause (with placeholder)
                new String[]{String.valueOf(sid)} // Where arguments
        );
    }

    // Method to update a student record in the database
    long updateStudent(long sid, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME_KEY, name);  // Column name and value
        return database.update(
                STUDENT_TABLE_NAME,        // Table name
                values,                      // Values to update
                S_ID +"=?",                  // Where clause (with placeholder)
                new String[]{String.valueOf(sid)} // Where arguments
        );
    }


    long addStatus(long sid, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(S_ID, sid);
        values.put(DATE_KEY, date);
        values.put(STATUS_KEY, status);

        return database.insert(STATUS_TABLE_NAME, null, values);
    }


    long updateStatus(long sid, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_KEY, status);
        String whereClause = DATE_KEY + "=? AND " + S_ID + "=?";
        String[] whereArgs = { date, String.valueOf(sid) };

        return database.update(STATUS_TABLE_NAME, values, whereClause, whereArgs);
    }



    String getStatus(long sid, String date) {
        String status = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = DATE_KEY + "='" + date + "' AND " + S_ID + "=" + sid;

        Cursor cursor = database.query(STATUS_TABLE_NAME, null, whereClause, null, null, null, null);
        if (cursor.moveToFirst()) {

            int statusIndex = cursor.getColumnIndexOrThrow(STATUS_KEY);
            status = cursor.getString(statusIndex);
        }

        return status;
    }





}
