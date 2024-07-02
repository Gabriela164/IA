###############################

Práctica 07: Perceptrón
Curso: Inteligencia Artificial 2024-1
Alumn@: López Diego Gabriela
Num. Cuenta: 318243485

############################### 

------REPORTE-------
Observaciones de los perceptrones entrenados con cada uno de los conjuntos de entrenamiento. 

**PERCEPTRON AND**
---> Conjunto de entradas de entrenamiento 1 = [([0,0,0,0]),([1,1,1,1])]

Cuando el perceptrón se entrena con este conjunto de entradas, es fácil notar que su aprendizaje se ve limitado. Aprende solo que las entradas [0,0,0] arrojan el resultado 0 y las entradas [1,1,1] arrojan el resultado 1 de toda la tabla de verdad AND. Sin embargo, cuando se le da una entrada que no está en el conjunto de entrenamiento, el perceptrón no sabe qué hacer y arroja un resultado aleatorio. Por ejemplo, si elegimos dicho conjunto de entrenamiento, nuestro programa al ejecutarlo nos arroga el siguiente resultado: 
-------------------------------------------
Introduzca una opción: 1

ENTRENANDO PERCEPTRÓN
Iteración: 1, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (lo aprendió)
Entrada: [1, 0, 1], Salida: 1
Entrada: [0, 0, 1], Salida: 0
Entrada: [1, 1, 1], Salida: 1 (lo aprendió)
-------------------------------------------

Vemos que apesar de que logramos entrenar el precepton, 
cuando le damos una entrada que no está en el conjunto de entrenamiento, el precepton puede dar un resultado erróneo de la tabla de verdad AND.

---> Conjunto de entradas de entrenamiento 2 = [
                    [0, 0, 0, 0],
                    [0, 0, 1, 0],
                    [0, 1, 0, 0],
                    [0, 1, 1, 0],
                    [1, 0, 0, 0],
                    [1, 0, 1, 0],
                    [1, 1, 0, 0],
                    [1, 1, 1, 1]]

Con este conjunto de entretamiento el perceptrón es entrenado de forma correcta. Se le otrogan al preceptron todas las entradas de la tabla de verdad AND y el perceptron aprende a dar el resultado correcto para cada una de ellas. Por ejemplo, si elegimos dicho conjunto de entrenamiento, nuestro programa al ejecutarlo nos arroga el siguiente resultado:

-------------------------------------------
Iteración: 1390, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (lo aprendió)
Entrada: [1, 0, 1], Salida: 0 (lo aprendió)
Entrada: [0, 0, 1], Salida: 0 (lo aprendió)
Entrada: [1, 1, 1], Salida: 1 (lo aprendió)
-------------------------------------------
Vemos que el perceptron aprendió correctamente la tabla de verdad AND.

----> Conjunto de entrenamiento 3 = [[1,1,1,1], [1,0,0,0]]

De forma similar, que el conjunto de entrenamiento 1. El perceptrón se ve fuertemente limitado por solo aprender que las entradas [1,1,1] arroja la salida 1 y que la entrada [1,0,0] arroja la salida 0 de toda la tabla de verdad AND. Por lo cual, con otra entrada que no esté en este conjunto de entrenamiento, muy probablemente arrojará una salida erronea. Por ejemplo, 
si elegimos dicho conjunto de entrenamiento, nuestro programa al ejecutarlo nos arroga el siguiente resultado:
------------------------------------------
ENTRENANDO PERCEPTRÓN
Iteración: 1, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (correcto pero no lo aprendió)
Entrada: [1, 0, 1], Salida: 1 (erróneo, no lo aprendió)
Entrada: [0, 0, 1], Salida: 1 (erróneo, no lo aprendió)
Entrada: [1, 1, 1], Salida: 1 (lo aprendió)
------------------------------------------ 

--> Conjunto de entrenamiento 4 = = [[0,0,1,0],[0,1,0,0]]

Lo mismo ocurre que el caso anterior. El perceptrión aprende que 
las entradas [0,0,1] arrojan la salida 0 y que las entradas [0,1,0] arrojan la salida 0 de toda la tabla de verdad AND. Por lo cual, con otra entrada que no esté en este conjunto de entrenamiento, muy probablemente arrojará una salida erronea. Por ejemplo,
-------------------------------------------
Iteración: 1003, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (correcto pero no lo aprendió)
Entrada: [1, 0, 1], Salida: 1 (erróneo, no lo aprendio)
Entrada: [0, 0, 1], Salida: 0 (lo aprendió)
Entrada: [1, 1, 1], Salida: 1 (correcto pero no lo aprendió)
-------------------------------------------


