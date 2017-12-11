package com.pankecho.ia;

/**
 * Created by pankecho on 10/12/17.
 */

public class Secciones {

    private int [][] matriz;
    private int contador = 1;

    public Secciones(boolean[][] matriz, int rows, int colum){
        this.matriz = new int[rows][colum];
        for (int i = 0; i < colum; i++){
            for (int j = 0; j < rows; j++){
                this.matriz[j][i] = matriz[i][j] ? 1 : 0;
            }
        }
        recorrer();
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

    public void capa(int matriz[][],int i,int j){
        if(j-1>-1&&j+1<matriz[0].length&&i-1>-1){

            if(matriz[i][j]!=0)
                if(matriz[i][j-1]==0&&matriz[i-1][j]==0&&matriz[i-1][j+1]==0){
                    matriz[i][j]=contador;
                    contador++;
                }else{
                    matriz[i][j]=((matriz[i][j-1]>0)? matriz[i][j-1]:matriz[i][j]);

                    matriz[i][j]=((matriz[i-1][j]>0)? matriz[i-1][j]:matriz[i][j]);

                    matriz[i][j]=((matriz[i-1][j+1]>0)?matriz[i-1][j+1]:matriz[i][j]);
                    cambiar(matriz[i][j],matriz[i][j-1]);
                    cambiar(matriz[i][j],matriz[i-1][j]);
                    cambiar(matriz[i][j],matriz[i-1][j+1]);
                }
        }
        else
        if(j-1>-1&&i-1>-1){

            if(matriz[i][j]!=0)
                if(matriz[i][j-1]==0&&matriz[i-1][j]==0){
                    matriz[i][j]=contador;
                    contador++;
                }else{
                    matriz[i][j]=((matriz[i][j-1]>0)? matriz[i][j-1]:matriz[i][j]);

                    matriz[i][j]=((matriz[i-1][j]>0)? matriz[i-1][j]:matriz[i][j]);
                    cambiar(matriz[i][j],matriz[i][j-1]);
                    cambiar(matriz[i][j],matriz[i-1][j]);
                }

        }
        else
        if(j-1>-1){

            if(matriz[i][j]!=0)
                if(matriz[i][j-1]==0){
                    matriz[i][j]=contador;
                    contador++;
                }else{
                    matriz[i][j]=((matriz[i][j-1]>0)? matriz[i][j-1]:matriz[i][j]);
                    cambiar(matriz[i][j],matriz[i][j-1]);
                }

        }
        else
        if(j+1<matriz[0].length&&i-1>-1){
            if(matriz[i][j]!=0)
                if(matriz[i-1][j]==0&&matriz[i-1][j+1]==0){
                    matriz[i][j]=contador;
                    contador++;
                }else{
                    matriz[i][j]=((matriz[i-1][j]>0)? matriz[i-1][j]:matriz[i][j]);

                    matriz[i][j]=((matriz[i-1][j+1]>0)? matriz[i-1][j+1]:matriz[i][j]);
                    cambiar(matriz[i][j],matriz[i-1][j]);
                    cambiar(matriz[i][j],matriz[i-1][j+1]);
                }
        }
    }
}
