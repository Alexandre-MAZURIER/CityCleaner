package etu.ihm.citycleaner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import etu.ihm.citycleaner.ui.groups.Group;


public class GroupManager {

    private static final String TABLE_NAME = "groups";
    public static final String KEY_ID="id";
    public static final String KEY_NAME="name";
    public static final String CREATE_TABLE_GROUP = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID+" INTEGER primary key," +
            " "+KEY_NAME+" TEXT" +
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

    public long addGroup(Group group) {
        // Adding new group in the table

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.getName());

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

    public int removeGroup(Group group) {
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID+" = ?";
        String[] whereArgs = {group.getId() + ""};

        return this.db.delete(TABLE_NAME, where, whereArgs);
    }

    public Group getGroup(int id) {

        Group group = null;
        int groupId;
        String groupName;

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID+"="+id, null);
        if (c.moveToFirst()) {
            groupId = c.getInt(c.getColumnIndex(KEY_ID));
            groupName = c.getString(c.getColumnIndex(KEY_NAME));
            c.close();

            group = new Group(groupId, groupName, null);
        }

        return group;
    }

    public Cursor getGroups() {
        // select all the groups of the table
        return this.db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
