package etu.ihm.citycleaner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class TrashManager {

    private static final String TABLE_NAME = "trash";
    public static final String KEY_ID = "id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_CLUTTER = "clutter";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_DATE = "date";
    public static final String KEY_IMAGE_URL = "image";

    public static final String CREATE_TABLE_TRASH = "CREATE TABLE " + TABLE_NAME +
            " (" +
            " "+KEY_ID+" INTEGER primary key," +
            " "+KEY_TYPE+" INTEGER," +
            " "+KEY_CLUTTER+" INTEGER," +
            " "+KEY_LATITUDE+" REAL," +
            " "+KEY_LONGITUDE+" REAL," +
            " "+KEY_DATE+" TEXT," +
            " "+KEY_IMAGE_URL+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public TrashManager(Context context){
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addTrash(Trash trash) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, trash.getType());
        values.put(KEY_CLUTTER, trash.getClutter());
        values.put(KEY_LATITUDE, trash.getLatitude());
        values.put(KEY_LONGITUDE, trash.getLongitude());
        values.put(KEY_DATE, trash.getDate());
        values.put(KEY_IMAGE_URL, trash.getImage());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modTrash(Trash trash) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, trash.getType());
        values.put(KEY_CLUTTER, trash.getClutter());
        values.put(KEY_LATITUDE, trash.getLatitude());
        values.put(KEY_LONGITUDE, trash.getLongitude());
        values.put(KEY_DATE, trash.getDate());
        values.put(KEY_IMAGE_URL, trash.getImage());

        String where = KEY_ID+" = ?";
        String[] whereArgs = {trash.getId()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supAnimal(Trash trash) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID+" = ?";
        String[] whereArgs = {trash.getId()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Trash getTrash(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        Trash t = new Trash();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID+"="+id, null);
        if (c.moveToFirst()) {
            t.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            t.setClutter(c.getInt(c.getColumnIndex(KEY_CLUTTER)));
            t.setLatitude(c.getDouble(c.getColumnIndex(KEY_LATITUDE)));
            t.setLongitude(c.getDouble(c.getColumnIndex(KEY_LONGITUDE)));
            t.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
            t.setImage(c.getString(c.getColumnIndex(KEY_IMAGE_URL)));
            c.close();
        }

        return t;
    }

    public Cursor getTrashscursor() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

    public ArrayList<Trash> getTrashs() {
        ArrayList<Trash> trashList = new ArrayList<>();
        Cursor c = this.getTrashscursor();
        if (c.moveToFirst())
        {
            do {
                Trash t = new Trash();
                t.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                t.setClutter(c.getInt(c.getColumnIndex(KEY_CLUTTER)));
                t.setLatitude(c.getDouble(c.getColumnIndex(KEY_LATITUDE)));
                t.setLongitude(c.getDouble(c.getColumnIndex(KEY_LONGITUDE)));
                t.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                t.setImage(c.getString(c.getColumnIndex(KEY_IMAGE_URL)));
                trashList.add(t);
            }
            while (c.moveToNext());
        }
        c.close(); // fermeture du curseur
        return trashList;
    }
}
