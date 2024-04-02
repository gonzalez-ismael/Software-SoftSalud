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
