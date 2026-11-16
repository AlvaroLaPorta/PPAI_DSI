package ar.edu.utn.frc.dsi.bolsines.domain.state.remito;

import ar.edu.utn.frc.dsi.bolsines.domain.*;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("REMITO_EN_BOLSIN_ENVIADO")
public class EnBolsinEnviadoRemito extends Estado {

    public EnBolsinEnviadoRemito() {
        super("EnBolsinEnviado", "Remito viajando dentro de un bolsín enviado", "Remito");
    }

    public EnBolsinEnviadoRemito(Remito remito) {
        super("EnBolsinEnviado", "Remito viajando dentro de un bolsín enviado", "Remito", remito);
    }

    // Secuencia 1.3.1.7.1 -> 1.3.1.7.1.5 sobre la referencia inversa this.remito
    @Override
    public void recibirYAceptar(LocalDateTime fechaActual, Empleado nombre, String opcionRecepcion) {
        // 1.3.1.7.1.1 -> 1.3.1.7.1.2: crearEstadoRem(): RecibidoYAceptado
        RecibidoYAceptadoRemito nuevoEstadoRem = crearEstadoRem();

        // 1.3.1.7.1.3: cambiarEstadoDocumentacion(fechaActual: DateTime)
        cambiarEstadoDocumentacion(fechaActual, nombre, opcionRecepcion);

        // 1.3.1.7.1.5: setEstado(estado: RecibidoYAceptado)
        this.remito.setEstado(nuevoEstadoRem);
    }

    public RecibidoYAceptadoRemito crearEstadoRem() {
        return new RecibidoYAceptadoRemito(this.remito);
    }

    public void cambiarEstadoDocumentacion(LocalDateTime fechaActual, Empleado nombre, String opcionRecepcion) {
        for (DetalleRemito detalle : this.remito.getDetalleRemito()) {
            detalle.procesarRecepcion(fechaActual, nombre, opcionRecepcion);
        }
    }
}
