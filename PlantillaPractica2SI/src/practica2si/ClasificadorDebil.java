/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2si;

import java.util.ArrayList;

/**
 *
 * @author Pablo
 */
public class ClasificadorDebil 
{
    // Hiperplano que se crea para el clasificador debil
    private Hiperplano hiperplano;
    // Valor de confianza para este clasificador
    private double valorConfianza;
    // Tasa de error del clasificador
    private double error;
    
    // Constructor
    public ClasificadorDebil(int[] minPuntos, int[] maxPuntos)
    {
       this.hiperplano = new Hiperplano(minPuntos, maxPuntos);
       this.valorConfianza = 0.0;
       this.error = 0.0;
    }
    
    // Devuelve el hiperplano del clasificador
    public Hiperplano getHiperplano() { return hiperplano; }
    // Devuelve el valor de confianza del clasificador debil
    public double getValorConfianza() { return valorConfianza; }
    
    // Tasa de error del clasificador
    public void setError(double error) { this.error = error; }
    public double getError() { return error; }
    
    // Determina donde se encuentra un punto en el hiperplano.
    // 0 si el punto esta contenido en el plano
    // +1 si el punto esta por encima del plano
    // -1 si el punto esta por debajo del plano
    public int determinarPunto(Cara c)
    {
        if(this.hiperplano.evaluar(c.getData()) < 0.0)
            return -1;
        else
            return 1;
    }
    
    // Clasifico el conjunto de aprendijaze y encuentro el mejor
    public void conjuntoAprendizaje(ArrayList<Cara> listaCaras) 
    {    
        int er = 0;
        for(Cara c: listaCaras)
        {
            // Evalua el punto y devuelve en que parte se encuentra
            int pos = determinarPunto(c);
            int tipoCara = c.getTipo();
            if(pos != tipoCara) {
                 er++;
            }
        }
        // Calculo la tasa de error de ese hiperplano
        double tasaError = (double) er/listaCaras.size();
        this.error = tasaError;
        // Calculo el valor de confianza del que menor tasa de error me ha dado
        this.valorConfianza = 0.5 * Math.log((1 - error)/error);
    }
    
    // Entreno el clasificador a partir del mejor hiperplano
    public void conjuntoTest(ArrayList<Cara> listaTest)
    {
        int er = 0;
        for(Cara c: listaTest)
        {
            // Evalua el punto y devuelve en que parte se encuentra
            int pos = determinarPunto(c);
            int tipoCara = c.getTipo();
            if(pos != tipoCara) {
                 er++;
            }
        }
        double tasaError = (double) er/listaTest.size();
        System.out.println("Test mejor hiperplano");
        // Imprimo los resultados obtenidos
        System.out.println("- Tasa de fallos: " + tasaError*100 + "%");
        System.out.println("----------------------");
    }
    
    private void imprimirHiperplano(int pos, int acierto, int fallo, float tasa) {
        // Muestra el num de hiperplano
        if(pos != -1)
            System.out.println("Hiperplano: " + pos);
        
        System.out.println("- Aciertos: " + acierto);
        System.out.println("- Fallos: " + fallo);
        System.out.println("- TASA DE ACIERTOS: " + tasa*100 + "%");
        System.out.println("------------------------------");
        System.out.println("");
    }
}
