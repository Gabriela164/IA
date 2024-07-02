package recocido;

import java.util.Arrays;
import java.util.Random;

public class SolucionAgente extends Solucion {

    private int[] ruta; //Representa la ruta que hara el agente viajero a traves de las ciudades 
    private DatosPAV datos; //Datos de las ciudades que seran recolectados del archivo .tsp

    /*
     * Constructor que crea una solucion aleatoria a partir de los datos de las ciudades
     * @param datos: datos de las ciudades
     */
    public SolucionAgente(DatosPAV datos) {
        this.datos = datos;
        int numCiudades = datos.numCiudades(); //Obtenemos el numero de ciudades
        ruta = new int[numCiudades]; 

        //Inicializamos la ruta 
        for (int i = 0; i < numCiudades; i++) {
            ruta[i] = i + 1; //Enumeramos las ciudades de 1 a n
        }
        shuffleArray(ruta); //Mezclamos la ruta
        evaluar(); //Calculamos el valor de la ruta
    }

    
    /*
     * Metodo privado para mezclar aleatoriamente un arreglo de enteros. 
     * Se utiliza para generar una ruta aleatoria.
     */
    private void shuffleArray(int[] array){
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }


    /*
     * Crea y devuelve una nueva solucion generando una ruta diferente a partir
     * de la ruta actual al intercambiar de forma aleatoria dos ciudades
     * @return nueva solucion
     */
    @Override
    public Solucion siguienteSolucion() {
        SolucionAgente nuevaSolucion = new SolucionAgente(datos);
        int[] nuevaRuta = nuevaSolucion.ruta;

        //Se eligen dos posiciones aleatorias de la ruta y se intercambian
        int pos1 = new Random().nextInt(nuevaRuta.length);
        int pos2 = new Random().nextInt(nuevaRuta.length);
        int temp = nuevaRuta[pos1];
        nuevaRuta[pos1] = nuevaRuta[pos2];
        nuevaRuta[pos2] = temp;

        nuevaSolucion.ruta = nuevaRuta;  //Asignamos la nueva ruta a la nueva solucion
        nuevaSolucion.evaluar();         //Calculamos el valor de la nueva solucion
        return nuevaSolucion;            //Regresamos la nueva solucion con la ruta modificada

    }


    /**
	* Asigna una calificación (valor) a la solución que invoca el método
	* @return evaluación de la solución
	*/
    @Override
    public float evaluar() {
        valor = 0;
        // Calcula la distancia entre cada par de ciudades
        for (int i = 0; i < ruta.length - 1; i++) {
            int ciudad1 = ruta[i];
            int ciudad2 = ruta[i + 1];

            double[] coordenadas1 = datos.coordenadas(ciudad1);
            double[] coordenadas2 = datos.coordenadas(ciudad2);
            double distancia = calcularDistancia(coordenadas1, coordenadas2);
            //Agregamos la distancia entre las dos ciudades a la calificacion
            valor += distancia;
        }
        
        int primeraCiudad = ruta[0];
        int ultimaCiudad = ruta[ruta.length - 1];
        double[] coordenadasPrimera = datos.coordenadas(primeraCiudad);
        double[] coordenadasUltima = datos.coordenadas(ultimaCiudad);
        double distanciaRegreso = calcularDistancia(coordenadasPrimera, coordenadasUltima);

        //Agregamos la distancia de regreso a la ciudad inicial
        valor += distanciaRegreso;

        return valor;
    }


    /*
     * Metodo privado que calcula la distancia euclidiana entre dos coordenadas 
     * de dos cuidades. 
     * @param coordenadas1: coordenadas de la primera ciudad
     * @param coordenadas2: coordenadas de la segunda ciudad
     * @return distancia entre las dos ciudades
     */
    private double calcularDistancia(double[] coordenadas1, double[] coordenadas2){
        double x1 = coordenadas1[0];
        double y1 = coordenadas1[1];
        double x2 = coordenadas2[0];
        double y2 = coordenadas2[1];

        //Calcula la distancia euclidiana entre dos puntos
        //distancia = raiz cuadrada de ((x1 - x2)^2 + (y1 - y2)^2)
        double distancia = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        return distancia;
    }


    /*
     * Metodo que devuelve una representacion en cadena de la solucion actual
     * @return representacion en cadena de la solucion
     */
    @Override
    public String toString() {
        return "Ruta: " + Arrays.toString(ruta) + "\nValor: " + valor;
    }
    
}
