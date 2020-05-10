package etu.ihm.citycleaner.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import etu.ihm.citycleaner.ui.groups.Group;
import etu.ihm.citycleaner.ui.mytrashs.Trash;


public class GroupManager {

    private static final String TABLE_NAME = "groups";
    public static final String KEY_ID="id";
    public static final String KEY_NAME="name";
    public static final String CREATE_TABLE_GROUP = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
            " (" +
            " "+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " "+KEY_NAME+" TEXT NOT NULL" +
            ");";
    private MySQLite mySQLiteBase; // file manager for the SQLite base
    private SQLiteDatabase db;

    public GroupManager(Context context) {
        this.mySQLiteBase = MySQLite.getInstance(context);
    }

    public void open() {
        this.db = mySQLiteBase.getWritableDatabase();
    }

    public void close() {
        this.db.close();
    }

    public long addGroup(String groupName) {
        // Adding new group in the table

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, groupName);

        // insert() returns the new group id or -1 in case of error
        return this.db.insert(TABLE_NAME,null,values);
    }

    public int updateGroup(Group group) {
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.getName());

        String where = KEY_ID+" = ?";
        String[] whereArgs = {group.getId() + ""};

        return this.db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int removeGroup(int groupId) {
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID+" = ?";
        String[] whereArgs = {groupId + ""};

        return this.db.delete(TABLE_NAME, where, whereArgs);
    }

    public Group getGroup(int id) {

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID+"="+id, null);

        return getGroupFromCursor(c);
    }

    public ArrayList<Group> getGroups() {
        ArrayList<Group> res = new ArrayList<>();
        // select all the groups of the table
        Cursor c = this.db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if (c.moveToFirst())
        {
            do {
                res.add(getGroupFromCursor(c));
            }
            while (c.moveToNext());
        }
        // closing cursor
        c.close();

        return res;
    }

    private Group getGroupFromCursor(Cursor c) {
        if (c != null && c.moveToFirst()) {
            int groupId;
            String groupName;

            groupId = c.getInt(c.getColumnIndex(KEY_ID));
            groupName = c.getString(c.getColumnIndex(KEY_NAME));
            c.close();
            return new Group(groupId, groupName, new ArrayList<Trash>());
        }
        return null;
    }
}
