package ar.edu.utn.frc.dsi.bolsines.domain.state.remito;

import ar.edu.utn.frc.dsi.bolsines.domain.*;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("REMITO_EN_BOLSIN_ENVIADO")
public class EnBolsinEnviadoRemito extends Estado {

    public EnBolsinEnviadoRemito() {
        super("EnBolsinEnviado", "Remito viajando dentro de un bolsín enviado", "Remito");
    }

    @Override
    public void recibirYAceptar(Remito remito, Empleado empleado,
                                List<Estado> estadosCatalogo, LocalDateTime fechaActual, String opcionRecepcion) {
        Estado estadoRemitoAceptado = estadosCatalogo.stream()
                .filter(e -> e.esAmbitoRemito() && e.esRecibidoYAceptado())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró el estado RecibidoYAceptado para Remito"));

        remito.setEstado(estadoRemitoAceptado);

        for (DetalleRemito detalle : remito.getDetalleRemito()) {
            detalle.aceptar(fechaActual, empleado, estadosCatalogo, opcionRecepcion);
        }
    }
}
