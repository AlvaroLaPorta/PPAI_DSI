package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_EN_BOLSIN_SALIENTE")
public class EnBolsinSalienteDoc extends Estado {
    public EnBolsinSalienteDoc() {
        super("EnBolsinSaliente", "Documentación dentro de bolsín saliente", "Documentacion");
    }
}
