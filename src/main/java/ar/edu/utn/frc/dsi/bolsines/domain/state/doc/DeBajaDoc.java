package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.Documentacion;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_DE_BAJA")
public class DeBajaDoc extends Estado {
    public DeBajaDoc() { super("DeBaja", "Documentación dada de baja", "Documentacion"); }
    public DeBajaDoc(Documentacion documentacion) { super("DeBaja", "Documentación dada de baja", "Documentacion", documentacion); }
}
