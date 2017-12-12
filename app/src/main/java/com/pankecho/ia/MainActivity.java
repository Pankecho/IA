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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private String m_Text = "";
    private AlertDialog.Builder builder;
    private EditText input;
    private Secciones s;


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
                builder.setTitle("Nombre del archivo");

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        writeFile(s.getMatrix(),pvg.getNumRows(),pvg.getNumColumns(),m_Text);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
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
}
