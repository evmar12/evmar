package com.agendaapp.agendapediatrica;

/**
 * Created by Rocio Note on 09/03/2017.
 */

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para los hijos
 */
public class Hijos_Esquema {
    public static abstract class HijosEntry implements BaseColumns{
        public static final String TABLE_NAME ="DATOS_HIJOS";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String FECHA_NACIMIENTO = "fecha_nac";
        public static final String SEXO = "sexo";

    }

}
