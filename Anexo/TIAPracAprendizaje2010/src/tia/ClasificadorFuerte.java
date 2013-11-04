package tia;

import java.util.ArrayList;

public class ClasificadorFuerte
{
	private ArrayList<ClasificadorDebil> clasificadores;
	
	/**
	 * Constructor por defecto que crea un clasificador fuerte sin ningún clasificador débil.
	 */
	public ClasificadorFuerte()
	{
		clasificadores = new ArrayList<ClasificadorDebil>();
	}
	
	/**
	 * Añade un clasificador débil al conjunto de clasificadores, haciendo más fuerte el clasificador.
	 * @param clasificador Clasificador débil que se va a añadir.
	 */
	public void anadirClasificador(ClasificadorDebil clasificador)
	{
		clasificadores.add(clasificador);
	}
	
	/**
	 * Predice si una cara está de frente o de perfíl según el clasificador fuerte.
	 * @param cara Cara que se va a evaluar.
	 * @return Devuelve 1 si está de frente o -1 si no lo está.
	 */
	public double H(Cara cara)
	{
		double resultado = 0;
		for (ClasificadorDebil i : clasificadores)
		{
			resultado += i.getValorConfianza() * i.h(cara);
		}
		if (resultado < 0)
			return -1;
		else
			return 1;
	}
	
	/**
	 * Predice si una cara está de frente o de perfíl según el clasificador fuerte.
	 * Esta sobrecarga del método H() permite una limitación del uso de clasificadores débiles a utilizar (sirve como depuración
	 * y para medir el rendimiento en las diferentes fases y con diferente cantidad de clasificadores).
	 * 
	 * @param cara Cara que se va a evaluar.
	 * @param c Cantidad de clasificadores débiles que van a utilizarse para la predicción.
	 * @return Devuelve 1 si está de frente o -1 si no lo está.
	 */
	public double H(Cara cara, int c)
	{
		double resultado = 0;
		for (ClasificadorDebil i : clasificadores)
		{
			resultado += i.getValorConfianza() * i.h(cara);
			if (c-- <= 0)
				break;
		}
		if (resultado < 0)
			return -1;
		else
			return 1;
	}
}
