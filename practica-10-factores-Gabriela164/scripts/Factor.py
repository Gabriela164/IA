#Práctica 10: Factores
#Curso: Inteligencia Artificial 2024-1

import itertools

def tabla_de_valores(l_variables):
    """
    Funcion tabla_de_valores. Genera la tabla de valores posbles a partir de una lista de valores
    posibles de las variables.
    
    Parámetros: l_variables: Lista de variables.
    
    Devuelve: una lista de listas. Representa la tabla de valores posibles con todas las combinaciones
    posibles de los valores de las variables dadas. 
    """
    aux = [x.valores_posibles for x in l_variables]
    return list(map(list, itertools.product(*aux)))

class Variable:
    """
    Clase Variable. Representa una variable. 
    nombre: Nombre de la variable.
    valores_posibles: Lista de valores posibles de la variable.
    """
    def __init__(self, nombre, valores_posibles):
        #Constructor de la clase Variable
        self.nombre = nombre
        self.valores_posibles = [str(x) for x in valores_posibles]

    def __str__(self):
        #Devuelve una cadena con el nombre de la variable y sus valores posibles
        return self.nombre + " : " + str(self.valores_posibles)

class Factor:
    """
    Clase Factor.
    alcance: Lista de variables que intervienen en el factor.
    valores: Lista de valores asociados a cada renglón.
    """
    def __init__(self, alcance, valores, tabla_valores):
        #Constructor de la clase Factor
        self.alcance = alcance
        self.valores = valores
        self.tabla_valores = tabla_valores
        self.nombre_vars = list(map(lambda x: x.nombre, self.alcance))

    def __str__(self):
        """
        Devuelve una representación en cadena del factor.
        """
        s = ''
        s += ((8 +(8*(len(self.nombre_vars)+1))) * '-') + '\n'
        for x in self.nombre_vars:
            s += '| ' + x + '\t'
        s += '|' + '  P(' + ','.join(self.nombre_vars) + ')\n'
        s += ((8 +(8*(len(self.nombre_vars)+1))) * '-') + '\n'
        for i in range(len(self.tabla_valores)):
            s += '| ' + '\t| '.join(self.tabla_valores[i]) + '\t|  ' + str(self.valores[i]) + '\n'
        s += ((8 +(8*(len(self.nombre_vars)+1))) * '-') + '\n'
        return s
    
    def __union_vars_factores(self, f2):
        """
        Une las variables de dos factores y devuelve una lista con las variables combinadas, sin duplicados.

        Parámetros:
        - f2: Factor
        El segundo factor cuyas variables se unirán con las del primer factor.

        Devuelve una lista de variables combinadas sin duplicados.
        """
        l = list(self.alcance)
        for x in f2.alcance:
            if x not in l:
                l.append(x)
        return l

    def __subconjunto_de_vars_y_estado(self, a, b):
        """
        Comprueba si el conjunto de variables y valores 'a' es un subconjunto de 'b'.

        Parámetros:
        - a: Lista de tuplas con variables y valores del primer conjunto.
        - b: Lista de tuplas con variables y valores del segundo conjunto.

        Devuelve: True si 'a' es un subconjunto de 'b' y False en caso contrario.
        """
        s1 = set()
        s2 = set()
        for x, y in a:
            s1.add(x+y)
        for x, y in b:
            s2.add(x+y)
        return s1.issubset(s2)        

    def multiplicacion(self, f2):
        """
        Funcion que multiplica dos factores.
        f2: Factor a multiplicar con el factor actual.
        """
        nuevas_variables = self.__union_vars_factores(f2)
        nueva_nombre_vars = list(map(lambda x: x.nombre, nuevas_variables))
        nueva_tabla_valores = tabla_de_valores(nuevas_variables)
        nuevos_valores = [1 for _ in range(len(nueva_tabla_valores))]

        # Emparejar éste factor con la nueva tabla de valores.
        for valor_f1, estado_vars_f1 in zip(self.valores, self.tabla_valores):
            a = list(zip(self.nombre_vars, estado_vars_f1))
            # Asignar valores nuevos al la nueva tabla de valores.
            for i, estado_vars_fr in enumerate(nueva_tabla_valores):
                b = list(zip(nueva_nombre_vars, estado_vars_fr))
                if self.__subconjunto_de_vars_y_estado(a, b):
                    nuevos_valores[i] = valor_f1

        for valor_f2, estado_vars_f2 in zip(f2.valores, f2.tabla_valores):
            a = list(zip(f2.nombre_vars, estado_vars_f2))
            # Asignar valores nuevos al la nueva tabla de valores.
            for i, estado_vars_fr in enumerate(nueva_tabla_valores):
                b = list(zip(nueva_nombre_vars, estado_vars_fr))
                if self.__subconjunto_de_vars_y_estado(a, b):
                    nuevos_valores[i] *= valor_f2

        return Factor(nuevas_variables, nuevos_valores, nueva_tabla_valores)

    def reduccion(self, variable, valor_r):
        """
        Funcion reducción. Reduce el factor a una variable y un valor.
        variable: Variable a reducir.
        valor_r: Valor de la variable a reducir.
        """
        nuevas_variables = list(filter(lambda x: x.nombre != variable, self.alcance))
        nueva_tabla_valores = tabla_de_valores(nuevas_variables)
        nuevos_valores = []

        pos_variable = self.nombre_vars.index(variable)

        for valor, estado_vars in zip(self.valores, self.tabla_valores):
            # Checar la posición de la variable respecto al estado de las variables
            if estado_vars[pos_variable] == valor_r:
                nuevos_valores.append(valor)

        return Factor(nuevas_variables, nuevos_valores, nueva_tabla_valores)

    def normalizacion(self):
        """
        Función normalización. Normaliza el factor.
        Suma todos los valores y divide cada valor entre la suma.
        """
        total = sum(self.valores)
        nuevos_valores = [val / total for val in self.valores]
        return Factor(self.alcance, nuevos_valores, self.tabla_valores)


    def marginalizacion(self, variable):
        """
        Función marginalización. Marginaliza el factor a una variable.
        variable: Variable a marginalizar.
        """
        valores_asociados_var = []
        nuevos_valores = []

        pos_variable = self.nombre_vars.index(variable)
        # Guardo los posibles valores de la variable dada.
        for estado_vars in self.tabla_valores:
            if estado_vars[pos_variable] not in valores_asociados_var:
                valores_asociados_var.append(estado_vars[pos_variable])

        for val in valores_asociados_var:
            nuevos_valores.append(sum(self.reduccion(variable, val).valores))

        f = self.alcance[pos_variable]
        return Factor([f], nuevos_valores, tabla_de_valores([f]))


if __name__ == '__main__':
    #CASOS DE PRUEBA     
    #Variables
    A = Variable('A', ['0', '1'])
    B = Variable('B', ['0', '1'])
    #Factor A
    factorA = Factor([A], [.3, .7], tabla_de_valores([A]))
    print('Factor A \n', factorA)
    #Factor B
    factorB = Factor([B], [.6, .4], tabla_de_valores([B]))
    print('Factor B \n', factorB)
    
    print('CASO DE PRUEBA 1: Multiplicación')
    f_mult = factorA.multiplicacion(factorB)
    print('Factor AxB \n', f_mult)

    print('CASO DE PRUEBA 2: Reducción')
    f_reduc = f_mult.reduccion('A', '0')
    print('Factor resultante de hacer reducción el factor AxB con (A=0), Factor B \n', f_reduc)
    
    print('CASO DE PRUEBA 3: Normalización')
    print('Factor B normalizado \n', f_reduc.normalizacion())

    print('CASO DE PRUEBA 4: Marginalización')
    print('Factor resultante de hacer marginalización del factor AB con A', '\n', f_mult.marginalizacion('A'))


    
    
