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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import Clases.BackPropagation;

public class MainActivity extends AppCompatActivity {

    private Secciones s;
    private BackPropagation bp;
    private String m_Text = "";
    private AlertDialog.Builder builder;
    private EditText input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);
        input = new EditText(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final PixelViewGrid pvg = new PixelViewGrid(this);
        pvg.setNumColumns(40);
        pvg.setNumRows(20);
        pvg.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ConstraintLayout l = (ConstraintLayout) findViewById(R.id.lay);
        l.addView(pvg);

        try{
            InputStream fis = getResources().openRawResource(R.raw.back);
            ObjectInputStream ois = new ObjectInputStream(fis);
            bp = (BackPropagation) ois.readObject();
            ois.close();
            showToast("Red cargada correctamente");
        }catch(Exception e){
            showToast("Hubo un error al cargar la red");
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = new Secciones(pvg.getMatriz(),pvg.getNumRows(),pvg.getNumColumns());
                String cadena = "";
                double[][] mat = s.armarMatriz();
                for (int i = 0; i < mat.length; i++) {
                    cadena += s.convertir(bp.clasifica(mat[i]));
                }
                showToast("" + cadena);
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

    public void writeFile(int[][] matrix,int numRows, int numColumns,String nombre){
        try {
            nombre = nombre + ".txt";
            File file = new File(getExternalFilesDir("MyFileStorage"),nombre);
            FileOutputStream fileOutput = new FileOutputStream(file);
            String salto = new String("\n");
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    String nueva = new String("" + matrix[i][j]);
                    fileOutput.write(nueva.getBytes());
                }
                fileOutput.write(salto.getBytes());
            }
            fileOutput.close();
        }catch (Exception e){

        }
    }

    public void showToast(CharSequence mensaje){
        Toast toast = Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT);
        toast.show();
    }
}
