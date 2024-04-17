package com.softsalud.software.persistence.model;

import java.time.LocalDate;

/**
 *
 * @author Gonzalez Ismael
 */
public class Persona {

    private Long dni;
    private String apellido;
    private String nombre;
    private int edad;
    private LocalDate fecha_nac;
    private Long numero_tel;
    private Long numero_tel_opcional;
    private String localidad; //RIO TURBIO - JULIA DEFOUR
    private String direccion;
    private boolean tuvo_covid;
    private boolean tuvo_trasplantes;
    private String factores_riesgo;
    private boolean visible;

    public Persona() {
        this.visible = true;
    }

    public Persona(Long dni, String apellido, String nombre, int edad, LocalDate fecha_nac, Long numero_tel, Long numero_tel_opcional,
            String localidad, String direccion, boolean tuvo_covid, boolean tuvo_trasplantes, String factores_riesgo) {
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.edad = edad;
        this.fecha_nac = fecha_nac;
        this.numero_tel = numero_tel;
        this.numero_tel_opcional = numero_tel_opcional;
        this.localidad = localidad;
        this.direccion = direccion;
        this.tuvo_covid = tuvo_covid;
        this.tuvo_trasplantes = tuvo_trasplantes;
        this.factores_riesgo = factores_riesgo;
    }

    @Override
    public String toString() {
        return "Persona{" + "dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre + ", edad=" + edad + ", fecha_nac=" + fecha_nac + ", numero_tel=" + numero_tel + ", numero_tel_opcional=" + numero_tel_opcional + ", localidad=" + localidad + ", direccion=" + direccion + ", tuvo_covid=" + tuvo_covid + ", tuvo_trasplantes=" + tuvo_trasplantes + ", factores_riesgo=" + factores_riesgo + ", visible=" + visible + '}';
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public Long getNumero_tel() {
        return numero_tel;
    }

    public void setNumero_tel(Long numero_tel) {
        this.numero_tel = numero_tel;
    }

    public Long getNumero_tel_opcional() {
        return numero_tel_opcional;
    }

    public void setNumero_tel_opcional(Long numero_tel_opcional) {
        this.numero_tel_opcional = numero_tel_opcional;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isTuvo_covid() {
        return tuvo_covid;
    }

    public void setTuvo_covid(boolean tuvo_covid) {
        this.tuvo_covid = tuvo_covid;
    }

    public boolean isTuvo_trasplantes() {
        return tuvo_trasplantes;
    }

    public void setTuvo_trasplantes(boolean tuvo_trasplantes) {
        this.tuvo_trasplantes = tuvo_trasplantes;
    }

    public String getFactores_riesgo() {
        return factores_riesgo;
    }

    public void setFactores_riesgo(String factores_riesgo) {
        this.factores_riesgo = factores_riesgo;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
