package practica2si;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase cara. Representa un ejemplo para el aprendizaje.
 * @author dviejo
 */
public class Cara {
        // Vector de cada cara
	private int []data;
	private int tipo;
        // Peso de la cara
        private double peso;
        
        /**
         * Lee la información de una imagen desde un fichero. Le asigna el tipo
         * que se recibe como parametro
         * @param fcara fichero de entrada
         * @param tipo 1 = cara; -1 = nocara
         */
        public Cara(File fcara, int tipo)
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
			for(cont=0;cont<data.length;cont++)
				data[cont] = data[cont] & mask;
			this.tipo = tipo;
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
                // Peso inicialmente a 0
                this.peso = 0;
	}
	
	public int []getData()
	{
		return data;
	}
	
	public int getTipo()
	{
		return tipo;
	}
	
	public void setTipo(int newtipo)
	{
		tipo = newtipo;
	}
        
        // El algoritmo Adaboost obtiene y establece el peso para la cara
        public double getPeso() { return peso; }
        public void setPeso(double peso)
        {
            this.peso = peso;
        }
        
        // Obtiene el menor punto
        public int getMin() 
        {
            int minimo = 576;
            for(int i = 0; i < data.length; i++) {
                if(data[i] < 576)
                    minimo = data[i];
            }
            return minimo;
        }
        
        // Obtiene el maximo punto
        public int getMax() 
        {
            int maximo = 0;
            for(int i = 0; i < data.length; i++) {
                if(data[i] > maximo)
                    maximo = data[i];
            }
            return maximo;
        }
}
