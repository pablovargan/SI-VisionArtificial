**Práctica 2: Visión Artificial y Aprendizaje**

28/10/2013
> Sesión 1: Inicio y especificación del proyecto

4/11/2013
> Sesión 2: Diseño e implementación del hiperplano

> Añadida la carpeta 'Anexo' con práctica de apoyo para la realización de la práctica.

7/11/2013
> Implementado los [min,max] de cada cara.
> Obtenido en un vector en el método *getPuntos()* todos los valores mínimos y máximos de todas las caras.
> Valores entre 0 y 255 (está en escala de grises)

11/11/2013
> Sesión 3: Diseño de un clasificador débil
> 
> **TO-DO: Convertir hiperplano en clasificador y crear una nueva clase hiperplano.**

17/11/2013
> Creada la clase **Hiperplano** que crea el vector de puntos entre 1 y 255 puntos o entre los puntos obtenidos entre los vectores de mínimos y máximos de todas las caras. Se normaliza este vector para que tenga la misma dirección y sentido que el vector dado y se calcula C como punto del espacio aleatorio. Además incluye el método evaluar que a partir de un punto en un plano genera el resultado de en que lado queda el punto.
> 
> Creada la clase **ClasificadorDebil** en el que a partir del NUMCLASIFICADORES, minPuntos y maxPuntos genera los hiperplanos dependiendo del NUMCLASIFICADORES. Implementado el conjunto de aprendizaje que va evaluando cada hiperplano con las caras para decidir el número de aciertos y fallos para ver cual es el mejor hiperplano calculando la tasa de aciertos (%) y asignamos cual es el mejor hiperplano para la lista de aprendizaje que le pasamos por parámetro.

18/11/2013
> Sesión 4: Diseño del algoritmo AdaBoost

> Añadida la propiedad probabilidad a la clase Cara para que el algormitmo pueda establecer y obtener los pesos asociados a esa cara y añadida la clase AdaBoost.
> 
> **Modificado** en Hiperplano el término C que mejora la tasa de aciertos y voltea el resultado llegan a generar un 90% de aciertos. (Comentados los errores en la implementación).

01/12/2013
> Comentado y refactorizado el código.
> Creada la clase clasificador fuerte.

9/12/2013
> Reimplementada la clase Hiperplano y clasificador debil con el método evaluar para que determine en que parte del plano se encuentra.
> Rediseñado el conjunto de aprendizaje para obtener mejores resultados.
> Implementado el clasificador fuerte para que a partir de de varios clasificadores débiles lo entrenemos para mejorarlo y quedarnos con el mejor que valor de confianza nos de.
> Metodo que nos da el clasificador débil elegido para el clasificador fuerte.
> **TODO** Obtener gráficas de resultados, evitar sobre-entrenamiento y diseñar-implementar el perceptron simple.

10/12/2013
> Creada la rama develop para modificar que se cree un hiperplano para un único clasificador débil y luego entrenar estos en el clasificador débil y obtener los mejores para entrenarlos y mejorarlos.
> **TODO** Pruebas nuevas de clasificador débil.

11/12/2013
> Implementadas las pruebas y finalizados los TODO's
 
13/12/2013
> Finalizada la práctica
> Publicada la realease