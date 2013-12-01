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
    // Los que generaré a partir de c(NUMCLASIFICADORES)
    private ArrayList<Hiperplano> hp;
    // Hiperplano que mejor clasifique
    private Hiperplano mejor;
    // Conjunto de aprendizaje
    
    // Genera c hiperplanos aleatorios
    public ClasificadorDebil(int numC)
    {
        this.numC = numC;
        hp = new ArrayList<Hiperplano>();
        for(int i = 0; i < this.numC; i++)
            hp.add(new Hiperplano());
    }
    // Genera c hiperplanos a partir de los puntos(min,max)
    public ClasificadorDebil(int numC, int[] minPuntos, int[] maxPuntos)
    {
        this.numC = numC;
        hp = new ArrayList<Hiperplano>();
        for(int i = 0; i < this.numC; i++)
            hp.add(new Hiperplano(minPuntos, maxPuntos));
    }
    
    // Devuelve el que menor tasa de error devuelve
    public Hiperplano getMejor() { return mejor; }
    
    // Clasifico el conjunto de aprendijaze y encuentro el mejor
    public void conjuntoAprendizaje(ArrayList<Cara> aprender) {
        // Aprendizaje[aciertos,fallos]
        int[] aciertos = new int[hp.size()];
        int[] fallos = new int[hp.size()];
        // Cada hiperplano con una cara
        for(int i = 0; i < hp.size(); i++)
        {
            int acierto = 0, fallo = 0;
            for(int j = 0; j < aprender.size(); j++)
            {
                // Evalua el punto y devuelva en que parte se encuentra
                double res = hp.get(i).evaluar(aprender.get(j).getData());
                int tipoCara = aprender.get(j).getTipo();
                if(res > 0)
                {
                    if(tipoCara == 1)
                        acierto++;
                    else
                        fallo++;
                }
                else
                {
                    if(tipoCara == - 1)
                        acierto++;
                    else
                        fallo++;
                }
            }
            aciertos[i] = acierto;
            fallos[i] = fallo;
        }
        getMejorHiperplano(aciertos, fallos, aprender.size());
    }
    
    private void getMejorHiperplano(int[] a, int[] f, int tamCara)
    {
        // Pivote
        int mejor = 0;
        // Busco el mejor
        for(int i = 0; i < hp.size(); i++)
        {
            if(a[i] > a[mejor])
                mejor = i;
            float tasaAciertos = (float) a[i]/tamCara;
            // Voy imprimiendo los resultados obtenidos
            imprimirHiperplano(i, a[i], f[i], tasaAciertos);
        }
        // Asigno el mejor
        this.mejor = hp.get(mejor);
        System.out.println("EL MEJOR HIPERPLANO ES EL " + mejor);
    }
    
    public void testMejor(ArrayList<Cara> lt)
    {
        int acierto = 0, fallo = 0;
        for(int i = 0;i < lt.size(); i++)
        {
            double res = this.mejor.evaluar(lt.get(i).getData());
            int tipoCara = lt.get(i).getTipo();
            if(res > 0)
                {
                    if(tipoCara == 1)
                        acierto++;
                    else
                        fallo++;
                }
                else
                {
                    if(tipoCara == - 1)
                        acierto++;
                    else
                        fallo++;
                }
        }
        float tasaAciertos = (float) acierto/lt.size();
        System.out.println("Test mejor hiperplano");
        // Imprimo los resultados obtenidos
        imprimirHiperplano(-1,acierto, fallo, tasaAciertos);
        
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
