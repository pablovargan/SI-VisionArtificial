package tia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author dviejo
 * Esta clase encapsula los ejemplos a utilizar en el algoritmo AdaBoost. Incorpora la funcionalidad
 * para leer un fichero de imagen del disco. La imagen se almacena en un vector de enteros donde los 
 * colores están representados por niveles de gris entre 0 y 255.
 */
public class Cara
{

	private int[] data;
	private double tipo;
	private double probabilidad;

	/**
	 * Lee la información de una cara desde el disco duro. Almacena la información en el array <b>data</b>
	 * @param ruta Ruta al fichero 
	 */
	public Cara(File fcara)
	{
		int mask = 0x000000FF;
		int cont;
		BufferedImage bimage;
		try
		{
			bimage = ImageIO.read(fcara);
			data = bimage.getRGB(0, 0, bimage.getWidth(), bimage.getHeight(), null, 0, bimage.getWidth());
			//Asumiendo que la imagen ya está en escala de grises pero en formato color, 
			//convertimos ARGB en un único valor
			for (cont = 0; cont < data.length; cont++)
			{
				data[cont] = data[cont] & mask;
			}
			tipo = computeType(fcara.getName());
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Devuelve el vector de datos que representa la cara
	 * @return vector de datos
	 */
	public int[] getData()
	{
		return data;
	}

	/**
	 * Devuelve el tipo de la cara. 1 para las caras de frente, -1 para las que no lo están.
	 * @return tipo de la cara.
	 */
	public double getTipo()
	{
		return tipo;
	}

	/**
	 * Establece el tipo de la cara (1 o -1).
	 * @param newtipo El nuevo tipo a asociar a la cara
	 */
	public void setTipo(double newtipo)
	{
		tipo = newtipo;
	}

	/**
	 * Devuelve el peso de la cara asociado por el algoritmo AdaBoost D(i)
	 * @return peso de la cara D(i)
	 */
	public double getProbabilidad()
	{
		return probabilidad;
	}

	/**
	 * Establece el peso de la cara para su uso en el algoritmo AdaBoost D(i)
	 * @param newprob Nuevo valor para el peso D(i)
	 */
	public void setProbabilidad(double newprob)
	{
		probabilidad = newprob;
	}

	/**
	 * Calcula el tipo de la cara a partir del nombre de la imagen. Podrá ser 1 en caso de una cara
	 * de frente o -1 en caso de no estar de frente.
	 * @param name Nombre de la imagen
	 * @return Tipo (1 o -1)
	 */
	public double computeType(String name)
	{
		int pos = name.indexOf("v");
		if (Integer.parseInt(name.substring(pos + 1, pos + 2)) == 1)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}
