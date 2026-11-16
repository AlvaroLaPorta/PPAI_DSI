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
                                List<Estado> estadosCatalogo, LocalDateTime fechaActual, java.util.Map<Long, OpcionRecepcion> opciones) {
        boolean hayFaltantes = remito.getDetalleRemito().stream()
                .anyMatch(d -> opciones.get(d.getDocumentacion().getId()) == OpcionRecepcion.DENEGAR);
        Estado estadoDestino = estadosCatalogo.stream()
                .filter(e -> e.esAmbitoRemito() && (hayFaltantes
                        ? e.esRecibidoYAceptadoParcial() : e.esRecibidoYAceptado()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró el estado destino del remito"));
        for (DetalleRemito detalle : remito.getDetalleRemito()) {
            detalle.aceptar(fechaActual, empleado, estadosCatalogo,
                    opciones.get(detalle.getDocumentacion().getId()));
        }
        remito.setEstado(estadoDestino);
    }
}
