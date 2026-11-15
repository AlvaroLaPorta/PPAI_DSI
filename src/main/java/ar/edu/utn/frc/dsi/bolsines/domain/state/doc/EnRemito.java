package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_EN_REMITO")
public class EnRemito extends Estado {
    public EnRemito() {
        super("EnRemito", "Documentación incluida en un remito", "Documentacion");
    }
}
