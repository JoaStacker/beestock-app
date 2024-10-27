package com.api.crud.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_venta")

public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Float horasVendidas;

    @Column
    private Float precioHora;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false) // Clave foránea
    private Venta venta;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_servicio_id", nullable = false) // Clave foránea
    private TipoServicio tipoServicio;

    public DetalleVenta() {
    }

    public DetalleVenta(Float horasVendidas, Float precioHora, Venta venta, TipoServicio tipoServicio) {
        this.horasVendidas = horasVendidas;
        this.precioHora = precioHora;
        this.venta = venta;
        this.tipoServicio = tipoServicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getHorasVendidas() {
        return horasVendidas;
    }

    public void setHorasVendidas(Float horasVendidas) {
        this.horasVendidas = horasVendidas;
    }

    public Float getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(Float precioHora) {
        this.precioHora = precioHora;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
}
