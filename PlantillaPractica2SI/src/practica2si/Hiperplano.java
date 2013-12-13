/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2si;

/**
 *
 * @author Pablo
 */
public class Hiperplano 
{
    private static final int DIMENSION = 576;
    // Escala de grises [0,255]
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;
    // Tipos de caras
    private static int maxV = 1;
    private static int minV = -1;
    // Vector de puntos
    private int[] puntos;
    private double[] vector;
    // C
    private double C;
    // Tasa de error de ese hiperplano
    private double error;
    
    public Hiperplano() 
    {
        C = 0.0;
        puntos = new int[DIMENSION];
        vector = new double[DIMENSION];
        for(int i = 0; i < DIMENSION; i++) 
        {
            // Genero un hiperplano con valores aleatorios entre 1 y 255
            puntos[i] = (int)(Math.random() * MAX_VALUE);
            //Puntos entre -1 y 1
            vector[i] = (double) (Math.random() * (maxV - minV)) + minV;
        }
        // Normalizo el vector para que tenga la misma dirección y sentido
        int modulo = 0;
        for(int i = 0; i < DIMENSION; i++)
            modulo += vector[i];
        for(int i = 0; i < DIMENSION; i++)
            vector[i] /= modulo;
        // Calculo C como punto en el espacio aleatorio
        for(int i = 0; i < DIMENSION; i++)
            C += puntos[i] * vector[i];
    }
    public Hiperplano(int[] minPuntos, int[] maxPuntos)
    {
        C = 0.0;
        // Genero un hiperplano con los valores de los vectores del parametro (min, max)
        puntos = new int[DIMENSION];
        vector = new double[DIMENSION];
        for(int i = 0; i < DIMENSION; i++) 
        {
            // Los puntos ya los tengo porque los he cogido aleatoriamente
            puntos[i] = (int)(Math.random() * (maxPuntos[i] - minPuntos[i])) + minPuntos[i];
            vector[i] = (Math.random() * (maxV - minV)) + minV;
        }
        // Normalizo el vector para que tenga la misma dirección y sentido
        double modulo = 0;
        for(int i = 0; i < DIMENSION; i++)
            modulo += vector[i];
        for(int i = 0; i < DIMENSION; i++)
            vector[i] /= modulo;
        // Calculo C como punto en el espacio aleatorio
        for(int i = 0; i < DIMENSION; i++)
            C += puntos[i] * vector[i];
    }
    
    // Evalua un punto en un plano y genera el resultado de en qué lado queda el punto
    public double evaluar(int[] p)
    {
        double resultado = 0;
        for(int i = 0; i < p.length; i++)
            resultado += vector[i] * p[i];
        // 0 si esta en el hiperplano, positivo si esta por encima y negativo si está por debajo
        return resultado - C;
    }
    
    public double[] getVector() { return vector; }
    // Tasa de error del hiperplano
    public void setError(double error) { this.error = error; }
    public double getError() { return error; }
}
