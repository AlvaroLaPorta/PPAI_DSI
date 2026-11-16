package ar.edu.utn.frc.dsi.bolsines.domain.state.doc;

import ar.edu.utn.frc.dsi.bolsines.domain.Documentacion;
import ar.edu.utn.frc.dsi.bolsines.domain.Empleado;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("DOC_EN_BOLSIN_ENVIADO")
public class EnBolsinEnviadoDoc extends Estado {

    public EnBolsinEnviadoDoc() {
        super("EnBolsinEnviado", "Documentación en tránsito dentro de bolsín enviado", "Documentacion");
    }

    @Override
    public void aceptar(Documentacion doc, LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        doc.cambiarEstadoDoc(fechaActual, estadoDestino, empleado);
    }

    @Override
    public void denegar(Documentacion doc, LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        doc.cambiarEstadoDoc(fechaActual, estadoDestino, empleado);
    }

    @Override
    public void rechazar(Documentacion doc, LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        doc.cambiarEstadoDoc(fechaActual, estadoDestino, empleado);
    }

    @Override
    public void redirigir(Documentacion doc, LocalDateTime fechaActual, Empleado empleado, Estado estadoDestino) {
        doc.cambiarEstadoDoc(fechaActual, estadoDestino, empleado);
    }
}
