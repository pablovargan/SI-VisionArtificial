/**
 * 
 */
package tia;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author dviejo
 *
 */
public class Main
{

	private String rutaDir;
	private File[] files;
	//Parametros AdaBoost
	private double testRate;
	private int NUM_CANDIDATOS;
	private int NUM_CLASIFICADORES;
	private boolean PERCEPTRON_SIMPLE;
	private boolean VERBOSE;

	private ArrayList<Cara> listaEntrenamiento;
	private ArrayList<Cara> listaTest;

	public Main()
	{
		rutaDir = "";
		testRate = 0.1;
		NUM_CANDIDATOS = 1000;
		NUM_CLASIFICADORES = 20;
		PERCEPTRON_SIMPLE = false;
		VERBOSE = false;
	}

	public void Init()
	{
		int cont;
		//System.out.println("TIA. Practica de aprendizaje 2010");

		// Cargamos todas las imágenes de las caras en la lista.
		getFileNames(rutaDir);
		listaEntrenamiento = new ArrayList<Cara>();
		for (cont = 0; cont < files.length; cont++)
		{
			if (!files[cont].isDirectory())
			{
				listaEntrenamiento.add(new Cara(files[cont]));
			}
		}
		
		// Se reordena aleatoriamente la lista de caras.
		Collections.shuffle(listaEntrenamiento);
	
		// Se divide entre un conjunto de aprendizaje y un conjunto de test.
		listaTest = new ArrayList<Cara>();
		int totalCaras = listaEntrenamiento.size();
		for (int i = 0; i < totalCaras * testRate; i++)
		{
			listaTest.add(listaEntrenamiento.remove(0));
		}
		
		System.out.println("Clasificadores débiles: " + NUM_CLASIFICADORES);
		System.out.println("Candidatos aleatorios:  " + NUM_CANDIDATOS);
		System.out.println("Test rate:  " + testRate);
		System.out.println("Usando PERCEPTRÓN: " + PERCEPTRON_SIMPLE);
		System.out.println("-----------------------------------------------");
		
		// Aplicamos el algoritmo AdaBoost para obtener un clasificador.
		ClasificadorFuerte clasificadorFuerte = AdaBoost.ejecutarAlgoritmo(NUM_CLASIFICADORES, NUM_CANDIDATOS, listaEntrenamiento, PERCEPTRON_SIMPLE);
		System.out.println("-----------------------------------------------");
		
		// Evaluamos el error de entrenamiento.
		int aciertosEntrenamiento = 0;
		for (Cara j : listaEntrenamiento)
			if (clasificadorFuerte.H(j) == j.getTipo())
				aciertosEntrenamiento++;

		// Evaluamos el error de test.
		int aciertosTest = 0;
		for (Cara j : listaTest)
			if (clasificadorFuerte.H(j) == j.getTipo())
				aciertosTest++;
		
		System.out.println("Entrenamiento: " + " " + aciertosEntrenamiento + "/" + listaEntrenamiento.size() + " (" + (100.0*aciertosEntrenamiento/listaEntrenamiento.size()) + "%)");
		System.out.println("Test:          " + " " + aciertosTest + "/" + listaTest.size() + " (" + (100.0*aciertosTest/listaTest.size()) + "%)");
		
		if (VERBOSE)
		{
			// Salida para las gráficas.
			DecimalFormat formateador = new DecimalFormat ("#########.##");
			System.out.println("\n----- Error de entrenamiento -----");
			for (int i = 0; i < NUM_CLASIFICADORES; i++)
			{
				int fallos = 0;
				for (Cara j : listaEntrenamiento)
					if (clasificadorFuerte.H(j, i) != j.getTipo())
						fallos++;
				System.out.println(formateador.format((100.0 * fallos / listaEntrenamiento.size())));
			}
			System.out.println("\n----- Error de test -----");
			for (int i = 0; i < NUM_CLASIFICADORES; i++)
			{
				int fallos = 0;
				for (Cara j : listaTest)
					if (clasificadorFuerte.H(j, i) != j.getTipo())
						fallos++;
				System.out.println(formateador.format((100.0 * fallos / listaTest.size())));
			}
		}
	}

	public void setRuta(String r)
	{
		rutaDir = r;
	}

	public void setRate(double t)
	{
		testRate = t;
	}

	public void setNumCandidatos(int t)
	{
		NUM_CANDIDATOS = t;
	}

	public void setNumClasificadores(int c)
	{
		NUM_CLASIFICADORES = c;
	}

	private void getFileNames(String ruta)
	{
		File directorio = new File(ruta);
		if (!directorio.isDirectory())
		{
			throw new RuntimeException("La ruta debe ser un directorio");
		}
		ImageFilter filtro = new ImageFilter();
		files = directorio.listFiles(filtro);
	}

	/**
	 * Analiza los parametros y lanza la función
	 * @param args
	 */
	public static void main(String[] args)
	{
		int cont;
		Main programa;
		String option;
		boolean maluso = false;
		boolean ruta = false;
		int paso = 2;

		programa = new Main();

		for (cont = 0; cont < args.length && !maluso; cont += paso)
		{
			option = args[cont];
			if (option.charAt(0) == '-')
			{
				switch (option.charAt(1))
				{
					case 'd':
						programa.setRuta(args[cont + 1]);
						paso = 2;
						ruta = true;
						break;
					case 't':
						programa.setRate(Double.parseDouble(args[cont + 1]));
						paso = 2;
						break;
					case 'T':
						programa.setNumCandidatos(Integer.parseInt(args[cont + 1]));
						paso = 2;
						break;
					case 'c':
						programa.setNumClasificadores(Integer.parseInt(args[cont + 1]));
						paso = 2;
						break;
					case 'v':
						programa.VERBOSE = true;
						paso = 1;
						break;
					case 'n':
						programa.PERCEPTRON_SIMPLE = true;
						PerceptronSimple.CONSTANTE_APRENDIZAJE = Double.parseDouble(args[cont + 1]);
						paso = 2;
						break;
					case 'i':
						programa.PERCEPTRON_SIMPLE = true;
						PerceptronSimple.MAX_ITERACIONES = Integer.parseInt(args[cont + 1]);
						paso = 2;
						break;
					case 'o':
						programa.PERCEPTRON_SIMPLE = true;
						PerceptronSimple.TASA_ACIERTO_OBJETIVO = Double.parseDouble(args[cont + 1]);
						paso = 2;
						break;
					default:
						maluso = true;
				}
			}
			else
			{
				maluso = true;
			}
		}

		if (!maluso && ruta)
		{
			programa.Init();
		}
		else
		{
			System.err.println("Lista de parametros incorrecta");
			System.err.println("Uso: java Main -d ruta [-t testrate] [-T maxT] [-c numClasificadores] [-n constanteAprendizaje] [-i iteracionesReglaDelta] [-o tasaAciertoObjetivo]");
		}

	}
}
