/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ia.ajedrez.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author blackzafiro
 */
public class TableroTest {
    
    public TableroTest() {
    }

    @Test
    public void pruebaConstructorPosición() {
        Tablero t = new Tablero();
        Tablero.Posición p = t.new Posición('c', 2);
        assertEquals(2, p.renglón());
        assertEquals('c', p.columna());
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
    public void pruebaConstructorPosiciónRenIlegal() {
        Tablero t = new Tablero();
        Tablero.Posición p = t.new Posición('c', -2);
    }
 
    @Test(expected=IndexOutOfBoundsException.class)
    public void pruebaConstructorPosiciónColIlegal() {
        Tablero t = new Tablero();
        Tablero.Posición p = t.new Posición('z', 2);
    }
}
