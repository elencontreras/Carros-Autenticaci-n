package com.example.android.carros_material;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void existenciaCorreto() throws Exception{
        Carro c1 = new Carro(1,"wer12",0,0,1,20000000);
        Carro c2 = new Carro(1,"w32",0,0,1,20000000);
        Carro c3 = new Carro(1,"wr42",0,0,1,20000000);
        Carro c4 = new Carro(1,"qw182",0,0,1,20000000);

        Carro car[]={c1,c2,c3,c4};
        ArrayList<Carro> carros = new ArrayList<>(Arrays.asList(car));

        assertEquals(true, Metodos.exitencia_carro(carros, "w32"));
    }

    @Test
    public void existenciaIncorrecto() throws Exception{
        Carro c1 = new Carro(1,"wer12",0,0,1,20000000);
        Carro c2 = new Carro(1,"w32",0,0,1,20000000);
        Carro c3 = new Carro(1,"wr42",0,0,1,20000000);
        Carro c4 = new Carro(1,"qw182",0,0,1,20000000);

        Carro car[]={c1,c2,c3,c4};
        ArrayList<Carro> carros = new ArrayList<>(Arrays.asList(car));

        assertNotEquals(false, Metodos.exitencia_carro(carros, "w32"));
    }
}