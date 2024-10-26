package com.api.crud.services.models.response.CondicionTributaria;

public class CondicionTributariaResponseDTO {
    private Long id;
    private String tipo;

    public CondicionTributariaResponseDTO(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
