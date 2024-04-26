package com.softsalud.software.persistence.model;

/**
 *
 * @author Gonzalez Ismael
 */
public class Vacuna {
    private Long codigo;
    private String nombre_vacuna;

    public Vacuna() {
    }
    
    public Vacuna(String nombreVacuna) {
        this.nombre_vacuna = nombreVacuna;
    }
    
    public String[] toStringPersonalizado() {
        String[] vacuna = {String.valueOf(codigo),nombre_vacuna};
        return vacuna;
    }

    public String[] toStringTitulos() {
        String[] vacuna = {"codig","nombre_vacuna"};
        return vacuna;
    }

    @Override
    public String toString() {
        return "Vacuna{" + "codigo=" + codigo + ", nombre_vacuna=" + nombre_vacuna + '}';
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre_vacuna() {
        return nombre_vacuna;
    }

    public void setNombre_vacuna(String nombre_vacuna) {
        this.nombre_vacuna = nombre_vacuna;
    }
}
