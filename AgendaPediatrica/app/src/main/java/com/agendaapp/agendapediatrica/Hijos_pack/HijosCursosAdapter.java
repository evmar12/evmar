package com.agendaapp.agendapediatrica.Hijos_pack;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.agendaapp.agendapediatrica.Hijos_Esquema.HijosEntry;
import com.agendaapp.agendapediatrica.R;

/**
 * Created by Rocio Note on 10/03/2017.
 */

public class HijosCursosAdapter extends CursorAdapter {
    public HijosCursosAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_hijos, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Referencias UI
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);

        //get valores
        String name = cursor.getString(cursor.getColumnIndex(HijosEntry.NOMBRE));
    }
}
