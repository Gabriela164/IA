import numpy as np

class LogisticRegression:
    #Inicializar el modelo. 
    #learning_rate: tasa de aprendizaje = 0.1
    #max_iterations: número máximo de iteraciones = 100
    def __init__(self, learning_rate=0.1, max_iterations=100):
        self.learning_rate = learning_rate
        self.max_iterations = max_iterations

    # Función sigmoide 1 / (1 + e^(-x))
    def _sigmoide(self, x):
        return 1 / (1 + np.exp(-x))

    def fit(self, X, Y):
        self.w, self.theta = np.random.uniform(-0.5, 0.5, size=(X.shape[1], 1)), np.random.uniform(-0.5, 0.5)
        for t in range(self.max_iterations):
            for x, y in zip(X, Y):
                x = x.reshape(-1, 1)
                f_x = self._sigmoide(np.dot(x.T, self.w) + self.theta)
                gradient_w = self.learning_rate * (f_x - y) * x
                gradient_theta = self.learning_rate * (f_x - y)
                self.w -= gradient_w
                self.theta -= gradient_theta

    # La función predict_proba toma un vector o una matriz de vectores
    # de entrada x y calcula las probabilidades de pertenencia a la clase 
    # positiva (clase 1) para cada vector de entrada
    def predict_proba(self, x):
     if len(x.shape) == 1:
        # Si se pasa un solo vector, convertirlo en una matriz unidimensional
        x = x.reshape(1, -1)

    probabilities = []
    for x_i in x:
        x_i = x_i.reshape(-1, 1)
        f_x = self._sigmoid(np.dot(x_i.T, self.w) + self.theta)
        probabilities.append(f_x[0, 0])
    return np.array(probabilities)

    # La funcion predict realiza la clasificación binaria. Utiliza un umbral por defecto de 0.5
    def predict(self, X, threshold=0.5):
        probabilities = self.predict_proba(X)
        return (probabilities >= threshold).astype(int)

# Generar datos de entrenamiento con ruido
np.random.seed(0)
X_class0 = np.random.normal(0, 1, size=(150, 2))
X_class1 = np.random.normal(1, 1, size=(50, 2))
X = np.vstack((X_class0, X_class1))
Y = np.concatenate((np.zeros(150), np.ones(50)))

# Creamos y entrenamos el modelo de regresión logística
model = LogisticRegression()
model.fit(X, Y)

# Ejemplo de predicción
new_data = np.array([[0.5, 0.5], [2.0, 1.0]])
predictions = model.predict(new_data)
print("Predicciones:", predictions)
