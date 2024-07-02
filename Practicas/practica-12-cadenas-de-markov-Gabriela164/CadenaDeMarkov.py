import numpy as np

class CadenaDeMarkov:
    """
    Clase que representa una cadena de Markov.
    """
    def __init__(self, estados, prob_inicial, matriz_prob):
        """
        Constructor de la clase CadenaDeMarkov. 
        Parámetros:
        - estados: Lista de estados posibles de la cadena de Markov
        - prob_inicial: Lista de probabilidades iniciales de cada estado
        - matriz_prob: Matriz de probabilidades de transición entre estados
        """
        self.estados = estados
        self.prob_inicial = np.array(prob_inicial).reshape(-1, 1)  # Convertimos la columna en un vector columna
        self.matriz_prob = np.array(matriz_prob) 

    def generar_secuencia_estados(self, n, semilla=None):
        """
        Genera una secuencia de estados basada en el modelo de Markov definido por la matriz de probabilidades y 
        las probabilidades iniciales. 
        Parámetros:
        - n: Número de pasos para la secuencia de estados.
        - semilla: Semilla para la generación de números aleatorios 
        Devuelve:
        - Una lista que representa la secuencia de estados generada.
        """
        np.random.seed(semilla)
        secuencia_estados = [np.random.choice(self.estados, p=self.prob_inicial.ravel())]
        for _ in range(1, n):
            estado_actual = secuencia_estados[-1]
            idx_estado_actual = self.estados.index(estado_actual)
            prox_estado = np.random.choice(self.estados, p=self.matriz_prob[idx_estado_actual])
            secuencia_estados.append(prox_estado)
        return secuencia_estados


    def calcular_probabilidad_cadena(self, secuencia_estados):
        """
        Calcula la probabilidad de una secuencia de estados en el modelo de Markov definido.
        Parámetros:
        - secuencia_estados (list): Lista que representa la secuencia de estados.
        Retorna:
        - La probabilidad de la secuencia de estados proporcionada.
        """
        probabilidad = 1.0
        for i in range(len(secuencia_estados) - 1):
            estado_actual = secuencia_estados[i]
            prox_estado = secuencia_estados[i + 1]
            idx_estado_actual = self.estados.index(estado_actual)
            idx_prox_estado = self.estados.index(prox_estado)
            probabilidad_transicion = self.matriz_prob[idx_estado_actual, idx_prox_estado]
            probabilidad *= probabilidad_transicion
        return probabilidad

    def distribucion_largo_plazo(self):
        # Calcular la distribución límite a largo plazo 
        _, eigenvecs = np.linalg.eig(self.matriz_prob.T)
        eigenvec = eigenvecs[:, np.argmax(np.abs(_))]
        return eigenvec.real / np.sum(eigenvec.real)
