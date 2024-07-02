import random

#PASO 1: Inicialización de los pesos y el umbral
def inicializacion(num_entradas):
    pesos = [random.uniform(-0.5, 0.5) for _ in range(num_entradas)]
    umbral = random.uniform(-0.5, 0.5)
    return pesos, umbral

#PASO 2: Función de activación (función escalón)
def funcion_activacion(entrada_neta):
    if entrada_neta >= 0:
        return 1
    else:
        return 0

# PASO 3: Entrenamiento del perceptrón
def entrenar_perceptron(entradas, tasa_aprendizaje, pesos, umbral):
    entradas_sin_salida = entradas[:3]
    entrada_neta = sum(x * w for x, w in zip(entradas_sin_salida, pesos)) - umbral
    salida = funcion_activacion(entrada_neta)
    meta = entradas[3]
    error = meta - salida
    
    # Actualización de pesos (el umbral se queda fijo)
    for i in range(len(pesos)):
        pesos[i] += tasa_aprendizaje * entradas_sin_salida[i] * error
    return pesos

# Conjuntos de entrenamiento (x1, x2, x3, salida deseada)
conjunto_entrenamiento1 = [[0,0,0,0],[1,1,1,1]]
conjunto_entrenamiento2 = [
                    [0, 0, 0, 0],
                    [0, 0, 1, 1],
                    [0, 1, 0, 1],
                    [0, 1, 1, 1],
                    [1, 0, 0, 1],
                    [1, 0, 1, 1],
                    [1, 1, 0, 1],
                    [1, 1, 1, 1]]
conjunto_entrenamiento3 = [[1,1,1,1], [1,0,0,1]]
conjunto_entrenamiento4 = [[0,0,1,1],[0,1,0,1]]
conjunto_entrenamiento5 = [[0,0,0,0],[1,0,1,1],[0,1,1,1]]

print("\nPERCEPTRÓN OR")
#Preguntamos al usuario que conjunto de entrenamiento desea utilizar para entrenar al precepton
print("¿Qué conjunto de entrenamiento desea utilizar? (Se recomienda el 2)")
print("1." + str(conjunto_entrenamiento1))
print("2." + str(conjunto_entrenamiento2))
print("3." + str(conjunto_entrenamiento3))
print("4." + str(conjunto_entrenamiento4))
print("5." + str(conjunto_entrenamiento5))
print("6. Salir")

while True:
    try:
        opcion = int(input("Introduzca una opción: "))
        if opcion < 1 or opcion > 6:
            print("Ingrese un número entre 1 y 6.")
        else:
            break  
    except ValueError:
        print("Entrada no válida. Debe ingresar un número.")
if opcion == 1:
    conjunto_entrenamiento = conjunto_entrenamiento1
elif opcion == 2:
    conjunto_entrenamiento = conjunto_entrenamiento2
elif opcion == 3:
    conjunto_entrenamiento = conjunto_entrenamiento3
elif opcion == 4:
    conjunto_entrenamiento = conjunto_entrenamiento4
elif opcion == 5:
    conjunto_entrenamiento = conjunto_entrenamiento5
else:
    exit()


tasa_aprendizaje = 0.0001 #Variable no negativa menor que 1 que determina la velocidad de aprendizaje
iteraciones = 10000   #Número de veces que se repite el entrenamiento
num_entradas = 3    #Variables de entrada x1, x2, x3
 
# Inicialización de pesos y umbral
pesos, umbral = inicializacion(num_entradas)

# PASO 4: Iteracion: Repetimos el paso 2 y 3 para cada ejemplar del conjunto de entrenamiento
# hasta minimizar lo mejor posible el error. En este caso, hasta que el error sea 0.
print("\nENTRENANDO PERCEPTRÓN")

for iteracion in range(iteraciones):
    error_total = 0
    for datos_entrada in conjunto_entrenamiento:
        pesos = entrenar_perceptron(datos_entrada, tasa_aprendizaje, pesos, umbral)
        error = abs(datos_entrada[3] - funcion_activacion(sum(x * w for x, w in zip(datos_entrada[:3], pesos)) - umbral))
        error_total += error
    # Mostramos el proceso de cada iteración en la consola
    print(f"Iteración: {iteracion + 1}, Error: {error_total}")

    if error_total == 0: #Si el error es 0, el perceptrón está entrenado
        print("\nPERCEPTRÓN ENTRENADO :D")
        break
    elif iteracion == iteraciones - 1:
        print("\nPERCEPTRÓN NO ENTRENADO. Intenta de nuevo :(")

#Conjunto de entradas para probar el perceptrón
conjunto_entradas = [[0, 0, 0], [1, 0, 1], [0, 0, 1], [1, 1, 1]]

# Mostramos la evaluacion del perceptrón con las entradas del conjunto de entradas
print("\nResultado del perceptrón:")
for entrada in conjunto_entradas:
    resultado = funcion_activacion(sum(x * w for x, w in zip(entrada, pesos)) - umbral)
    print(f"Entrada: {entrada}, Salida: {resultado}")
