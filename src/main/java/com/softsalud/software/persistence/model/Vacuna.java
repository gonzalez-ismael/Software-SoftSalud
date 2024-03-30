package com.softsalud.software.persistence.model;

/**
 *
 * @author Gonzalez Ismael
 */
public class Vacuna {
    private Long codigo;
    private String nombreVacuna;

    public Vacuna() {
    }
    
    public Vacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }
}
