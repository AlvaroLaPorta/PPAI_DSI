package ar.edu.utn.frc.dsi.bolsines.domain.state.remito;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REMITO_CANCELADO")
public class CanceladoRemito extends Estado {
    public CanceladoRemito() {
        super("Cancelado", "Remito cancelado", "Remito");
    }
}
