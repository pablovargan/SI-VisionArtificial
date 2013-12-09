/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2si;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dviejo
 */
public class Practica2SI {

    private String rutaDir;
    private File []files;
    private int NUM_ITERACIONES; 
    private int NUM_CLASIFICADORES;
    private boolean VERBOSE;

    private double testRate;

    private ArrayList<Cara> listaAprendizaje;
    private ArrayList<Cara> listaTest;
    // Lista de caras
    private ArrayList<Cara> caras;

    public Practica2SI()
    {
        rutaDir = "";
        testRate = 0.5;
	NUM_ITERACIONES = 1;
	NUM_CLASIFICADORES = 1;
        VERBOSE = false;
    }
    
    public void init()
    {
        int cont;
	int aciertos, clase;
	System.out.println("Sistemas Inteligentes. Segunda práctica");
		
	getFileNames(rutaDir+"cara/");
	listaAprendizaje = new ArrayList<Cara>();
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			listaAprendizaje.add(new Cara(files[cont],1));
		}
	}
	getFileNames(rutaDir+"noCara/");
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			listaAprendizaje.add(new Cara(files[cont], -1));
		}
	}
	System.out.println(listaAprendizaje.size()+ " imágenes encontradas");
        // Hago una copia de las caras obtenidas
        caras = new ArrayList<Cara>();
        caras = listaAprendizaje;
	//inicializamos las listas
	listaTest = new ArrayList<Cara>();
		
	//Separamos los conjuntos de aprendizaje y test
	CrearConjuntoAprendizajeyTest();
	System.out.println(listaAprendizaje.size()+" imagenes para aprendizaje, "+listaTest.size()+" imagenes para el test ("+(testRate*100)+"%)");

	//Comenzamos el aprendizaje
	long t1 = System.currentTimeMillis();
        //TODO Aquí debéis poner vuestra llamada al método de entrenamiento de AdaBoost
	long t2 = System.currentTimeMillis();
	long time;
        
	time = t2 - t1;
	System.out.println("Tiempo empleado en el aprendizaje: "+((float)time/1000f)+" segundos");
	System.out.println("Número de clasificadores encontrados: "); //TODO añadir el valor
	/*
        //Test final
        if(VERBOSE)
        {
            aciertos = 0;
            for(Cara c: listaAprendizaje)
            {
                clase = -1;  //TODO Cambiar -1 por la llamada a clasificar utilizando el clasificador fuerte
                        //de Adaboost para el ejemplo c
                if(clase == c.getTipo())
                    aciertos++;
            }
            System.out.println("APRENDIZAJE. Tasa de aciertos: "+((float)aciertos/(float)(listaAprendizaje.size())*100.0f)+"%");
        }
        
	//Comprobamos el conjunto de test
	aciertos = 0;
	for(Cara c: listaTest)
	{
		clase = -1;  //TODO Cambiar -1 por la llamada a clasificar utilizando el clasificador fuerte
                            //de Adaboost para el ejemplo c
		if(clase == c.getTipo())
			aciertos++;
	}
	System.out.println("TEST. Tasa de aciertos: "+((float)aciertos/(float)(listaAprendizaje.size())*100.0f)+"%");
        */
        // Obtengo los 2 vectores con min y max
        int []minimos = this.getMinimos();
        int []maximos = this.getMaximos();
        //Hiperplano h = new Hiperplano(minimos,maximos);
        // Ejemplo creando hiperplanos con los puntos
        ClasificadorDebil cd = new ClasificadorDebil(NUM_CLASIFICADORES, minimos, maximos);
        cd.conjuntoAprendizaje(listaAprendizaje);
        //cd.testMejor(listaTest);
        
        //ClasificadorFuerte cf = new ClasificadorFuerte();
        //cf.adaBoost(NUM_CLASIFICADORES, listaAprendizaje, 9, minimos, maximos);
    }
    
    /**
     * Selecciona al azar un subconjunto para Test. El resto compondrá el conjunto de aprendizaje
     */
    private void CrearConjuntoAprendizajeyTest()
    {
    	int totalTam = listaAprendizaje.size();
	int tamTest = (int)(totalTam * testRate);
	int cont;
	Random rnd = new Random(System.currentTimeMillis());
		
	for(cont=0;cont<tamTest;cont++)
	{
            listaTest.add(listaAprendizaje.remove(rnd.nextInt(totalTam)));
            totalTam--;
	}
    }
    
    public void getFileNames(String ruta)
    {
    	File directorio = new File(ruta);
	if (!directorio.isDirectory())
            throw new RuntimeException("La ruta debe ser un directorio");
	ImageFilter filtro = new ImageFilter();
	files = directorio.listFiles(filtro);
    }

    public void setRuta(String r)
    {
            rutaDir = r;
    }

    public void setRate(double t)
    {
            testRate = t;
    }

    public void setNumIteraciones(int t)
    {
            NUM_ITERACIONES = t;
    }
    public void setNumClasificadores(int c)
    {
            NUM_CLASIFICADORES = c;
    }

    public void setVerbose(boolean v)
    {
            VERBOSE = v;
    }
    
    public int[] getMinimos() 
    {
        int []aux = new int[listaAprendizaje.size()];
        for(int i = 0; i < listaAprendizaje.size(); i++)
            aux[i] = listaAprendizaje.get(i).getMin();
        
        return aux;
    }
    
    public int[] getMaximos() 
    {
        int []aux = new int[listaAprendizaje.size()];
        for(int i = 0; i < listaAprendizaje.size(); i++)
            aux[i] = listaAprendizaje.get(i).getMax();
        
        return aux;
    }
    
    public void getPuntos() 
    {
        // Obtengo los 2 vectores con min y max
        int []minimos = this.getMinimos();
        int []maximos = this.getMaximos();
               
        for(int i = 0; i < minimos.length; i++)
            System.out.println("Min, Max: [" + minimos[i] + "," + maximos[i] + "]");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		int cont;
		Practica2SI programa;
		String option;
		boolean maluso = true;
		int paso = 2;
		
		programa = new Practica2SI();

		for(cont = 0; cont < args.length; cont+=paso)
		{
			option = args[cont];
			if(option.charAt(0) == '-')
			{
				switch(option.charAt(1))
				{
				case 'd':
					programa.setRuta(args[cont+1]);
					paso = 2;
                                        maluso = false;
					break;
				case 't':
					programa.setRate(Double.parseDouble(args[cont+1]));
					paso = 2;
					break;
				case 'T':
					programa.setNumIteraciones(Integer.parseInt(args[cont + 1]));
					paso = 2;
					break;
				case 'c':
					programa.setNumClasificadores(Integer.parseInt(args[cont + 1]));
					paso = 2;
					break;
				case 'v':
					programa.setVerbose(true);
					paso = 1;
					break;
				default:
					maluso = true;
				}
			}
			else maluso = true;
		}
		
		if(!maluso) {
			programa.init();
                }
		else
		{
			System.out.println("Lista de parametros incorrecta");
			System.out.println("Uso: java Practica2SI -d ruta [-t testrate] [-T maxT] [-c numClasificadores] [-v]");
		}
    }
}
