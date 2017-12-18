package com.pankecho.ia;

import java.util.Random;

/**
 * Created by pankecho on 18/12/17.
 */

public class TLU {
    double a = 0; //activacion
    double W[];//Vector de pesos
    double X[];//Vector de entrada
    double theta = 0; //Umbral como un peso más
    int y = 0; //Salida de la neurona
    Random rnd;
    final double BIAS = -1; //Bias


    //CONSTRUCTORES DE LA CLASE
    public TLU(double[] in){
        rnd = new Random();
        X = in;
        W = new double[in.length];
        initWs();
    }

    public TLU(Perceptron[] in){
        rnd = new Random();
        in_oculta(in);
        W = new double[in.length];
        initWs();
    }

    public TLU(TLU[] in){
        rnd = new Random();
        in_oculta(in);
        W = new double[in.length];
        initWs();
    }

    private void in_oculta(Perceptron[] capaAnterior){
        X = new double[capaAnterior.length];
        for(int i = 0; i < capaAnterior.length; i++){
            X[i] = capaAnterior[i].y;
        }
    }

    private void initWs(){
        for(int i = 0; i< X.length; i++){
            W[i] = rnd.nextDouble();
        }
        theta = rnd.nextDouble();
    }

    private void in_oculta(TLU[] capaAnterior){
        X = new double[capaAnterior.length];
        for(int i = 0; i < capaAnterior.length; i++){
            X[i] = capaAnterior[i].y;
        }
    }

    public boolean procesamiento(double[] in, double[] w, double theta){
        if(in.length == w.length){
            for(int i = 0; i < in.length; i++){
                a += in[i] * w[i];
            }
            a += theta * BIAS;
            y = umbralado(a);
        }
        else{
            System.out.println("Los tamaños de los vectores para el procesamiento no son iguales...");
        }
        return false;
    }

    private int umbralado(double potencial){
        if (potencial >= 0)
            return 1;
        else
            return 0;
    }
}
