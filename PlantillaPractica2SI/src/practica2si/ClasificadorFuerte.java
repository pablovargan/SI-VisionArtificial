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
public class ClasificadorFuerte {
    // Se compone de un array de ClasificadoresDebiles
    private ArrayList<ClasificadorDebil> clasificadoresDebiles;
    
    // Constructor
    public ClasificadorFuerte() 
    {
        this.clasificadoresDebiles = new ArrayList<ClasificadorDebil>();
    }
    
    public int numClasificadoresEncontrados() { return this.clasificadoresDebiles.size(); }
    
    public void adaBoost(int numClasificadores, ArrayList<Cara>listaAprendizaje, 
            int numCandidatos, int[] minimos, int[] maximos)
    {
        // Inicializar la distribucion de pesos D(i) = 1/N sobre el conjunto de entrenamiento
        // N es el tamaño del vector
        for(Cara c: listaAprendizaje)
            c.setPeso(1/listaAprendizaje.size());
        // Empiezo a buscar-entrenar los clasificadores debiles para crear un
        // clasificador fuerte
        //ClasificadorFuerte cF = new ClasificadorFuerte();
        for(int i = 0; i < numClasificadores; i++)
        {
            // Inicialmente, cuando T=1 todos los ejemplos son igualmente probables
            // 1. Entrenar clasificador debil para ht a partir de Dt
            ClasificadorDebil cDebil = null;
            // Genero clasificadores debiles a partir de numCandidatos
            for(int j = 0; j < numCandidatos; j++)
            {
                // Me debo quedar con el mejor 
                ClasificadorDebil aux = new ClasificadorDebil(numClasificadores, minimos, maximos);
                aux.conjuntoAprendizaje(listaAprendizaje);
                // Ahora debo elegir el mejor buscando el que menor tasa de error tenga
                if(cDebil == null || aux.getMejor().getError() < cDebil.getMejor().getError())
                    cDebil = aux;
            }
            // Añado el mejor que he obtenido a
            //cF.clasificadoresDebiles.add(cDebil);
            this.clasificadoresDebiles.add(cDebil);
            // 2. Calcular el valor de confianza para ht(de ese clasificador)
            // Al realizar el test obtengo el valor de confianza, entonces lo recojo del
            // del clasificador debil elegido
            double valorConfianza = cDebil.getValorConfianza();
            
            // 3. Actualizar distribución D sobre el conjunto de entrenamiento
            // Z = Factor de normalizacion
            double Z = 0.0;
            // Se calcula Z como un valor de normalización, siendo n el total de patrones y
            // Dt(i) el peso del patrón i en la iteración t.
            for(Cara c: listaAprendizaje)
                Z += c.getPeso();
            // Donde yi es la clase del patrón real y ht(i) es la clase que devuelve el clasificador
            // de esta iteración al evaluar el patrón i.
            double A = 0.0;
            for(Cara c: listaAprendizaje)
            {
                // Actualizar los pesos (darselos a los que esten cerca de la frontera)
                // segun la expresion del enunciado
                
                // Cuando yi no coincide con ht(i), el exponente de e toma un valor positivo y, si
                // no, toma un valor negativo. Es por eso que, cuando no coincide, el peso del patrón
                // se incrementa. Si coincide, decrementa por ser negativo.
                if(cDebil.tipoCara(c) != c.getTipo())
                    A = Math.pow(Math.E, valorConfianza);
                else
                    A = Math.pow(Math.E, -valorConfianza);
                double resultado = (c.getPeso() * A/Z);
                c.setPeso(resultado);
            }
            
            // 4. Actualizar el clasificador fuerte
            int aciertos = 0;
            for(Cara c: listaAprendizaje)
            {
                //if(cF.H(c) == c.getTipo())
                if(this.H(c) == c.getTipo())    
                    aciertos++;
            }
            System.out.println("Clasificador " + (i + 1) + ": " + aciertos + "/" + listaAprendizaje.size() + " (" + (100.0 * aciertos/listaAprendizaje.size()) + "%)");
            // Al final de esta iteración se comprueba qué tasa de error se obtiene al evaluar el
            // conjunto de entrenamiento sobre el clasificador fuerte; es decir, sobre la totalidad
            // de los clasificadores débiles. Si la tasa de error es 0, ya no es necesario repetir
            // el bucle.
            if (aciertos == listaAprendizaje.size())
            {
                System.out.println("Obtenido el 100%");
		break;
            }
        }
        // Devuelve un clasificador fuerte con el conjunto de clasificadores debiles
        //return cF;
    }
    
    public double H(Cara cara)
    {
        double resultado = 0;
        for (ClasificadorDebil cDebil : clasificadoresDebiles)
            resultado += cDebil.getValorConfianza() * cDebil.tipoCara(cara);
        if (resultado < 0)
            return -1;
        else
            return 1;
    }
}
