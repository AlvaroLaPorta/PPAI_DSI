package ar.edu.utn.frc.dsi.bolsines.domain;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Remito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private Integer numero;

    @ManyToOne(optional = false)
    private Estado estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "remito_id")
    private List<DetalleRemito> detalleRemito = new ArrayList<>();

    protected Remito() {}
    public Remito(LocalDate fecha, Integer numero, Estado estadoInicial) {
        this.fecha = fecha;
        this.numero = numero;
        this.estado = estadoInicial;
    }

    public void recibirYAceptar(Empleado empleado, List<Estado> estadosCatalogo,
                                LocalDateTime fechaActual, String opcionRecepcion) {
        this.estado.recibirYAceptar(this, empleado, estadosCatalogo, fechaActual, opcionRecepcion);
    }

    public Integer getNumero() { return numero; }
    public LocalDate getFecha() { return fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public List<DetalleRemito> getDetalleRemito() { return detalleRemito; }
}
