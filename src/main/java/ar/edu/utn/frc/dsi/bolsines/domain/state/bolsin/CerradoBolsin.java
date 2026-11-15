package ar.edu.utn.frc.dsi.bolsines.domain.state.bolsin;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOLSIN_CERRADO")
public class CerradoBolsin extends Estado {
    public CerradoBolsin() {
        super("Cerrado", "Bolsín precintado listo para envío", "Bolsin");
    }
}
