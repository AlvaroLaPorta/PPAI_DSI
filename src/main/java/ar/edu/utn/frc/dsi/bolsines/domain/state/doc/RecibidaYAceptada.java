package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_RECIBIDA_Y_ACEPTADA")
public class RecibidaYAceptada extends Estado {
    public RecibidaYAceptada() {
        super("RecibidaYAceptada", "Documentación completa recibida y aceptada en destino", "Documentacion");
    }

    @Override
    public boolean esRecibidoYAceptada() {
        return true;
    }
}
