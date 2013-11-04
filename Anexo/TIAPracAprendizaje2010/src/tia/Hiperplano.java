package tia;

public class Hiperplano
{
	public static final int DIMENSIONES = 3087;
	public static final int MIN_VALUE = 0;
	public static final int MAX_VALUE = 255;
	
	private double coeficientes[];
	private double terminoIndependiente;
	
	/**
	 * Constructor por defecto que genera un hiperplano aleatorio según los límites establecidos en las constantes de la clase.
	 */
	public Hiperplano()
	{
		coeficientes = new double[DIMENSIONES];
		terminoIndependiente = 0;
		
		// Generamos aleatoriamente un vector normal de dirección para el hiperplano.
		for (int i = 0; i < DIMENSIONES; i++)
		{
			coeficientes[i] = Math.random() * ((Math.random() < 0.5) ? -1 : 1);
		}
		
		// Normalizamos el vector.
		double modulo = 0;
		for (int i = 0; i < DIMENSIONES; i++)
			modulo += coeficientes[i] * coeficientes[i];
		modulo = Math.sqrt(modulo);
		for (int i = 0; i < DIMENSIONES; i++)
			coeficientes[i] /= modulo;
		
		// Calculamos aleatoriamente un punto en el espacio (dentro de los límites 0-255) para obtener el valor del término independiente.
		for (int i = 0; i < DIMENSIONES; i++)
		{
			terminoIndependiente -= coeficientes[i] * (((int) (Math.random() * 1000)) % (MAX_VALUE + 1));
		}
	}
	
	/**
	 * Obtiene el array de coeficientes del vector normal del hiperplano.
	 * Dado que se devuelve una referencia, permite modificar los elementos del vector.
	 * @return Devuelve una referencia al vector de coeficientes de la normal del hiperplano.
	 */
	public double[] getCoeficientes()
	{
		return coeficientes;
	}
	
	/**
	 * Evalúa un punto en el plano.
	 * Genera el resultado de comprobar a qué lado del hiperplano queda el punto.
	 * @param punto Punto con tantas dimensiones como el plano.
	 * @return Devuelve 0 si el punto está contenido en el hiperplano. Un valor positivo si está por encima o negativo si está por debajo del hiperplano.
	 */
	public double evaluar(int[] punto)
	{
		double resultado = 0;
		
		for (int i = 0; i < coeficientes.length; i++)
		{
			resultado += coeficientes[i] * punto[i];
		}
		
		return resultado + terminoIndependiente;
	}
}
