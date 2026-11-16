package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.Documentacion;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_NO_RECIBIDA")
public class NoRecibida extends Estado {
    public NoRecibida() { super("NoRecibida", "Documentación faltante o incompleta al abrir el bolsín", "Documentacion"); }
    public NoRecibida(Documentacion documentacion) { super("NoRecibida", "Documentación faltante o incompleta al abrir el bolsín", "Documentacion", documentacion); }
    @Override
    public boolean esNoRecibida() { return true; }
}
