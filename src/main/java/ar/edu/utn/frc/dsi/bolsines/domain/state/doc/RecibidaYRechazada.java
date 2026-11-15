package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_RECIBIDA_Y_RECHAZADA")
public class RecibidaYRechazada extends Estado {
    public RecibidaYRechazada() {
        super("RecibidaYRechazada", "Documentación rechazada por no corresponder a la CM destino", "Documentacion");
    }

    @Override
    public boolean esRecibidaYRechazada() {
        return true;
    }
}
