package com.pankecho.ia;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pankecho on 10/12/17.
 */

public class Secciones {

    private int [][] matriz;
    private int contador = 1;
    private char [] letras =  {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private int rows,cols;
    private Set<Integer> set;

    public Secciones(boolean[][] matriz, int rows, int colum){
        this.matriz = new int[rows][colum];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < colum; j++){
                this.matriz[i][j] = matriz[i][j] ? 1 : 0;
            }
        }
        this.rows = rows;
        this.cols = colum;
        recorrer();
        cargarSet();
    }
    public void recorrer(){
        int contador=1;
        for(int i=0;i<matriz.length;i++)
            for(int j=0;j<matriz[0].length;j++){
                capa(matriz,i,j);
            }
    }

    public void imprimir(){
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz[0].length;j++){
                System.out.print(" "+matriz[i][j]);
            }
            System.out.println();
        }
    }

    public void cambiar(int valor,int valor_a_buscar){
        for(int i=0;i<matriz.length;i++)
            for(int j=0;j<matriz[0].length;j++){
                if(matriz[i][j]!=0&&matriz[i][j]==valor_a_buscar)
                    matriz[i][j]=valor;
            }
    }

    public void capa(int matriz[][],int i,int j) {
        if (j - 1 > -1 && j + 1 < matriz[0].length && i - 1 > -1) {

            if (matriz[i][j] != 0)
                if (matriz[i][j - 1] == 0 && matriz[i - 1][j] == 0 && matriz[i - 1][j + 1] == 0) {
                    matriz[i][j] = contador;
                    contador++;
                } else {
                    matriz[i][j] = ((matriz[i][j - 1] > 0) ? matriz[i][j - 1] : matriz[i][j]);

                    matriz[i][j] = ((matriz[i - 1][j] > 0) ? matriz[i - 1][j] : matriz[i][j]);

                    matriz[i][j] = ((matriz[i - 1][j + 1] > 0) ? matriz[i - 1][j + 1] : matriz[i][j]);
                    cambiar(matriz[i][j], matriz[i][j - 1]);
                    cambiar(matriz[i][j], matriz[i - 1][j]);
                    cambiar(matriz[i][j], matriz[i - 1][j + 1]);
                }
        } else if (j - 1 > -1 && i - 1 > -1) {

            if (matriz[i][j] != 0)
                if (matriz[i][j - 1] == 0 && matriz[i - 1][j] == 0) {
                    matriz[i][j] = contador;
                    contador++;
                } else {
                    matriz[i][j] = ((matriz[i][j - 1] > 0) ? matriz[i][j - 1] : matriz[i][j]);

                    matriz[i][j] = ((matriz[i - 1][j] > 0) ? matriz[i - 1][j] : matriz[i][j]);
                    cambiar(matriz[i][j], matriz[i][j - 1]);
                    cambiar(matriz[i][j], matriz[i - 1][j]);
                }

        } else if (j - 1 > -1) {

            if (matriz[i][j] != 0)
                if (matriz[i][j - 1] == 0) {
                    matriz[i][j] = contador;
                    contador++;
                } else {
                    matriz[i][j] = ((matriz[i][j - 1] > 0) ? matriz[i][j - 1] : matriz[i][j]);
                    cambiar(matriz[i][j], matriz[i][j - 1]);
                }
        } else if (j + 1 < matriz[0].length && i - 1 > -1) {
            if (matriz[i][j] != 0)
                if (matriz[i - 1][j] == 0 && matriz[i - 1][j + 1] == 0) {
                    matriz[i][j] = contador;
                    contador++;
                } else {
                    matriz[i][j] = ((matriz[i - 1][j] > 0) ? matriz[i - 1][j] : matriz[i][j]);
                    matriz[i][j] = ((matriz[i - 1][j + 1] > 0) ? matriz[i - 1][j + 1] : matriz[i][j]);
                    cambiar(matriz[i][j], matriz[i - 1][j]);
                    cambiar(matriz[i][j], matriz[i - 1][j + 1]);
                }
        }
    }

    public int[][] getMatrix(){
        return matriz;
    }

    public void cargarSet(){
        this.set = new HashSet<Integer>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.set.add(matriz[i][j]);
            }
        }
    }

    public double[] convertir(int valor){
        double[] array = new double[rows * cols];
        int indice = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                array[indice] = (double)(matriz[i][j] == valor ? 1 : 0);
                indice++;
            }
        }
        System.out.println(array.length);
        return array;
    }

    public double[][] armarMatriz(){
        set.remove(0);
        double[][] matriz = new double[this.set.size()][800];
        int contador = 0;
        for (Integer i : set) {
            matriz[contador] = convertir(i);
            contador++;
        }
        return matriz;
    }

    public String convertir(String valores){
        String cadena = "";
        try{
            cadena += letras[Integer.parseInt(valores,2) - 1];
        }catch (Exception e){
            cadena += "";
        }
        return cadena;
    }

}
