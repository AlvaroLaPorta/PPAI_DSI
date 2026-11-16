package ar.edu.utn.frc.dsi.bolsines.domain.state.remito;

import ar.edu.utn.frc.dsi.bolsines.domain.Remito;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REMITO_CREADO")
public class CreadoRemito extends Estado {
    public CreadoRemito() { super("Creado", "Remito generado", "Remito"); }
    public CreadoRemito(Remito remito) { super("Creado", "Remito generado", "Remito", remito); }
}
