package tia;

import java.util.ArrayList;

public class PerceptronSimple extends ClasificadorDebil
{
	public static double CONSTANTE_APRENDIZAJE = 0.1;
	public static int MAX_ITERACIONES = 100;
	public static double TASA_ACIERTO_OBJETIVO = 0.75;
	
	/**
	 * Entrena el clasificador según los pesos de las caras más próximas a la frontera (las caras más controvertidas).
	 * Utiliza la regla delta de una única neurona (transparencia 7 del tema de "Perceptrones multicapa").
	 * Se trata de ajustar el hiperplano a través del espacio hasta minimizar la tasa de aciertos.
	 * Establece además el error y el valor de confianza del clasificador débil.
	 * 
	 * @param conjuntoEntrenamiento Conjunto de caras sobre las que se entrena el clasificador.
	 */
	@Override
	public void entrenaClasificador(ArrayList<Cara> conjuntoEntrenamiento)
	{		
		// Entrenamos el clasificador hasta obtener una tasa de aciertos objetivo o superemos las iteraciones máximas.
		int iteraciones = 0;
		double tasaAciertos = 0;
		while (iteraciones < MAX_ITERACIONES && tasaAciertos < TASA_ACIERTO_OBJETIVO)
		{
			// Ajustamos el hiperplano asociado a la neurona (wj' = wj + cte_aprendizaje * (d - y) * xj).
			for (Cara i : conjuntoEntrenamiento)
			{
				double d = i.getTipo();
				double y = h(i);
				int[] x = i.getData();
				for (int j = 0; j < Hiperplano.DIMENSIONES; j++)
				{
					hiperplano.getCoeficientes()[j] = hiperplano.getCoeficientes()[j] + CONSTANTE_APRENDIZAJE * (d - y) * x[j];
				}
			}
			
			// Incrementamos el número de iteraciones y recalculamos la tasa de aciertos.
			iteraciones++;
			tasaAciertos = 0;
			for (Cara i : conjuntoEntrenamiento)
			{
				if (h(i) == i.getTipo())
					tasaAciertos++;
			}
			tasaAciertos /= conjuntoEntrenamiento.size();
		}
			
		// Finalmente se calcula el error del igual forma que en el clasificador débil base.
		error = 0;
		for (Cara i : conjuntoEntrenamiento)
		{
			if (h(i) != i.getTipo())
				error += i.getProbabilidad();
		}
		valorConfianza = 0.5 * Math.log10((1 - error) / error);	
	}
}
