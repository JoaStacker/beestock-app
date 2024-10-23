package com.api.crud.services.models.response.direccion;

public class DireccionResponseDTO {
    private Long id;
    private String calle;
    private String numero;
    private String piso;

    public DireccionResponseDTO(Long id, String calle, String numero, String piso) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }
}
