package org.asgas;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class CalculadoraTest {
    Calculadora calculadora;

    @BeforeEach
    public void init() {
        calculadora = new Calculadora();
    }

    @Test
    @DisplayName("Prueba de suma simple")
    public void probarSuma() {
        assertEquals(25.0, calculadora.sumar(5.0, 20.0));
    }

    @Test
    @Disabled("La prueba de restas fue deshabilitada porque así lo quiso el jefe.")
    public void probarResta() {
        assertEquals(30.0, calculadora.restar(50.0, 20.0));
    }

    @Test
    public void probarMultiplicacion() {
        assertEquals(25.0, calculadora.multiplicar(5.0, 5.0));
    }

    @Test
    public void probarDivision() {
        assertThrows(ArithmeticException.class, () -> {
            var division = 100/0;
        });
    }

    @Test
    @DisplayName("Prueba de la tabla del 5:")
    public void probarTabla5() {
        int[] numbers = {1, 2, 3, 4, 5};
        assertAll("Tabla del 5",
                () -> {assertEquals(5, calculadora.multiplicar(5, 1));},
                () -> {assertEquals(10, calculadora.multiplicar(5, 2));},
                () -> {assertEquals(15, calculadora.multiplicar(5, 3));},
                () -> {assertEquals(20, calculadora.multiplicar(5, 4));},
                () -> {assertEquals(25, calculadora.multiplicar(5, 5));});
    }
}
