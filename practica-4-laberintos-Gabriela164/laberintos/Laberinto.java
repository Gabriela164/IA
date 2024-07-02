/*
 * PRÁCTICA 4: Recursión y Retractación (Backtracking)
 * Alumn@: Lopez Diego Gabriela
 * Num. De Cuenta: 318243485
 * Curso: Inteligencia Artificial 2024-1
 * Fecha: 19/sep/23
 */

package laberintos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import processing.core.PApplet;

public class Laberinto extends PApplet {
    int alto = 9;
    int ancho = 9;
    int celda = 50;
    ModeloLaberinto modelo;
    //Generamos las coordenadas de la celda inicial de manera aleatoria
    int inicioX = (int) random(ancho);
    int inicioY = (int) random(alto);

    @Override
    public void setup() {
        frameRate(60);
        background(50);
        // Creamos el laberinto(tablero) con las dimensiones y tamaño de celda especificados
        modelo = new ModeloLaberinto(ancho, alto, celda);
        // Construimos el recorrido del laberinto con las coordenadas de la celda inicial
        modelo.generarLaberinto(inicioX, inicioY);
    }

    @Override
    public void settings() {
        size(ancho * celda, alto * celda);
    }

    @Override
    public void draw() {
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                //Recorremos el tablero de celdas y pintamos cada celda de acuerdo a su estado
                Celda celdaActual = modelo.mundo[i][j];
                if (celdaActual.esVisitado && celdaActual.esActual) {
                    fill(255, 0, 0); // Color rojo para celdas que son visitadas y actuales al mismo tiempo
                } else if (celdaActual.esVisitado) {
                    fill(0, 0, 255); // Color azul para celdas que ya han sido visitadas
                } else {
                    fill(204, 204, 204); // Color gris para celdas no visitadas
                }
                stroke(25, 25, 25); //Dibuja lineas de color gris
                rect(j * modelo.tamanio, i * modelo.tamanio, modelo.tamanio, modelo.tamanio);
                strokeWeight(3); //Cambiamos de tamaño el grosor de las lineas

                if (!celdaActual.pared_1) {
                    stroke(0, 0, 255);
                    line(j * modelo.tamanio, i * modelo.tamanio, (j + 1) * modelo.tamanio, i * modelo.tamanio);
                }
                if (!celdaActual.pared_2) {
                    stroke(0, 0, 255);
                    line((j * modelo.tamanio) + modelo.tamanio, i * modelo.tamanio, (j + 1) * modelo.tamanio,
                            (i + 1) * modelo.tamanio);
                }
                if (!celdaActual.pared_3) {
                    stroke(0, 0, 255);
                    line(j * modelo.tamanio, (i * modelo.tamanio) + modelo.tamanio, (j + 1) * modelo.tamanio,
                            (i + 1) * modelo.tamanio);
                }
                if (!celdaActual.pared_4) {
                    stroke(0, 0, 255);
                    line(j * modelo.tamanio, i * modelo.tamanio, j * modelo.tamanio, (i + 1) * modelo.tamanio);
                }
            }
        }
    }

    class Celda {
        int celdaX;
        int celdaY;
        boolean pared_1;
        boolean pared_2;
        boolean pared_3;
        boolean pared_4;
        boolean esVisitado; // Indica si la celda ha sido visitada o no
        boolean esActual;

        Celda(int celdaX, int celdaY, boolean esVisitado) {
            this.celdaX = celdaX;
            this.celdaY = celdaY;
            this.esVisitado = esVisitado;
            this.pared_1 = true;
            this.pared_2 = true;
            this.pared_3 = true;
            this.pared_4 = true;
            this.esActual = false;
        }
    }

    class ModeloLaberinto {
        int ancho, alto;
        int tamanio;
        Celda[][] mundo; // Tablero de celdas
        int direccion;   // Dirección: 1 = arriba, 2 = derecha, 3 = abajo, 4 = izquierda

        ModeloLaberinto(int ancho, int alto, int tamanio) {
            this.ancho = ancho;
            this.alto = alto;
            this.tamanio = tamanio;
            mundo = new Celda[alto][ancho];

            for (int i = 0; i < alto; i++) {
                for (int j = 0; j < ancho; j++) {
                    mundo[i][j] = new Celda(j, i, false);
                }
            }
        }

        /*
         * Metodo que contruye el recorrido del laberinto
         * @param x posicion aleatoria en el eje x de la celda inicial
         * @param y posicion aleatoria en el eje y de la celda inicial
         */
        void generarLaberinto(int x, int y) {
          //Creamos una pila para almacenar las celdas visitadas
          Stack<Celda> pila = new Stack<>();
          Celda celdaActual = mundo[y][x];  //Creamos la celda inicial
          celdaActual.esVisitado = true;    //Marcamos la celda inicial como visitada
          pila.push(celdaActual);           //Agregamos la celda inicial a la pila
      
          while (!pila.isEmpty()) {
              celdaActual = pila.peek(); //Celda actual es la celda que se encuentra en la cima de la pila
              celdaActual.esActual = true; //Marcamos la celda actual como actual
              //Direcciones: 1 = arriba, 2 = derecha, 3 = abajo, 4 = izquierda
              ArrayList<Integer> direcciones = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
              Collections.shuffle(direcciones);
      
              boolean celdaDisponible = false;

              // Verificamos una por una las celdas (arriba,der,abajo,izq) en orden aleatorio (se ha desordenado la lista direcciones)
              // hasta encontrar una celda disponible para moverse. Si se ha encontrado una celda disponible, se rompe el ciclo for. 
              // En caso contrario, retrocedemos y sacamos la celda anterior de la pila y la marcamos como actual.
              for (int direccion : direcciones) {
                  int xSiguiente = celdaActual.celdaX;
                  int ySiguiente = celdaActual.celdaY;
      
                  if (direccion == 1) {
                    //La celda arriba de la actual, es la siguiente
                      ySiguiente--;
                  } else if (direccion == 2) {
                    //La celda de la derecha de la actual, es la siguiente
                      xSiguiente++;
                  } else if (direccion == 3) {
                    //La celda de abajo de la actual, es la siguiente
                      ySiguiente++;
                  } else if (direccion == 4) {
                    //La celda de la izquierda de la actual, es la siguiente
                      xSiguiente--;
                  }
      
                  // Verificamos que xSiguiente y ySiguiente estén dentro de los límites del laberinto(tablero)
                  if (xSiguiente >= 0 && xSiguiente < ancho && ySiguiente >= 0 && ySiguiente < alto) {
                    //Verificamos si la celda no ha sido visitada
                      if (!mundo[ySiguiente][xSiguiente].esVisitado) {
                          celdaDisponible = true;
                          //Borramos las paredes correspondientes
                          //Recordemos que pared_1 es la pared de arriba
                          //pared_2 es la pared de la derecha
                          //pared_3 es la pared de abajo
                          //pared_4 es la pared de la izquierda
                          if (direccion == 1) { //Si la dirección es arriba
                              celdaActual.pared_1 = false; 
                              mundo[ySiguiente][xSiguiente].pared_3 = false; 
                          } else if (direccion == 2) { //Si la dirección es derecha
                              celdaActual.pared_2 = false;
                              mundo[ySiguiente][xSiguiente].pared_4 = false;
                          } else if (direccion == 3) { //Si la dirección es abajo
                              celdaActual.pared_3 = false;
                              mundo[ySiguiente][xSiguiente].pared_1 = false;
                          } else if (direccion == 4) { //Si la dirección es izquierda
                              celdaActual.pared_4 = false;
                              mundo[ySiguiente][xSiguiente].pared_2 = false;
                          }
                          //Marcamos la celda actual como no actual, se convierte en solo celda visitada
                          celdaActual.esActual = false;
                          //Marcamos la celda siguiente como visitada, actual y la agregamos a la pila
                          mundo[ySiguiente][xSiguiente].esVisitado = true;
                          mundo[ySiguiente][xSiguiente].esActual = true;
                          pila.push(mundo[ySiguiente][xSiguiente]);
                          break;
                      }
                  }
              }
              //Si no hay celdas disponibles, sacamos la celda anterior de la pila y la marcamos como actual
              if (!celdaDisponible) {
                celdaActual.esActual = false;
                pila.pop();
              }
          }
          //Marcamos como actual a la ultima celda visitada en el laberinto
          // y poder pintarla de rojo (ya que se ha finalizado de recorrer el laberinto)
          celdaActual.esActual = true;
        }
    }

    public static void main(String[] args) {
        PApplet.main(new String[] { "laberintos.Laberinto" });
    }
}
