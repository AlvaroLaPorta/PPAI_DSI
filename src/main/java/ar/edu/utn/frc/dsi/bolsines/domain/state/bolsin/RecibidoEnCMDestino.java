package ar.edu.utn.frc.dsi.bolsines.domain.state.bolsin;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOLSIN_RECIBIDO_CM_DESTINO")
public class RecibidoEnCMDestino extends Estado {
    public RecibidoEnCMDestino() {
        super("RecibidoEnCMDestino", "Bolsín recibido en la Comisión Médica de destino", "Bolsin");
    }

    @Override
    public boolean esRecibido() {
        return true;
    }
}
