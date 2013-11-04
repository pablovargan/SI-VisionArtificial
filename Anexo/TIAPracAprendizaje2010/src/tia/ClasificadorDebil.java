package tia;

import java.util.ArrayList;

public class ClasificadorDebil
{
	protected Hiperplano hiperplano;
	protected double error;
	protected double valorConfianza;
	
	/**
	 * Constructor por defecto que genera un clasificador con un hiperplano aleatorio.
	 */
	public ClasificadorDebil()
	{
		hiperplano = new Hiperplano();
		error = Double.MAX_VALUE;
		valorConfianza = Double.MIN_VALUE;
	}
	
	/**
	 * Obtiene el error de entrenamiento del clasificador.
	 * Este método sólo debe ejecutarse después de entrenar el clasificador (entrenarClasificador()).
	 * @return Devuelve un valor real que es el error de entrenamiento del clasificador.
	 */
	public double getError()
	{
		return error;
	}
	
	/**
	 * Obtiene el valor de confianza del clasificador.
	 * Este método sólo debe ejecutarse después de entrenar el clasificador (entrenarClasificador()).
	 * @return Devuelve un valor real que es el valor de confianza del clasificador.
	 */
	public double getValorConfianza()
	{
		return valorConfianza;
	}
	
	/**
	 * Entrena el clasificador según los pesos de las caras más próximas a la frontera (las caras más controvertidas).
	 * Establece además el error y el valor de confianza del clasificador débil.
	 * @param conjuntoEntrenamiento Conjunto de caras sobre las que se entrena el clasificador.
	 */
	public void entrenaClasificador(ArrayList<Cara> conjuntoEntrenamiento)
	{
		error = 0;
		for (Cara i : conjuntoEntrenamiento)
		{
			if (h(i) != i.getTipo())
				error += i.getProbabilidad();
		}
		valorConfianza = 0.5 * Math.log10((1 - error) / error);
	}
	
	/**
	 * Predice si una cara está de frente o de perfíl según el clasificador débil definido por un único hiperplano.
	 * @param cara Cara que se va a evaluar.
	 * @return Devuelve 1 si está de frente o -1 si no lo está.
	 */
	public double h(Cara cara)
	{
		if (hiperplano.evaluar(cara.getData()) < 0)
			return -1;
		else
			return 1;
	}
}
