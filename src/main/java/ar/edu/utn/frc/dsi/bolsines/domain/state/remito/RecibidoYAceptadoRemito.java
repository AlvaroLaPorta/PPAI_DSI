package ar.edu.utn.frc.dsi.bolsines.domain.state.remito;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REMITO_RECIBIDO_Y_ACEPTADO")
public class RecibidoYAceptadoRemito extends Estado {
    public RecibidoYAceptadoRemito() {
        super("RecibidoYAceptado", "Remito recibido y aceptado en destino", "Remito");
    }

    @Override
    public boolean esRecibidoYAceptado() {
        return true;
    }
}
