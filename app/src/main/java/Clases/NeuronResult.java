package Clases;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pankecho on 19/12/17.
 */

public class NeuronResult implements Serializable {
    public ArrayList<ArrayList<double[]>> pesos;//Matríz de pesos por capa
    public int aprendidos;//Número de patrones aprendidos correctamente
    public double aprendizaje;//Porcentaje de aprendizaje de los patrones presentados
    public int numPatrones;//Número total de patrones de entrenamiento
    public long tEjecucion;//Tiempo de ejecucion en milisegundos
    public double[] vError;

    public NeuronResult(int epocas, ArrayList<Perceptron[]> capas, double[] vError, long time, int patrones, int aprendidos){
        this.pesos = new ArrayList<ArrayList<double[]>>();
        this.vError = vError;
        llenaPesos(capas);
        this.tEjecucion = time;
        this.numPatrones = patrones;
        this.aprendidos = aprendidos;
        this.aprendizaje = aprendidos/numPatrones;
    }

    private void llenaPesos(ArrayList<Perceptron[]> capas){
        for(int capa = 0; capa < capas.size(); capa++){//Recorremos todas las capas (número de neuronas por capa)
            ArrayList<double[]> pesosCapa = new ArrayList<double[]>();
            for(int neurona = 0; neurona < capas.get(capa).length; neurona++){//Recorremos las neuronas de la capa
                for(int w = 0; w < capas.get(capa)[neurona].W.length; w++){
                    double peso[] = new double[2];
                    peso[0] = (double)neurona;
                    peso[1] = capas.get(capa)[neurona].W[0];
                    pesosCapa.add(peso);
                }
            }
            pesos.add(pesosCapa);
        }
    }

}//Fin de la clase
