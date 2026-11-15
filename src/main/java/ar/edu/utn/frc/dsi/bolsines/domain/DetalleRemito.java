package ar.edu.utn.frc.dsi.bolsines.domain;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class DetalleRemito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String areaCMCDestino;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Documentacion documentacion;

    protected DetalleRemito() {}
    public DetalleRemito(String areaCMCDestino, Documentacion documentacion) {
        this.areaCMCDestino = areaCMCDestino;
        this.documentacion = documentacion;
    }

    public void aceptar(LocalDateTime fechaActual, Empleado empleado, List<Estado> estadosCatalogo, String opcionRecepcion) {
        switch (opcionRecepcion) {
            case "ACEPTAR" -> {
                Estado est = estadosCatalogo.stream()
                        .filter(e -> e.esAmbitoDocumentacion() && e.esRecibidoYAceptada())
                        .findFirst().orElseThrow();
                documentacion.aceptar(fechaActual, empleado, est);
            }
            case "DENEGAR" -> {
                Estado est = estadosCatalogo.stream()
                        .filter(e -> e.esAmbitoDocumentacion() && e.esNoRecibida())
                        .findFirst().orElseThrow();
                documentacion.denegar(fechaActual, empleado, est);
            }
            case "RECHAZAR" -> {
                Estado est = estadosCatalogo.stream()
                        .filter(e -> e.esAmbitoDocumentacion() && e.esRecibidaYRechazada())
                        .findFirst().orElseThrow();
                documentacion.rechazar(fechaActual, empleado, est);
            }
            case "REDIRIGIR" -> {
                Estado est = estadosCatalogo.stream()
                        .filter(e -> e.esAmbitoDocumentacion() && e.esParaRedirigir())
                        .findFirst().orElseThrow();
                documentacion.redirigir(fechaActual, empleado, est);
            }
            default -> throw new IllegalArgumentException("Opción de recepción desconocida: " + opcionRecepcion);
        }
    }

    public String getAreaCMCDestino() { return areaCMCDestino; }
    public Documentacion getDocumentacion() { return documentacion; }
}
