package com.coursera.mascotasrecycleyview.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.coursera.mascotasrecycleyview.pojos.Mascota;
import com.coursera.mascotasrecycleyview.utils.MascotaAdaptador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseDatos extends SQLiteOpenHelper {

    private Context context;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaMascota = "Create table " + ConstantesBaseDatos.TABLE_MASCOTAS + " ("+
                                        ConstantesBaseDatos.TABLE_MASCOTAS_ID + " Integer primary key autoincrement, " +
                                        ConstantesBaseDatos.TABLE_MASCOTAS_NOMBRE + " text, " +
                                        ConstantesBaseDatos.TABLE_MASCOTAS_FOTO + " integer, " +
                                        ConstantesBaseDatos.TABLE_MASCOTAS_LIKES + " text " +
                                         ")";

        String queryCrearTablaLikesMascotas = "Create table " + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS + " ("+
                                        ConstantesBaseDatos.TABLE_LIKES_MASCOTAS_ID + " Integer primary key autoincrement, " +
                                        ConstantesBaseDatos.TABLE_LIKES_MASCOTAS_ID_MASCOTA + " integer, " +
                                        ConstantesBaseDatos.TABLE_LIKES_MASCOTAS_NUMERO_LIKES + " integer, " +
                                        "foreign key (" + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS_ID_MASCOTA + " ) " +
                                        "references " + ConstantesBaseDatos.TABLE_MASCOTAS + "(" + ConstantesBaseDatos.TABLE_MASCOTAS_ID + ")" +
                                        ")";

        db.execSQL(queryCrearTablaMascota);
        db.execSQL(queryCrearTablaLikesMascotas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + ConstantesBaseDatos.TABLE_MASCOTAS);
        db.execSQL("Drop table if exist " + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS);

        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Mascota> obtenerTodosLasMascotas(boolean ordenar) {
        ArrayList<Mascota> mascotas = new ArrayList<>();

        String query = "Select * from " + ConstantesBaseDatos.TABLE_MASCOTAS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            Mascota mascotaActual = new Mascota();
            mascotaActual.setId(registros.getInt(0));
            mascotaActual.setNombre(registros.getString(1));
            mascotaActual.setFoto(registros.getInt(2));

            String queryLikes = "Select count (" + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS_NUMERO_LIKES + ") as likes" +
                    " From " + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS +
                    " Where " + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS_ID_MASCOTA + "=" + mascotaActual.getId();

            Cursor registroLikes = db.rawQuery(queryLikes, null);

            if (registroLikes.moveToNext()) {
                mascotaActual.setLikes(registroLikes.getInt(0));
            } else {
                mascotaActual.setLikes(0);
            }

            mascotas.add(mascotaActual);
        }

        if (ordenar) {
            final ArrayList<Mascota> mascotasAux = new ArrayList<>();
            Collections.sort(mascotas, new Comparator<Mascota>() {
                @Override
                public int compare(Mascota m1, Mascota m2) {
                    return new Integer(m2.getLikes()).compareTo(new Integer(m1.getLikes()));
                }
            });

            List<Object> tempList = mascotas.stream().limit(5).collect(Collectors.toList());
            tempList.forEach(new Consumer<Object>() {
                @Override
                public void accept(Object e) {
                    mascotasAux.add((Mascota) e);
                }
            });
            db.close();
            return mascotasAux;
        }
        db.close();
        return mascotas;
    }

    public void insertarMascota(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_MASCOTAS, null, contentValues);
        db.close();
    }

    public void insertarLikeMacota(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_LIKES_MASCOTAS, null, contentValues);
        db.close();
    }

    public int obtenerLikesMascota(Mascota mascota) {
        int likes = 0;
        String query = "Select count (" + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS_NUMERO_LIKES + ") " +
                " From " + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS +
                " Where " + ConstantesBaseDatos.TABLE_LIKES_MASCOTAS_ID_MASCOTA + "=" + mascota.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);
        if(registros.moveToNext()) {
            likes = registros.getInt(0);
        }
        db.close();
        return likes;
    }
}
