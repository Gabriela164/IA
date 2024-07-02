from CadenaDeMarkov import *

#Ejemplo del soldado. Modelamos el comportamiento de un soldado en un videojuego en acción.
#Los estados son: caminando, de pie o dormido.  
estados = ["Caminando", "Dormido", "De pie"]
prob_inicial = [0.2, 0.2, 0.6]  
matriz_prob = [[0.27,0.03,0.7], [0.3,0.2,0.5],[0.6,0.2,0.2]]

cadena_markov = CadenaDeMarkov(estados, prob_inicial, matriz_prob)

secuencia_generada = cadena_markov.generar_secuencia_estados(10)
print("Secuencia generada:", secuencia_generada)

probabilidad_secuencia = cadena_markov.calcular_probabilidad_cadena(secuencia_generada)
print("Probabilidad de la secuencia generada:", probabilidad_secuencia)

distribucion_largo_plazo = cadena_markov.distribucion_largo_plazo()
print("Distribución a largo plazo de los estados:", distribucion_largo_plazo)
