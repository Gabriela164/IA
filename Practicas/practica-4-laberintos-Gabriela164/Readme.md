PRÁCTICA 4: Recursión y Retractación (Backtracking)
Alumn@: Lopez Diego Gabriela
Num. De Cuenta: 318243485
Curso: Inteligencia Artificial 2024-1

Compilar --->
javac -d ./Classes -cp lib/core.jar:. laberintos/*.java

Ejecutar --->
java -cp ./Classes:lib/core.jar laberintos.Laberinto

RESULTADO DE EJECUCIÓN:

Al ejecutarlo podremos observar un tablero de dimensión NxN donde:
-Las celdas visitadas son de color azul
-Las celdas actuales y visitadas(al mismo tiempo) son de color rojo
-Las celdas que no han sido visitadas o no son visitadas y actuales al mismo tiempo, serán de color gris.

El algoritmo que implementamos logra recorrer todo el laberinto de manera exitosa utilizando recursión y backtracking. Por lo cual, se pintará todo de color azul. Sin embargo, dejamos la ultima celda que fue visitada de color rojo para poder visualizar la ejecución del algoritmo de una mejor manera. Recordemos que las celdas rojas también cumplen con ser visitadas. 



