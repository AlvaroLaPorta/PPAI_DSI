package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.Documentacion;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_REGISTRADA")
public class Registrada extends Estado {
    public Registrada() { super("Registrada", "Documentación registrada en mesa de entrada", "Documentacion"); }
    public Registrada(Documentacion documentacion) { super("Registrada", "Documentación registrada en mesa de entrada", "Documentacion", documentacion); }
}