--> Conjunto de entrenamiento 5 = [[1,0,1,0],[0,0,0,0],[0,1,1,0]]

De manera analoga, observamos lo mismo que el caso anterior. 

-------------------------------------------
Iteración: 1682, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (lo aprendió)
Entrada: [1, 0, 1], Salida: 0 (lo aprendió)
Entrada: [0, 0, 1], Salida: 0 (correcto pero no lo aprendió)
Entrada: [1, 1, 1], Salida: 1 (correcto pero no lo aprendió)
------------------------------------------


**Perceptron OR**

---> Conjunto de entradas de entrenamiento 1 = [([0,0,0,0]),([1,1,1,1])]

Lo mismo que el caso del perceptrón AND. Solo aprende que las entradas [0,0,0] arrojan el resultado 0 y las entradas [1,1,1] arrojan el resultado 1 de toda la tabla de verdad OR. 
---------------------------------------------
ENTRENANDO PERCEPTRÓN
Iteración: 1, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (lo aprendió)
Entrada: [1, 0, 1], Salida: 1 (correcto pero no lo aprendió)
Entrada: [0, 0, 1], Salida: 1 (correcto pero no lo aprendió)
Entrada: [1, 1, 1], Salida: 1 (lo aprendió)
---------------------------------------------

--> Conjunto de entrenamiento 2 = [
                    [0, 0, 0, 0],
                    [0, 0, 1, 1],
                    [0, 1, 0, 1],
                    [0, 1, 1, 1],
                    [1, 0, 0, 1],
                    [1, 0, 1, 1],
                    [1, 1, 0, 1],
                    [1, 1, 1, 1]]
En este caso, el perceptrón aprende correctamente la tabla de verdad OR. 
-------------------------------------------
Iteración: 3464, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (lo aprendió)
Entrada: [1, 0, 1], Salida: 1 (lo aprendió)
Entrada: [0, 0, 1], Salida: 1 (lo aprendió)
Entrada: [1, 1, 1], Salida: 1 (lo aprendió)
-------------------------------------------


---> Conjunto de entrenamiento 3 = [[1,1,1,1], [1,0,0,1]]

El perceptrón solo aprende que las entradas [1,1,1] arrojan la salida 1 y que las entradas [1,0,0] arrojan la salida 1 de toda la tabla de verdad OR.

-------------------------------------------
PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (correcto pero no lo aprendió)
Entrada: [1, 0, 1], Salida: 1 (correcto pero no lo aprendió)
Entrada: [0, 0, 1], Salida: 0 (erróneo, no lo aprendio)
Entrada: [1, 1, 1], Salida: 1 (lo aprendió)
-------------------------------------------

Vemos que apesar lograr entrenar el perceptrón este arroja un resultado erróneo para la entrada [0,0,1] pues segun la tabla de verdad OR, la salida debe ser 1 y el perceptrón arroja la salida 0.

--> Conjunto de entrada 4 = = [[0,0,1,1],[0,1,0,1]]

El perceptrón solo aprende que las entradas [0,0,1] arrojan la salida 1 y que las entradas [0,1,0] arrojan la salida 1 de toda la tabla de verdad OR.

------------------------------------------
Iteración: 5956, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (correcto pero no lo aprendió)
Entrada: [1, 0, 1], Salida: 0 (erróneo,  no lo aprendio)
Entrada: [0, 0, 1], Salida: 1 (lo aprendió)
Entrada: [1, 1, 1], Salida: 1 (correcto pero no lo aprendió)
-------------------------------------------

--> Conjunto de entrenamiento 5 = [[0,0,0,0],[1,0,1,1],[0,1,1,1]]

-----------------------------------
ENTRENANDO PERCEPTRÓN
Iteración: 1, Error: 0

PERCEPTRÓN ENTRENADO :D

Resultado del perceptrón:
Entrada: [0, 0, 0], Salida: 0 (lo aprendió)
Entrada: [1, 0, 1], Salida: 1 (lo aprendió)
Entrada: [0, 0, 1], Salida: 0 (erróneo, no lo aprendió)
Entrada: [1, 1, 1], Salida: 1 (correcto pero no lo aprendió)
-----------------------------------