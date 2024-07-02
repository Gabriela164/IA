# SOLUCIÓN AL PROBLEMA DE LAS N REINAS DISTRUIBUIDAS 
# EN UN TABLERO N X N SIN QUE SE AMENACEN ENTRE SI, 
# USANDO ALGORITMOS GENÉTICOS.
  
# La solución se representa como un arreglo de N elementos. Cada elemento del
# arreglo representa el No. de fila en la que se encuentra la reina de la columna 
# correspondiente. Por ejemplo, si N = 8 en un tablero de 8x8, una solucion
# optima seria [4, 2, 7, 3, 6, 8, 1, 5] entonces la primer reina estara en la columna 1 fila 4, la
# segunda reina estara en la columna 2 fila 2, la tercera reina estara en la columna 3 fila 7, etc.

# Alumn@: Gabriela López Diego 
# Curso: Inteligencia Artificial 2024-1
# Fecha: 10 de septiembre del 2023
import random

#Metodo para generar el individuo inicial (parte de la población inicial) 
def individuoInicial(N_reinas):
    individuo1 = []
    for _ in range(N_reinas):
        individuo1.append(random.randint(1,N_reinas))
    return individuo1

#Metodo para calcular la aptitud de un individuo dado 
def funcionAptitud(individuo):
    Num_reinas = len(individuo)
    aptitud = Num_reinas * (Num_reinas - 1) // 2  # Aptitud máxima si ninguna reina se ataca

    # Itera sobre las filas 
    for i in range(Num_reinas - 1):
        for j in range(i + 1, Num_reinas):
            #Verifica si las reinas se atacan
            if individuo[i] == individuo[j] or abs(individuo[i] - individuo[j]) == abs(i - j):
                #Si se atacan, se disminuye la aptitud
                aptitud -= 1                
    return aptitud

#Metodo que implementa seleccion por ruleta
def seleccionRuleta(poblacion, num_padres):
    # Se calcula la aptitud total de la población
    aptitudTotal = sum(funcionAptitud(individuo) for individuo in poblacion)
    seleccionIndividuos = []
    
    # Se seleccionan los padres para la siguiente generación. Aquellos 
    # individuos con mayor aptitud tienen mayor probabilidad de ser seleccionados.
    for _ in range(num_padres):
        ruleta = random.uniform(0, aptitudTotal)
        suma_aptitud = 0
        for individuo in poblacion:
            suma_aptitud += funcionAptitud(individuo)
            if suma_aptitud >= ruleta:
                seleccionIndividuos.append(individuo)
                break
    return seleccionIndividuos


# Metodo que implementa el operador de corte de dos individuos (padres) 
# para generar dos hijos
def corte(padres):
    hijos = []
    for i in range(0, len(padres), 2):
        padre1 = padres[i]
        padre2 = padres[i + 1]
        corte_punto = random.randint(1, len(padre1) - 1)
        hijo1 = padre1[:corte_punto] + padre2[corte_punto:]
        hijo2 = padre2[:corte_punto] + padre1[corte_punto:]
        hijos.extend([hijo1, hijo2])
    return hijos

# Metodo que implementa la mutacion en los hijos generados. 
# Se verifica valor por valor del hijo si se muta o no. Se elige si
# se cambia o no a otra fila de manera aleatoria y con probabilidad 0.2
def mutacion(hijos, probabilidad_mutacion): 
    hijos_con_mutacion = []
    
    #Itera sobre cada uno de los hijos
    for hijo in hijos:
        nuevo_hijo = hijo[:]  # Crear una copia del hijo original
        #Itera sobre cada uno de los valores del hijo
        for i in range(len(hijo)):
            if random.random() < probabilidad_mutacion:
                nuevo_valor = random.randint(1, len(hijo))
                nuevo_hijo[i] = nuevo_valor
        hijos_con_mutacion.append(nuevo_hijo)
    
    #Regresa los hijos con mutacion
    return hijos_con_mutacion


# Metodo que implementa el proceso de elitismo. 
# Se ordena la población por aptitud de manera descendente y se conservan
# a los mejores individuos  
def proceso_Elitismo(poblacion, tamanio_mejoresIndividuos):
    # Ordenamos la población por aptitud de manera descendente
    poblacionOrdenada = sorted(poblacion, key=funcionAptitud, reverse=True)
    # Conservar a los mejores individuos (élite)
    mejores = poblacionOrdenada[:tamanio_mejoresIndividuos]
    return mejores


N_de_reinas = 9  # N reinas (se puede cambiar)
tamanio_poblacion = 100 # Tamaño de la población, numero de individuos
probabilidad_de_mutacion = 0.2 
tamanio_mejoresIndividuos = 40 # Num de mejores individuos que se conservan en la población
max_Generaciones = 10000 
generaciones = 0

# Se genera la población inicial 
poblacion = [individuoInicial(N_de_reinas) for _ in range(tamanio_poblacion)]


# Se itera hasta que se encuentre una solución o se alcance el número máximo de generaciones
while generaciones < max_Generaciones:
    padres = seleccionRuleta(poblacion, tamanio_poblacion)
    hijos = corte(padres)
    hijos_mutados = mutacion(hijos, probabilidad_de_mutacion)
    
    # Seleccionamos los mejores individuos de la poblacion
    mejoresIndividuos = proceso_Elitismo(poblacion, tamanio_mejoresIndividuos)
    # y pasan a la siguiente generación sin alteraciones por recombinacion ni mutacion
    hijos_mutados.extend(mejoresIndividuos)
    poblacion = hijos_mutados
    generaciones += 1
    
    # Seleccionamos el mejor individuo de la población en la generación actual
    mejorIndividuo = max(poblacion, key=funcionAptitud)
    
    #Verifica si se encontro una solucion optima 
    if funcionAptitud(mejorIndividuo) == N_de_reinas * (N_de_reinas - 1) // 2:
        print("SOLUCION OPTIMA: para N =", N_de_reinas, " es: ", mejorIndividuo)
        print("Generación:", generaciones)
        # Si hemos encontrado una solución, terminamos el programa
        exit()

print("No se encontró una solución después de", max_Generaciones, "generaciones. Intente de nuevo")
