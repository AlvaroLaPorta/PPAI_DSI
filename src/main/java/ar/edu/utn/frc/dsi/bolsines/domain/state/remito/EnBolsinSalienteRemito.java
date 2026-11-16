package ar.edu.utn.frc.dsi.bolsines.domain.state.remito;

import ar.edu.utn.frc.dsi.bolsines.domain.Remito;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REMITO_EN_BOLSIN_SALIENTE")
public class EnBolsinSalienteRemito extends Estado {
    public EnBolsinSalienteRemito() { super("EnBolsinSaliente", "Remito asignado a bolsín saliente", "Remito"); }
    public EnBolsinSalienteRemito(Remito remito) { super("EnBolsinSaliente", "Remito asignado a bolsín saliente", "Remito", remito); }
}
