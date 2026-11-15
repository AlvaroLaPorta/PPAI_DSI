package ar.edu.utn.frc.dsi.bolsines.domain.state.bolsin;

import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOLSIN_CREADO")
public class CreadoBolsin extends Estado {
    public CreadoBolsin() {
        super("Creado", "Bolsín creado en origen", "Bolsin");
    }
}
