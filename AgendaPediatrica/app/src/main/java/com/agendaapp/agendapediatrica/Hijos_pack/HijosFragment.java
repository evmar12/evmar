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
import android.widget.AdapterView;
import android.widget.ListView;
import com.agendaapp.agendapediatrica.Hijos_Esquema.HijosEntry;
import com.agendaapp.agendapediatrica.HijosDBHelper;
import com.agendaapp.agendapediatrica.R;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HijosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HijosFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_HIJOS = 2;

    private HijosDBHelper mHijosDbHelper;

    private ListView mHijosList;
    private HijosCursosAdapter mHijossAdapter;
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
        View root = inflater.inflate(R.layout.fragment_hijos, container, false);

        // Referencias UI
        mHijosList = (ListView) root.findViewById(R.id.hijos_list);
        mHijossAdapter = new HijosCursosAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mHijosList.setAdapter(mHijossAdapter);

        // Eventos

        // Temporalmente se deshabilitan los escuchas para mostrar detalle y agregar nuevo hijo
        /*
        mHijosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mHijossAdapter.getItem(i);
                String currentHijoId = currentItem.getString(
                        currentItem.getColumnIndex(HijosEntry.ID));

                showDetailScreen(currentHijoId);
            }
        });
        */

        /*
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });
        */


        getActivity().deleteDatabase(mHijosDbHelper.DATABASE_NAME);

        // Instancia de helper
        mHijosDbHelper = new HijosDBHelper(getActivity());

        // Carga de datos
        loadHijos();

        return root;
    }

    private class HijosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {

            return mHijosDbHelper.getAllHijos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mHijossAdapter.swapCursor(cursor);
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
