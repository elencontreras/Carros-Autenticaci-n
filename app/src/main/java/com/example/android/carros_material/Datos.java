package com.example.android.carros_material;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by android on 21/10/2017.
 */

public class Datos {

    private static String db = "Carros";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private static ArrayList<Carro> carros = new ArrayList();


    public static void guardarCarro(Carro c){
        c.setId(databaseReference.push().getKey());
        databaseReference.child(db).child(c.getId()).setValue(c);
    }

    public static void setCarros(ArrayList<Carro> car){
        carros=car;
    }

    public static ArrayList<Carro> obtenerCarro(){
        return carros;
    }

    public static void eliminarCarro(Carro c){
        databaseReference.child(db).child(c.getId()).removeValue();

    }

    public static String getId(){
       return databaseReference.push().getKey();
    }


    public static int ExisteCarro(String placa){
        for (int i = 0; i < carros.size(); i++) {
            if (carros.get(i).getPlaca().equals(placa)){
                return i;
            }
        }
        return -1;

    }

    public static void Modificar(Carro c) {
        databaseReference.child(db).child(c.getId()).setValue(c);
    }
}
