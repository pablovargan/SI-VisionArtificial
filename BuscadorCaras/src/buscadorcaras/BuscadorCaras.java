/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buscadorcaras;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Pablo
 */
public class BuscadorCaras {
    // Ruta y ficheros de las imagenes
    private File []files;
    private String rutaCaras;
    private static final double testRate = 0.5;
    private ArrayList<Cara> listaAprendizaje;
    private ArrayList<Cara> listaTest;
    // Ruta y fichero de la imagen para buscar las caras
    private BufferedImage imagen;
    private String rutaImagen;
    
    // Constructor
    public BuscadorCaras() {}
    
    public void setRutaCaras(String rutaCaras) { this.rutaCaras = rutaCaras; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    
    public void init()
    {
        initClasificador();
        // Recojo la imagen
        imagen = null;
        try {
          imagen = ImageIO.read(new File(rutaCaras));
        } catch (IOException ex) {
            System.out.println("No se pudo leer la imagen");
        }
        
    }
    
    private void initClasificador()
    {
        int cont;
        System.out.println("BuscadorCaras. Segunda práctica.");
        System.out.println("--------------------------------");
        // Caras 
        getFileNamesCaras(rutaCaras+"cara/");
	listaAprendizaje = new ArrayList<Cara>();
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			listaAprendizaje.add(new Cara(files[cont],1));
		}
	}
	getFileNamesCaras(rutaCaras+"noCara/");
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			listaAprendizaje.add(new Cara(files[cont], -1));
		}
	}
        listaTest = new ArrayList<Cara>();
        CrearConjuntoAprendizajeyTest();
        // Obtengo los 2 vectores con min y max
        int []minimos = this.getMinimos();
        int []maximos = this.getMaximos();
        ClasificadorFuerte cFuerte = new ClasificadorFuerte();
        cFuerte.adaBoost(10, listaAprendizaje, 5, minimos, maximos);
    }
    
    private void getFileNamesCaras(String ruta)
    {
    	File directorio = new File(ruta);
	if (!directorio.isDirectory())
            throw new RuntimeException("La ruta debe ser un directorio");
	ImageFilter filtro = new ImageFilter();
        files = directorio.listFiles(filtro);
    }
    
    // Selecciona al azar un subconjunto para Test. El resto compondrá el conjunto de aprendizaje
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
    
    private int[] getMinimos() 
    {
        int []aux = new int[listaAprendizaje.size()];
        for(int i = 0; i < listaAprendizaje.size(); i++)
            aux[i] = listaAprendizaje.get(i).getMin();
        
        return aux;
    }
    
    private int[] getMaximos() 
    {
        int []aux = new int[listaAprendizaje.size()];
        for(int i = 0; i < listaAprendizaje.size(); i++)
            aux[i] = listaAprendizaje.get(i).getMax();
        
        return aux;
    }
    
    private void getPuntos() 
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
        // Recibe por parametro una imagen con -d y la ruta
        if(args.length == 4) 
        {
            BuscadorCaras bC = new BuscadorCaras();
            // Ruta de las caras
            if(args[0].equals("-d")) 
            {
                bC.setRutaCaras(args[1]);
            }
            // Ruta de la imagen
            if(args[2].equals("-i"))
            {
                bC.setRutaImagen(args[3]);
            }
            bC.init();
        }
        else
        {
            System.out.println("Uso: java BuscadorCaras -d ruta ");
        }
    }
}
