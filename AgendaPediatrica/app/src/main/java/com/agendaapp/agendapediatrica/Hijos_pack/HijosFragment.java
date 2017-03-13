package com.agendaapp.agendapediatrica.Hijos_pack;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.agendaapp.agendapediatrica.HijosDBHelper;
import com.agendaapp.agendapediatrica.R;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HijosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HijosFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_HIJOS = 2;

    private HijosDBHelper mHijosDBHelper;

    private ListView mHijosList;
    private HijosCursosAdapter mHijosAdapter;
    private FloatingActionButton mAddButton;

    public HijosFragment() {
        // Required empty public constructor
    }


    public static HijosFragment newInstance() {
        return new HijosFragment();
    }

/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_hijos, container, false);

        //Regerencias UI
        mHijosList = (ListView) root.findViewById(R.id.hijos_list);
        mHijosAdapter = new HijosCursosAdapter(getActivity(),null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Setup
        mHijosList.setAdapter(mHijosAdapter);

        //Instancia de helper
        mHijosList.setAdapter(mHijosAdapter);

        //Carga de datos
        loadHijos();

        return root;
    }

    private class HijosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mHijosDBHelper.getAllHijos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mHijosAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

    private void loadHijos(){
        //Cargar Datos
        new HijosLoadTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

    }
}
