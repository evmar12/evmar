package com.agendaapp.agendapediatrica;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.agendaapp.agendapediatrica.Hijos_Esquema.HijosEntry;
/**
 * Created by Rocio Note on 09/03/2017.
 */

public class HijosDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Datos_hijos.db";

    public HijosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + HijosEntry.TABLE_NAME + " ("
                + HijosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HijosEntry.ID + " TEXT NOT NULL,"
                + HijosEntry.NOMBRE + " TEXT NOT NULL,"
                + HijosEntry.FECHA_NACIMIENTO + " TEXT NOT NULL,"
                + HijosEntry.SEXO + " TEXT NOT NULL,"
                + "UNIQUE (" + Hijos_Esquema.HijosEntry.ID + "))");
        // Insertar datos ficticios para prueba inicial
        mockData(db);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase ) {
        mockHijos(sqLiteDatabase, new Hijos("Carlos Perez", "25/10/2016", "MAS"));
        mockHijos(sqLiteDatabase, new Hijos("Lorena Perez", "25/01/2011", "FEM"));
    }

    public long mockHijos(SQLiteDatabase db, Hijos hijos) {
        return db.insert(
                HijosEntry.TABLE_NAME,
                null,
                hijos.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //NO HAY OPERACIONES
    }

    /**
     * Usa getWritableDatabase() para obtener el manejador de la base de datos para
     * operaciones de escritura. En cuestiones de lectura usa getReadableDatabase()
     * @param hijos
     * @return
     */
    public long saveHijos (Hijos hijos){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                HijosEntry.TABLE_NAME,
                null,
                hijos.toContentValues());
    }

    public Cursor getAllHijos() {
        return getReadableDatabase()
                .query(
                        HijosEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getHijosById(String hijosId) {
        Cursor c = getReadableDatabase().query(
                HijosEntry.TABLE_NAME,
                null,
                HijosEntry.ID + " LIKE ?",
                new String[]{hijosId},
                null,
                null,
                null);
        return c;
    }
    public int deleteHijos(String hijosId) {
        return getWritableDatabase().delete(
                HijosEntry.TABLE_NAME,
                HijosEntry.ID + " LIKE ?",
                new String[]{hijosId});
    }
}
