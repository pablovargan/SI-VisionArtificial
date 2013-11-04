package tia;

import java.util.ArrayList;

public class AdaBoost
{
	/**
	 * Ejecuta el algoritmo AdaBoost para obtener un clasificador fuerte.
	 * 
	 * @param numClasificadores Cantidad de clasificadores que tendrá el clasificador fuerte.
	 * @param numCandidatos Cantidad de hiperplanos aleatorios que se generarán, seleccionando el mejor candidato.
	 * @param listaEntrenamiento Conjunto de entrenamiento para entrenar el clasificador fuerte.
	 * @param usarPerceptronSimple Indica si se va a utilizar el PerceptronSimple para entrenar el clasificador débil o no.
	 * @return Devuelve un clasificador fuerte (compuesto de múltiples clasificadores débiles).
	 */
	public static ClasificadorFuerte ejecutarAlgoritmo(int numClasificadores, int numCandidatos, ArrayList<Cara> listaEntrenamiento, boolean usarPerceptronSimple)
	{		
		// Inicializamos los pesos del conjunto de entrenamiento.
		for (Cara i : listaEntrenamiento)
			i.setProbabilidad(1.0/listaEntrenamiento.size());
		
		// Buscamos T clasificadores débiles para formar un clasificador fuerte.
		ClasificadorFuerte clasificadorFuerte = new ClasificadorFuerte();
		for (int i = 0; i < numClasificadores; i++)
		{
			// 1. Generamos aleatoriamente múltiples clasificadores y escogemos el mejor candidato.
			ClasificadorDebil candidato = null;
			for (int j = 0; j < numCandidatos; j++)
			{
				ClasificadorDebil candidatoAux = null;
				if (!usarPerceptronSimple)
					candidatoAux = new ClasificadorDebil();
				else
					candidatoAux = new PerceptronSimple();
				candidatoAux.entrenaClasificador(listaEntrenamiento);
				if (candidato == null || candidatoAux.getError() < candidato.getError())
					candidato = candidatoAux;
			}
			clasificadorFuerte.anadirClasificador(candidato);
			
			// 2. Obtenemos el valor de confianza del clasificador.
			double valorConfianza = candidato.getValorConfianza();

			// 3. Se actualizan los pesos.
			double Z = 0;
			for (Cara j : listaEntrenamiento)
				Z += j.getProbabilidad();
			for (Cara j : listaEntrenamiento)
			{
				double A;
				if (candidato.h(j) != j.getTipo())
					A = Math.pow(Math.E, valorConfianza);
				else
					A = Math.pow(Math.E, -valorConfianza);
				j.setProbabilidad(j.getProbabilidad() * A / Z);
			}
			
			// 4. Se finaliza la búsqueda si se obtiene el 100% de aciertos con el clasificador.
			int aciertos = 0;
			for (Cara j : listaEntrenamiento)
			{
				if (clasificadorFuerte.H(j) == j.getTipo())
					aciertos++;
			}
			System.out.println("Clasificador " + (i + 1) + ": " + aciertos + "/" + listaEntrenamiento.size() + " (" + (100.0 * aciertos/listaEntrenamiento.size()) + "%)");
			if (aciertos == listaEntrenamiento.size())
			{
				System.out.println("Obtenido el 100%");
				break;
			}
		}
		
		return clasificadorFuerte;
	}
}
