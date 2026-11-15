package ar.edu.utn.frc.dsi.bolsines.domain.state.bolsin;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOLSIN_DE_BAJA")
public class DeBajaBolsin extends Estado {
    public DeBajaBolsin() {
        super("DeBaja", "Bolsín dado de baja", "Bolsin");
    }
}
