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
    private ArrayList<Hiperplano> hiperplanos;
    // Mejor hiperplano del clasificador
    private Hiperplano mejor;
    // Valor de confianza para este clasificador
    private double valorConfianza;
    
    // Constructor
    public ClasificadorDebil(int numClasificadores, int[] minPuntos, int[] maxPuntos)
    {
       this.hiperplanos = new ArrayList<Hiperplano>();
       for(int i = 0; i < numClasificadores; i++)
           hiperplanos.add(new Hiperplano(minPuntos, maxPuntos));
       this.valorConfianza = 0.0;
    }
    // Devuelve el valor de confianza del clasificador debil
    public double getValorConfianza() { return valorConfianza; }
    public Hiperplano getMejor() { return mejor; }
    
    // Determina donde se encuentra un punto en el hiperplano.
    // 0 si el punto esta contenido en el plano
    // +1 si el punto esta por encima del plano
    // -1 si el punto esta por debajo del plano
    public int determinarPunto(Hiperplano h, Cara c)
    {
        if(h.evaluar(c.getData()) < 0.0)
            return -1;
        else
            return 1;
    }
    
    // Clasifico el conjunto de aprendijaze y encuentro el mejor
    public void conjuntoAprendizaje(ArrayList<Cara> listaCaras) 
    {
        for(Hiperplano hp: hiperplanos)
        {
            int er = 0;
            for(Cara c: listaCaras)
            {
                // Evalua el punto y devuelve en que parte se encuentra
                int pos = determinarPunto(hp,c);
                int tipoCara = c.getTipo();
                if(pos != tipoCara) {
                     er++;
                }
            }
            // Calculo la tasa de error de ese hiperplano
            double tasaError = (double) er/listaCaras.size();
            hp.setError(tasaError);
        }
        asignarMejorHiperplano();
    }
    
    private void asignarMejorHiperplano()
    {
         // Busco el mejor hiperplano, es decir, el que menor tasa de error tenga
        Hiperplano aux = null;
        for(Hiperplano mejor: hiperplanos)
        {
            if(aux == null || mejor.getError() < aux.getError())
                aux = mejor;
        }
        // Lo asigno como mejor del clasificador debil
        this.mejor = aux;
        // Calculo el valor de confianza del que menor tasa de error me ha dado
        this.valorConfianza = 0.5 * Math.log((1 - mejor.getError())/mejor.getError());
    }
            
    
    // Entreno el clasificador a partir del mejor hiperplano
    public void conjuntoTestMejor(ArrayList<Cara> listaTest)
    {
        int er = 0;
        for(Cara c: listaTest)
        {
            // Evalua el punto y devuelve en que parte se encuentra
            int pos = determinarPunto(this.mejor, c);
            int tipoCara = c.getTipo();
            if(pos != tipoCara) {
                 er++;
            }
        }
        double tasaError = (double) er/listaTest.size();
        // TODO: ¿Reasignar la tasa de error?
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
