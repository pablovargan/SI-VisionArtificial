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
    // NUMCLASIFICADORES
    private int numC;
    // Los que generaré a partir de c(numCandidatos)
    private ArrayList<Hiperplano> hp;
    // Hiperplano que mejor clasifique
    private Hiperplano mejor;
    // Valor de confianza para este clasificador
    private double valorConfianza;
    // Genera c hiperplanos aleatorios
    public ClasificadorDebil(int numCandidatos)
    {
        this.numC = numC;
        hp = new ArrayList<Hiperplano>();
        for(int i = 0; i < this.numC; i++)
            hp.add(new Hiperplano());
        this.valorConfianza = 0.0;
    }
    // Genera c hiperplanos a partir de los puntos(min,max)
    public ClasificadorDebil(int numC, int[] minPuntos, int[] maxPuntos)
    {
        this.numC = numC;
        hp = new ArrayList<Hiperplano>();
        for(int i = 0; i < this.numC; i++)
            hp.add(new Hiperplano(minPuntos, maxPuntos));
        this.valorConfianza = 0.0;
    }
    
    // Devuelve el que menor tasa de error devuelve
    public Hiperplano getMejor() { return mejor; }
    // Devuelve el valor de confianza del clasificador debil
    public double getValorConfianza() { return valorConfianza; }
    
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
        for(Hiperplano h: hp)
        {
            int er = 0;
            for(Cara c: listaCaras)
            {
                // Evalua el punto y devuelve en que parte se encuentra
                int pos = determinarPunto(h, c);
                int tipoCara = c.getTipo();
                if(pos != tipoCara) {
                     er++;
                }
            }
            // Calculo la tasa de error de ese hiperplano
            double tasaError = (double) er/listaCaras.size();
            h.setError(tasaError);
        }
        // Busco el mejor hiperplano con menor tasa de errores
       getMejorHiperplano(listaCaras.size());
    }
    
    // Obtengo el mejor hiperplano y calculo el valor de confiazan de este clasificador
    private void getMejorHiperplano(int tamCara)
    {
        Hiperplano aux = new Hiperplano(Double.MAX_VALUE);
        for(Hiperplano h: hp) {
            if(h.getError() < aux.getError())
                aux = h;
        }
        // Calculo el valor de confianza del que menor tasa de error me ha dado
        this.valorConfianza = 0.5 * Math.log((1-aux.getError())/aux.getError());
        // Asigno el mejor 
        this.mejor = aux;
        //System.out.println("VALOR DE CONFIANZA: " + valorConfianza);
        //System.out.println("CON TASA DE ERROR DE " + this.mejor.getError()*100 + "%");
    }
    
    // Entreno el clasificador a partir del mejor hiperplano
    public void conjuntoTest(ArrayList<Cara> listaTest)
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
