package ar.edu.utn.frc.dsi.bolsines.domain;

import jakarta.persistence.*;

@Entity
public class TipoDocumentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    protected TipoDocumentacion() {}
    public TipoDocumentacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }
}
