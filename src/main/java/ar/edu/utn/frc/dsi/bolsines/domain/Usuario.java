package ar.edu.utn.frc.dsi.bolsines.domain;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String contrasena;

    protected Usuario() {}
    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
}
