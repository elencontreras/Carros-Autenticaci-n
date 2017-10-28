package com.example.android.carros_material;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by android on 21/10/2017.
 */

public class Metodos {
    public static int fotoAleatoria(ArrayList<Integer> fotos) {
        int fotoSeleccionada;
        Random r = new Random();
        fotoSeleccionada = r.nextInt(fotos.size());
        return fotos.get(fotoSeleccionada);
    }

    public static boolean exitencia_carro(ArrayList<Carro> carros, String placa) {
        for (int i = 0; i < carros.size(); i++) {
            if (carros.get(i).getPlaca().equals(placa)) {
                return true;
            }
        }
        return false;
    }


}
