package com.pankecho.ia;

import java.util.Random;

/**
 * Created by pankecho on 18/12/17.
 */

public class Perceptron {
    double a = 0; //activacion
    double W[];//Vector de pesos
    double X[];//Vector de entrada
    double theta = 0; //Umbral como un peso más
    double y = 0; //Salida de la neurona
    Random rnd;
    final double BIAS = 1; //Bias
    double error = 0;



    public Perceptron(double[] in){
        rnd = new Random();
        X = in;
        W = new double[in.length];
        initWs();
    }

    public Perceptron(Perceptron[] in){
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
    private double activacion(){
        a = 0;
        for(int i = 0; i < X.length; i++){
            a += X[i] * W[i];
        }
        a += BIAS * theta;
        return a;
    }

    private double sigmoide(double a){
        y = 1/(1 + Math.pow(Math.E,-a));
        return y;
    }

    private double escalon(double a){
        y = (a >= 0)?1:0;
        return y;
    }

    private double lineal(double a){
        y = a;
        return y;
    }

    public void ajuste(double alpha){
        for(int i = 0; i < W.length; i++){
            //System.out.println("Peso anterior = " + W[i]);
            W[i] += alpha * this.error * this.X[i];
            //System.out.println("Peso ajustado = " + W[i]);
        }
        theta += alpha * this.error * this.BIAS;
    }

    private void initWs(){
        for(int i = 0; i< X.length; i++){
            W[i] = rnd.nextDouble();
        }
        theta = rnd.nextDouble();
    }

    public void procesamiento(boolean o){
        if(o)
            escalon(activacion());
        else
            sigmoide(activacion());
    }

    public void procesamiento(){
        sigmoide(activacion());
    }
}
