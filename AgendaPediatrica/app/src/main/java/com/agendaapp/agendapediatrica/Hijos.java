package com.agendaapp.agendapediatrica;

import android.content.ContentValues;

import com.agendaapp.agendapediatrica.Hijos_Esquema.HijosEntry;

import java.util.UUID;
/**
 * Created by Rocio Note on 09/03/2017.
 */

/**
 * Entidad Hijos
 */
public class Hijos {
    private String id;
    private String nombre;
    private String  fecha_nac;
    private String sexo;


    public Hijos (String nombre, String fecha_nac, String sexo){
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.fecha_nac = fecha_nac;
        this.sexo = sexo;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(HijosEntry.ID, id);
        values.put(HijosEntry.NOMBRE, nombre);
        values.put(HijosEntry.FECHA_NACIMIENTO, fecha_nac);
        values.put(HijosEntry.SEXO, sexo);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getFecha_nac(){
        return fecha_nac;
    }

    public String getSexo(){
        return sexo;
    }


}