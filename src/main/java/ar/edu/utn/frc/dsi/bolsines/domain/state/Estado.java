package ar.edu.utn.frc.dsi.bolsines.domain.state;

import ar.edu.utn.frc.dsi.bolsines.domain.Bolsin;
import ar.edu.utn.frc.dsi.bolsines.domain.Documentacion;
import ar.edu.utn.frc.dsi.bolsines.domain.Empleado;
import ar.edu.utn.frc.dsi.bolsines.domain.Remito;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "estado")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_estado", discriminatorType = DiscriminatorType.STRING)
public abstract class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String ambito;

    protected Estado() {}

    protected Estado(String nombre, String descripcion, String ambito) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ambito = ambito;
    }

    public boolean esEnviado() { return false; }
    public boolean esRecibido() { return false; }
    public boolean esRecibidoYAceptado() { return false; }
    public boolean esRecibidoYAceptada() { return false; }
    public boolean esNoRecibida() { return false; }
    public boolean esRecibidaYRechazada() { return false; }
    public boolean esParaRedirigir() { return false; }

    public boolean esAmbitoBolsin() { return "Bolsin".equalsIgnoreCase(this.ambito); }
    public boolean esAmbitoRemito() { return "Remito".equalsIgnoreCase(this.ambito); }
    public boolean esAmbitoDocumentacion() { return "Documentacion".equalsIgnoreCase(this.ambito); }

    public void recibirYAceptarBolsin(Bolsin bolsin, LocalDateTime fechaActual, Empleado empleado,
                                      List<Estado> estadosCatalogo, String opcionRecepcion) {
        throw new UnsupportedOperationException("Transición recibirYAceptarBolsin no válida en estado: " + nombre);
    }

    public void recibirYAceptar(Remito remito, Empleado empleado,
                                List<Estado> estadosCatalogo, LocalDateTime fechaActual, String opcionRecepcion) {
        throw new UnsupportedOperationException("Transición recibirYAceptar no válida en estado: " + nombre);
    }

    public void aceptar(Documentacion doc, LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        throw new UnsupportedOperationException("Transición aceptar() no válida en estado: " + nombre);
    }

    public void denegar(Documentacion doc, LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        throw new UnsupportedOperationException("Transición denegar() no válida en estado: " + nombre);
    }

    public void rechazar(Documentacion doc, LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        throw new UnsupportedOperationException("Transición rechazar() no válida en estado: " + nombre);
    }

    public void redirigir(Documentacion doc, LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        throw new UnsupportedOperationException("Transición redirigir() no válida en estado: " + nombre);
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getAmbito() { return ambito; }
    public void setAmbito(String ambito) { this.ambito = ambito; }
}
