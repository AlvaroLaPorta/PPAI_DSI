package ar.edu.utn.frc.dsi.bolsines.domain.state.bolsin;

import ar.edu.utn.frc.dsi.bolsines.domain.*;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("BOLSIN_ENVIADO")
public class Enviado extends Estado {

    public Enviado() {
        super("Enviado", "Bolsín despachado hacia la Comisión Médica destino", "Bolsin");
    }

    @Override
    public boolean esEnviado() {
        return true;
    }

    @Override
    public void recibirYAceptarBolsin(Bolsin bolsin, LocalDateTime fechaActual, Empleado empleado,
                                      List<Estado> estadosCatalogo, java.util.Map<Long, OpcionRecepcion> opciones) {
        Estado estadoDestino = estadosCatalogo.stream()
                .filter(e -> e.esAmbitoBolsin() && e.esRecibido())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró el estado RecibidoEnCMDestino"));

        bolsin.cambiarEstadoBolsin(fechaActual, estadoDestino, empleado);

        for (Remito remito : bolsin.getRemitos()) {
            remito.recibirYAceptar(empleado, estadosCatalogo, fechaActual, opciones);
        }
    }
}
