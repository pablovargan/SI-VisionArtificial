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
    private int c;
    // Los que generaré a partir de c(NUMCLASIFICADORES)
    private ArrayList<Hiperplano> hp;
    // Hiperplano que mejor clasifique
    private Hiperplano mejor;
    // Conjunto de aprendizaje
    
    // Genera c hiperplanos aleatorios
    public ClasificadorDebil(int c)
    {
        this.c = c;
        hp = new ArrayList<Hiperplano>();
        for(int i = 0; i < this.c; i++)
            hp.add(new Hiperplano());
    }
    // Genera c hiperplanos a partir de los puntos(min,max)
    public ClasificadorDebil(int c, int[] minPuntos, int[] maxPuntos)
    {
        this.c = c;
        hp = new ArrayList<Hiperplano>();
        for(int i = 0; i < this.c; i++)
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
                if(res < 0)
                {
                    if(tipoCara == - 1)
                        acierto++;
                    else
                        fallo++;
                }
                else
                {
                    if(tipoCara == 1)
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
        System.out.println("Hiperplano  Aciertos    Fallos  TasaAciertos");
        // Pivote
        int mejor = 0;
        // Busco el mejor
        for(int i = 0; i < hp.size(); i++)
        {
            if(a[i] > a[mejor])
                mejor = i;
            float tasaAciertos = (float) a[i]/tamCara;
            // Voy imprimiendo los resultados obtenidos
            System.out.println(i + "    " + a[i] + " " + f[i] + "   " +
                    tasaAciertos);
        }
        this.mejor = hp.get(mejor);
    }
    
    public void testMejor(ArrayList<Cara> lt)
    {
        int acierto = 0, fallo = 0;
        for(int i = 0;i < lt.size(); i++)
        {
            double res = this.mejor.evaluar(lt.get(i).getData());
            int tipoCara = lt.get(i).getTipo();
            if(res < 0)
            {
                if(tipoCara == - 1)
                    acierto++;
                else
                    fallo++;
            }
            else
            {
                if(tipoCara == 1)
                    acierto++;
                else
                    fallo++;
            }
        }
        float tasaAciertos = (float) acierto/lt.size();
        System.out.println("Test mejor hiperplano");
        System.out.println("Aciertos    Fallos  TasaAciertos");
        System.out.println(acierto + "  " + fallo + "   " + tasaAciertos);
    }
}
