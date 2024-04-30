package com.softsalud.software.persistence.model;

import java.time.LocalDate;

/**
 *
 * @author Gonzalez Ismael
 */
public class Vacunacion {

    private Long persona_dni;
    private Long vacuna_codigo;
    private String lote_vacuna;
    private int numero_dosis;
    private LocalDate fecha_vacunacion;
    private String lugar_vacunacion;

    private String nombre_completo;
    private String nombre_vacuna;

    public Vacunacion() {
    }

    public Vacunacion(Long persona_dni, Long vacuna_codigo, String lote_vacuna, int numero_dosis, LocalDate fecha_vacunacion, String lugar_vacunacion) {
        this.persona_dni = persona_dni;
        this.vacuna_codigo = vacuna_codigo;
        this.lote_vacuna = lote_vacuna;
        this.numero_dosis = numero_dosis;
        this.fecha_vacunacion = fecha_vacunacion;
        this.lugar_vacunacion = lugar_vacunacion;
    }

    public String[] toStringPersonalizado() {
        String[] vacunacion = {String.valueOf(persona_dni), nombre_vacuna, lote_vacuna,
            String.valueOf(numero_dosis), String.valueOf(fecha_vacunacion), lugar_vacunacion};
        return vacunacion;
    }

    public static String[] toStringTitulos() {
        String[] vacunacion = {"persona_dni", "nombre_vacuna", "lote_vacuna",
            "numero_dosis", "fecha_vacunacion", "lugar_vacunacion"};
        return vacunacion;
    }

    @Override
    public String toString() {
        return "Vacunacion{" + "persona_dni=" + persona_dni + ", vacuna_codigo=" + vacuna_codigo + ", lote_vacuna=" + lote_vacuna + ", numero_dosis=" + numero_dosis + ", fecha_vacunacion=" + fecha_vacunacion + ", lugar_vacunacion=" + lugar_vacunacion + ", nombre_completo=" + nombre_completo + ", nombre_vacuna=" + nombre_vacuna + '}';
    }

    public Long getPersona_dni() {
        return persona_dni;
    }

    public void setPersona_dni(Long persona_dni) {
        this.persona_dni = persona_dni;
    }

    public Long getVacuna_codigo() {
        return vacuna_codigo;
    }

    public void setVacuna_codigo(Long vacuna_codigo) {
        this.vacuna_codigo = vacuna_codigo;
    }

    public String getLote_vacuna() {
        return lote_vacuna;
    }

    public void setLote_vacuna(String lote_vacuna) {
        this.lote_vacuna = lote_vacuna;
    }

    public int getNumero_dosis() {
        return numero_dosis;
    }

    public void setNumero_dosis(int numero_dosis) {
        this.numero_dosis = numero_dosis;
    }

    public LocalDate getFecha_vacunacion() {
        return fecha_vacunacion;
    }

    public void setFecha_vacunacion(LocalDate fecha_vacunacion) {
        this.fecha_vacunacion = fecha_vacunacion;
    }

    public String getLugar_vacunacion() {
        return lugar_vacunacion;
    }

    public void setLugar_vacunacion(String lugar_vacunacion) {
        this.lugar_vacunacion = lugar_vacunacion;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getNombre_vacuna() {
        return nombre_vacuna;
    }

    public void setNombre_vacuna(String nombre_vacuna) {
        this.nombre_vacuna = nombre_vacuna;
    }
}
