package com.pankecho.ia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String m_Text = "";
    private AlertDialog.Builder builder;
    private EditText input;
    private Secciones s;
    ArrayList<String[]> patrones;
    String[][] matriz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(this);
        input = new EditText(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final PixelViewGrid pvg = new PixelViewGrid(this);
        pvg.setNumColumns(20);
        pvg.setNumRows(40);
        pvg.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ConstraintLayout l = (ConstraintLayout) findViewById(R.id.lay);
        l.addView(pvg);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = new Secciones(pvg.getMatriz(),pvg.getNumRows(),pvg.getNumColumns());
            }
        });

        FloatingActionButton entrenar = (FloatingActionButton) findViewById(R.id.entrenar);
        entrenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream br = null;
                patrones = new ArrayList<String[]>();
                try{
                    br = getResources().openRawResource(R.raw.patrones);
                    BufferedReader lector = new BufferedReader(new InputStreamReader(br));
                    String linea = lector.readLine();
                    while(linea != null){
                        String[] campos = linea.split(",");
                        patrones.add(campos);
                        linea = lector.readLine();
                    }
                    matriz = new String[patrones.size()][patrones.get(0).length];
                    for(int i = 0; i < patrones.size(); i++){
                        for(int j = 0; j < patrones.get(i).length; j++){
                            matriz[i][j] = patrones.get(i)[j];
                        }
                    }
                    br.close();
                }catch (FileNotFoundException e){
                    Log.d("Archivo :","No existe");
                }catch (IOException e){

                }
                int[] capas = {804,20,20,1};
                BackPropagation bp = new BackPropagation(capas,matriz,100,0.01);
                NeuronResult nr = bp.entrenamiento();
                Log.d("Total de aprendidos :","" + nr.aprendidos);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
