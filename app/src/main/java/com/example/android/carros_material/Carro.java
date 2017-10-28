package com.example.android.carros_material;

/**
 * Created by android on 21/10/2017.
 */

public class Carro {

    private String id;
    private String foto;
    private String placa;
    private int marca;
    private int modelo;
    private int color;
    private double precio;

    public Carro() {

    }

    public Carro(String foto, String placa, int marca, int modelo, int color, double precio) {
        this.foto = foto;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.precio = precio;
    }

    public Carro(String id, String foto, String placa, int marca, int modelo, int color, double precio) {
        this.id = id;
        this.foto = foto;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.precio = precio;
    }

    public Carro(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getMarca() {
        return marca;
    }

    public void setMarca(int marca) {
        this.marca = marca;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void guardar() {
        Datos.guardarCarro(this);
    }


    public void eliminar(){
        Datos.eliminarCarro(this);
    }

    public void modificar(){
        Datos.Modificar(this);
    }
}
