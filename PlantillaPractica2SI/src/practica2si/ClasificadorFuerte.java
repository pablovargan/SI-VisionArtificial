/*S
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
    private ClasificadorDebil elegido;
    
    // Constructor
    public ClasificadorFuerte() 
    {
        this.clasificadoresDebiles = new ArrayList<ClasificadorDebil>();
        this.elegido = null;
    }
    
    public int numClasificadoresEncontrados() { return clasificadoresDebiles.size(); }
    // Comprueba si es cara o no
    public int determinarCara(Cara c)
    { 
        double res = 0.0;
        for(ClasificadorDebil cDebil: this.clasificadoresDebiles)
            res += cDebil.getValorConfianza() * cDebil.determinarPunto(cDebil.getMejor() ,c);
        // Obtengo donde se encuentra contenido en el plano
        if(res < 0.0)
            return -1;
        else
            return 1;
    }
    public void adaBoost(int numClasificadores, ArrayList<Cara>listaAprendizaje, 
            int numIteraciones, int[] minimos, int[] maximos)
    {
        int []aciertosCandidato = new int[numClasificadores];
        // Inicializar la distribucion de pesos D(i) = 1/N sobre el conjunto de entrenamiento
        // N es el tamaño del vector
        for(Cara c: listaAprendizaje)
            c.setPeso((double) 1.0/listaAprendizaje.size());
        // Empiezo a buscar-entrenar los clasificadores debiles para crear un
        // clasificador fuerte
        for(int i = 0; i < numIteraciones; i++)
        {
            // Inicialmente, cuando T=1 todos los ejemplos son igualmente probables
            // 1. Entrenar clasificador debil para ht a partir de Dt
            // Genero clasificadores debiles a partir de numCandidatos
            ClasificadorDebil cDebil = new ClasificadorDebil(numClasificadores,minimos,maximos);
            // Me debo quedar con el mejor 
            cDebil.conjuntoAprendizaje(listaAprendizaje);
            // Recojo el mejor obtenido en el conjunto de aprendizaje
            // Añado el mejor que he obtenido a
            this.clasificadoresDebiles.add(cDebil);
            // 2. Calcular el valor de confianza para ht(de ese clasificador)
            // Al realizar el test obtengo el valor de confianza, entonces lo recojo del
            // del clasificador debil elegido
            double valorConfianza = cDebil.getValorConfianza();
            // 3. Actualizar distribución D sobre el conjunto de entrenamiento
            // Z = Factor de normalizacion
            double Z = 0.0;
            // Dt(c) el peso de la cara c en esa iteracion de listaAprendizaje
            for(Cara c: listaAprendizaje)
                Z += c.getPeso();
            for(int j = 0; j < listaAprendizaje.size()-1; j++)
            {
                double actualizar = 0.0;
                Cara c = listaAprendizaje.get(j);
                // Si acierto --> valorConfianza, si no es -
                if(cDebil.determinarPunto(cDebil.getMejor(),c) != c.getTipo())
                    actualizar = Math.pow(Math.E,-valorConfianza);
                // ¡ACIERTO!
                else
                    actualizar = Math.pow(Math.E,valorConfianza);
                // Ahora actualizo la distribucion de pesos de la iteracion siguiente
                listaAprendizaje.get(j+1).setPeso(c.getPeso() * actualizar / Z);
            }
            // 4. Actualizar el clasificador fuerte y me quedo con el que mejor
            int aciertos = 0;
            for(Cara cara: listaAprendizaje)
            {
                // Evalua el punto y devuelve en que parte se encuentra
                int pos = this.determinarCara(cara);
                int tipoCara = cara.getTipo();
                // Si son iguales, es un acierto y es valido ese clasificador
                if(pos == tipoCara)
                    aciertos++;
            }
            aciertosCandidato[i] = aciertos;
            System.out.println("Clasificador " + (i + 1) + ": " + aciertos + "/" + listaAprendizaje.size() + " (" + (100.0 * aciertos/listaAprendizaje.size()) + "%)");
            // Si obtengo los mismos aciertos que el corto la ejecución
            if(aciertos == listaAprendizaje.size())
                break;
        }
        getClasificadorDebilCandidato(aciertosCandidato, listaAprendizaje.size());
    }
    
    private void getClasificadorDebilCandidato(int []aciertosCandidato,
            int tamListaCaras)
    {
        int aciertos = 0, numClasificador = 0;
        for(int i = 0; i < aciertosCandidato.length; i++) 
        {
            if(aciertosCandidato[i] > aciertos)
            {
                aciertos = aciertosCandidato[i];
                numClasificador = i;
            }
        }
        // Asigno como el clasificador elegido
        this.elegido = clasificadoresDebiles.get(numClasificador);
        // Lo muestro por pantalla
        System.out.println("Clasificador elegido " + (numClasificador + 1) + ": " + aciertos 
                + "/" + tamListaCaras + " (" + (100.0 * aciertos/tamListaCaras) + "%)");
    }
}
