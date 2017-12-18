package com.pankecho.ia;

import java.util.ArrayList;

/**
 * Created by pankecho on 18/12/17.
 */

public class BackPropagation {
    public String[][] patterns;//Cada fila es un patron, el último elemnto de la fila es la clasificación correcta
    private double alpha; //Tasa de aprendizaje
    private int iteraciones;
    private int filas, cols;
    private double ECM;//Error cuadratico
    private double[] vError; //Vectór para almacenar el error cuadrático medio por época
    private ArrayList<Perceptron[]> capas;
    private double errorMin = 0.0; //Error mínimo permitido para el entrenamiento satisfactorio
    private int aciertos = 0;
    private NeuronResult resultado;

    public BackPropagation(int capas[], String patrones[][], int iteraciones, double alpha){
        this.capas = new ArrayList<Perceptron[]>();
        this.vError = new double[iteraciones];
        this.iteraciones = iteraciones;
        this.alpha = alpha;
        this.patterns = patrones;
        initNeurons(capas);
    }

    public NeuronResult entrenamiento(){
        double e =0;
        long tEjecucion;
        long startTime = System.nanoTime();//Iniciamos el conteo del tiempo de ejecución
        for(int i = 0; i< iteraciones; i++){
            aciertos = 0;
            ECM = 0;
            for(int patron = 0; patron < patterns.length; patron++){//Pasamos todos los patrones por la red neuronal
                propagation(patron);
                e = retropropagation(patron);
                ajuste();
                ECM += e;
            }
            vError[i] = 0.5 * ECM;
        }
        long endTime = System.nanoTime();//Terminamos el conteo de tiempo para la ejecución
        tEjecucion = (endTime-startTime);//Calculamos el tiempo de ejecución
        this.resultado = new NeuronResult(this.iteraciones, this.capas, vError, tEjecucion, patterns.length, aciertos);
        return resultado;
    }

    private void initNeurons(int[] capas){
        for(int i = 0; i < capas.length; i++){//Recorremos todas las capas (número de neuronas por capa)
            Perceptron[] capa_i = new Perceptron[capas[i]];//Vector de neuronas de la capa
            double[] in = null; //Vector de entradas a las neuronas
            if(i == 0){// Si la capa es de entrada
                in = new double[1]; //El vecto de entradas será de un solo elemento
                for(int item = 0; item < 1; item++){
                    in[item] = 0.0;//Iciamos la entrada en 0.0
                }
            }
            //Llenamos las capas con las neuronas correspondientes
            for(int j = 0; j < capas[i]; j++){
                if(i == 0)
                    capa_i[j] = new Perceptron(in);//Si es capa de entrada pasa el vector de un solo elemento
                else
                    capa_i[j] = new Perceptron(this.capas.get(i - 1));//Si es intermedia pasa el vector de neuronas de la capa anterior
            }
            this.capas.add(capa_i);
        }
    }

    private double retropropagation(int patron){
        double cls;
        double y;
        double sumWs;
        double error = 0;
        String resultado = "";
        for(int capa = capas.size() - 1; capa >= 0; capa--){//Recorremos todas las capas de la red neuronal desde la salida hasta la entrada
            for(int neurona = 0; neurona < capas.get(capa).length; neurona++){//Recorremos las neuronas de la capa
                sumWs = 0;
                y = capas.get(capa)[neurona].y;
                if(capa == capas.size() - 1){//Si la neurona pertenece a la capa de salida
                    /*Calculamos el error para cada una de las neuronas de salida*/
                    cls = Double.parseDouble(Character.toString(patterns[patron][patterns[patron].length - 1].charAt(neurona)));
                    capas.get(capa)[neurona].error = cls - y;// * y * (1 - y);// Calculamos el error para una salida sigmoidal
                    error += Math.pow(capas.get(capa)[neurona].error, 2);
                    resultado +=(int)y;
                } else //Si es una de las capas intermedias
                {
                    for(int n = 0; n < capas.get(capa + 1).length - 1; n++){ //Recorremos todas las neuronas de la capa siguiente
                        /*sumatoria del producto del error de cada neurona por el peso correspondiente a la neurona que se está evaluando*/
                        sumWs = capas.get(capa + 1)[n].error * capas.get(capa + 1)[n].W[neurona];
                    }
                    capas.get(capa)[neurona].error = y * sumWs; //Calculamos el error de la neurona
                }
            }
        }
        if(resultado.equals(patterns[patron][patterns[patron].length - 1])){
            aciertos++;
        }
        return error;
    }

    private void propagation(int patron){
        for(int capa = 0; capa < capas.size(); capa++){//Recorremos todas las capas de la red neuronal
            for(int neurona = 0; neurona < capas.get(capa).length; neurona++){//Recorremos las neuronas de la capa
                if(capa == 0){//Si la neurona pertenece a la capa de entrada
                    capas.get(capa)[neurona].X[0] = Double.parseDouble(patterns[patron][neurona]);// La neurna solo tiene un valor de entrada sin peso
                    capas.get(capa)[neurona].a = capas.get(capa)[neurona].X[0];//La activación es igual a la entrada
                    capas.get(capa)[neurona].y = capas.get(capa)[neurona].a;// El valor del patron de entrada x pasa tal cual
                } else if (capa == capas.size() -1){//si es la capa de salida
                    capas.get(capa)[neurona].procesamiento(true); //Calculamos la salida de la neurona
                } else{
                    capas.get(capa)[neurona].procesamiento();
                }
                if(capa == capas.size() -1){
                }
                //Propagamos el resultado de salida hacia las neuronas de la capa siguiente
                if(capa < capas.size() - 1){
                    for(int j = 0; j < capas.get(capa + 1).length; j++){
                        capas.get(capa + 1)[j].X[neurona] = capas.get(capa)[neurona].y;
                    }
                }
            }
        }
    }

    private void ajuste(){
        for(int capa = 0; capa < capas.size(); capa++){//Recorremos todas las capas de la red neuronal
            for(int neurona = 0; neurona < capas.get(capa).length; neurona++){//Recorremos las neuronas de la capa
                if(capa != 0){//Si la neurona pertenece a la capa de entrada
                    capas.get(capa)[neurona].ajuste(alpha);// Se hace el ajuste de los pesos de la neurona con el error calculado anteriormente
                }
            }
        }
    }
}
