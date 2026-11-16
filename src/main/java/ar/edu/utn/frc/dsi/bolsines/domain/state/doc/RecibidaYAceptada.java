package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.Documentacion;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_RECIBIDA_Y_ACEPTADA")
public class RecibidaYAceptada extends Estado {
    public RecibidaYAceptada() { super("RecibidaYAceptada", "Documentación completa recibida y aceptada en destino", "Documentacion"); }
    public RecibidaYAceptada(Documentacion documentacion) { super("RecibidaYAceptada", "Documentación completa recibida y aceptada en destino", "Documentacion", documentacion); }
    @Override
    public boolean esRecibidoYAceptada() { return true; }
}
