package com.agendaapp.agendapediatrica.Hijos_pack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.agendaapp.agendapediatrica.R;

public class HijosActivity extends AppCompatActivity {

    public static final String EXTRA_HIJOS_ID = "extra_hijos_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijos);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        HijosFragment fragment = (HijosFragment)
                getSupportFragmentManager().findFragmentById(R.id.hijos_container);

        if (fragment == null) {
            fragment = HijosFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.hijos_container, fragment)
                    .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Agregar Hijo no implementado aún.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
