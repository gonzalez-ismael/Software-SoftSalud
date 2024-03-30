package com.softsalud.software.persistence.model;

/**
 *
 * @author Gonzalez Ismael
 */
public class Direccion {

    private Long id;
    private String barrio;
    private String calle;
    private Integer numero;

    public Direccion() {
    }

    public Direccion(String barrio, String calle, Integer numero) {
        this.barrio = barrio;
        this.calle = calle;
        this.numero = numero;
    }

    public Direccion(Long id, String barrio, String calle, Integer numero) {
        this.id = id;
        this.barrio = barrio;
        this.calle = calle;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return barrio + " " + calle + " " + numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
