package ar.edu.utn.frc.dsi.bolsines.domain;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bolsin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroPrecinto;
    private LocalDate fecha;
    private Integer numeroBolsin;
    private Double peso;

    @ManyToOne(optional = false)
    private ComisionMedica origen;

    @ManyToOne(optional = false)
    private ComisionMedica destino;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bolsin_id")
    private List<Remito> remitos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bolsin_id")
    private List<CambioEstadoBolsin> cambioEstadoBolsin = new ArrayList<>();

    protected Bolsin() {}
    public Bolsin(String numeroPrecinto, LocalDate fecha, Integer numeroBolsin, Double peso,
                  ComisionMedica origen, ComisionMedica destino, Estado estadoInicial, LocalDateTime fechaInicio) {
        this.numeroPrecinto = numeroPrecinto;
        this.fecha = fecha;
        this.numeroBolsin = numeroBolsin;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
        this.cambioEstadoBolsin.add(new CambioEstadoBolsin(fechaInicio, estadoInicial));
    }

    public boolean esTuCmDestino(ComisionMedica cm) {
        return this.destino != null && cm != null && this.destino.getId().equals(cm.getId());
    }

    public boolean sosEstadoEnviado() {
        return cambioEstadoBolsin.stream()
                .filter(CambioEstadoBolsin::sosActual)
                .anyMatch(CambioEstadoBolsin::sosEnviado);
    }

    public Estado getEstadoActual() {
        return cambioEstadoBolsin.stream()
                .filter(CambioEstadoBolsin::sosActual)
                .map(CambioEstadoBolsin::getEstado)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("El bolsín no posee estado actual"));
    }

    public void recibirYAceptarBolsin(LocalDateTime fechaActual, Empleado empleado,
                                      List<Estado> estadosCatalogo, String opcionRecepcion) {
        getEstadoActual().recibirYAceptarBolsin(this, fechaActual, empleado, estadosCatalogo, opcionRecepcion);
    }

    public void cambiarEstadoBolsin(LocalDateTime fechaActual, Estado nuevoEstado) {
        cambioEstadoBolsin.stream()
                .filter(CambioEstadoBolsin::sosActual)
                .findFirst()
                .ifPresent(actual -> actual.setFechaHoraFin(fechaActual));
        agregarCambioEstado(new CambioEstadoBolsin(fechaActual, nuevoEstado));
    }

    public void agregarCambioEstado(CambioEstadoBolsin cambioEstado) {
        this.cambioEstadoBolsin.add(cambioEstado);
    }

    public Long getId() { return id; }
    public String getNumeroPrecinto() { return numeroPrecinto; }
    public LocalDate getFecha() { return fecha; }
    public Integer getNumeroBolsin() { return numeroBolsin; }
    public Double getPeso() { return peso; }
    public ComisionMedica getCMOrigen() { return origen; }
    public ComisionMedica getCMDestino() { return destino; }
    public List<Remito> getRemitos() { return remitos; }
    public List<CambioEstadoBolsin> getCambioEstadoBolsin() { return cambioEstadoBolsin; }
}
