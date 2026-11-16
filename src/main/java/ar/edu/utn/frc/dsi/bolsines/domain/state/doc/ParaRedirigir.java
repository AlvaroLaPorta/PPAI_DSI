package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.Documentacion;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DOC_PARA_REDIRIGIR")
public class ParaRedirigir extends Estado {
    public ParaRedirigir() { super("ParaRedirigir", "Documentación recibida en CMC para redistribución interna", "Documentacion"); }
    public ParaRedirigir(Documentacion documentacion) { super("ParaRedirigir", "Documentación recibida en CMC para redistribución interna", "Documentacion", documentacion); }
    @Override
    public boolean esParaRedirigir() { return true; }
}
