package ar.edu.utn.frc.dsi.bolsines.domain;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Documentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;
    private String asunto;
    private LocalDate fechaPase;
    private Integer numero;

    @ManyToOne(optional = false)
    private TipoDocumentacion tipoDocumentacion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "documentacion_id")
    private List<CambioEstadoDocumentacion> cambioEstadoDocumentacion = new ArrayList<>();

    protected Documentacion() {}
    public Documentacion(String asunto, LocalDate fechaPase, Integer numero,
                         TipoDocumentacion tipoDocumentacion, Estado estadoInicial, LocalDateTime fechaInicio) {
        this.asunto = asunto;
        this.fechaPase = fechaPase;
        this.numero = numero;
        this.tipoDocumentacion = tipoDocumentacion;
        this.cambioEstadoDocumentacion.add(new CambioEstadoDocumentacion(fechaInicio, estadoInicial));
    }

    public Estado getEstadoActual() {
        return cambioEstadoDocumentacion.stream()
                .filter(CambioEstadoDocumentacion::sosActual)
                .map(CambioEstadoDocumentacion::getEstado)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("La documentación no posee estado actual"));
    }

    public void aceptar(LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        getEstadoActual().aceptar(this, fechaActual, empleado, estadoDestino);
    }

    public void denegar(LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        getEstadoActual().denegar(this, fechaActual, empleado, estadoDestino);
    }

    public void rechazar(LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        getEstadoActual().rechazar(this, fechaActual, empleado, estadoDestino);
    }

    public void redirigir(LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        getEstadoActual().redirigir(this, fechaActual, empleado, estadoDestino);
    }

    public void cambiarEstadoDoc(LocalDateTime fechaActual, Estado nuevoEstado, Empleado empleado) {
        cambioEstadoDocumentacion.stream()
                .filter(CambioEstadoDocumentacion::sosActual)
                .findFirst()
                .ifPresent(actual -> actual.setFechaHoraFin(fechaActual));
        agregarCambioEstado(new CambioEstadoDocumentacion(fechaActual, nuevoEstado, empleado));
    }

    public void agregarCambioEstado(CambioEstadoDocumentacion cambioEstado) {
        this.cambioEstadoDocumentacion.add(cambioEstado);
    }

    public Long getId() { return id; }
    public String getAsunto() { return asunto; }
    public LocalDate getFechaPase() { return fechaPase; }
    public Integer getNumero() { return numero; }
    public TipoDocumentacion getTipoDoc() { return tipoDocumentacion; }
    public List<CambioEstadoDocumentacion> getCambioEstadoDocumentacion() { return cambioEstadoDocumentacion; }
}
