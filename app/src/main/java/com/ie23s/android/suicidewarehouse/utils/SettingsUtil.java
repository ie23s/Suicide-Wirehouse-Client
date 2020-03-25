package com.ie23s.android.suicidewarehouse.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingsUtil extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public SettingsUtil(Context context) {
        // конструктор суперкласса
        super(context, "SuicideWireHouseSettings", null, 1);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("CREATE TABLE `android_settings` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "`name` varchar(255) NOT NULL," +
                "`value` varchar(255) DEFAULT NULL," +
                "UNIQUE (name)); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 10010001:
                /////dddd
            case 10020111:
                //ddddd
            case 10020936:
                ///
        }
    }

    private String getData(String name) {
        Cursor cursor = db.rawQuery(
                "SELECT `value` FROM `android_settings` WHERE `name` = ?",
                new String[]{name});
        cursor.moveToNext();
        String data = cursor.getString(0);
        cursor.close();
        return data;
    }

    private void setData(String name, String value) {
        db.execSQL("INSERT INTO `android_settings`(`name`, `value`) VALUES (?, ?)",
                new String[]{name, value});
    }

    public String getString(String name) {
        return getData(name);
    }

    private void setString(String name, String value) {
        setData(name, value);
    }

    public int getInt(String name) {
        return Integer.parseInt(getData(name));
    }

    private void setInt(String name, int value) {
        setData(name, Integer.toString(value));
    }

    public boolean getBoolean(String name) {
        return Boolean.parseBoolean(getData(name));
    }

    private void setBoolean(String name, boolean value) {
        setData(name, Boolean.toString(value));
    }

}