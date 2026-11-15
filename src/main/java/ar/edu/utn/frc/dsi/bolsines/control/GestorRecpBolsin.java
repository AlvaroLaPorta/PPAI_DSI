package ar.edu.utn.frc.dsi.bolsines.control;

import ar.edu.utn.frc.dsi.bolsines.domain.*;
import ar.edu.utn.frc.dsi.bolsines.domain.state.Estado;
import ar.edu.utn.frc.dsi.bolsines.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GestorRecpBolsin {

    private final SesionRepository sesionRepository;
    private final EmpleadoRepository empleadoRepository;
    private final BolsinRepository bolsinRepository;
    private final EstadoRepository estadoRepository;

    private Usuario usuario;
    private Empleado empleadoLogueado;
    private ComisionMedica cmDestino;
    private String nombreCM;
    private Bolsin bolsinSeleccionado;
    private String opcionRecepcionSeleccionada;
    private LocalDateTime fechaHoraActual;
    private List<Estado> estados;

    public GestorRecpBolsin(SesionRepository sesionRepository,
                            EmpleadoRepository empleadoRepository,
                            BolsinRepository bolsinRepository,
                            EstadoRepository estadoRepository) {
        this.sesionRepository = sesionRepository;
        this.empleadoRepository = empleadoRepository;
        this.bolsinRepository = bolsinRepository;
        this.estadoRepository = estadoRepository;
    }

    public Map<String, Object> selOpcRegRecpBolsin() {
        buscarCM();
        List<Map<String, Object>> bolsinesPendientes = buscarBolsines();

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("empleado", empleadoLogueado.getNombreCompleto());
        respuesta.put("nombreCM", nombreCM);
        respuesta.put("esCMC", cmDestino.isEsCMC());
        respuesta.put("bolsines", bolsinesPendientes);
        return respuesta;
    }

    public void buscarCM() {
        Sesion sesionActual = sesionRepository.findAll().stream()
                .filter(Sesion::esActiva)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No hay una sesión activa en el sistema"));

        this.usuario = sesionActual.getUsuario();
        this.empleadoLogueado = empleadoRepository.findAll().stream()
                .filter(e -> e.esTuUsuario(this.usuario))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró empleado asociado al usuario"));

        this.cmDestino = empleadoLogueado.obtenerCM();
        this.nombreCM = cmDestino.getNombre();
    }

    public List<Map<String, Object>> buscarBolsines() {
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Bolsin b : bolsinRepository.findAll()) {
            if (b.esTuCmDestino(this.cmDestino) && b.sosEstadoEnviado()) {
                resultado.add(buscarDatosBolsin(b));
            }
        }
        return resultado;
    }

    public Map<String, Object> buscarDatosBolsin(Bolsin b) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", b.getId());
        dto.put("numeroBolsin", b.getNumeroBolsin());
        dto.put("numeroPrecinto", b.getNumeroPrecinto());
        dto.put("fecha", b.getFecha().toString());
        dto.put("peso", b.getPeso());
        dto.put("cmOrigen", b.getCMOrigen().getNombre());
        dto.put("estadoActual", b.getEstadoActual().getNombre());
        return dto;
    }

    public Map<String, Object> tomarSeleccionBolsin(Long bolsinId) {
        this.bolsinSeleccionado = bolsinRepository.findById(bolsinId)
                .orElseThrow(() -> new IllegalArgumentException("Bolsín no encontrado: " + bolsinId));

        return mostrarDatosRemitoYDoc();
    }

    public Map<String, Object> mostrarDatosRemitoYDoc() {
        List<Map<String, Object>> remitosDto = new ArrayList<>();
        for (Remito r : bolsinSeleccionado.getRemitos()) {
            List<Map<String, Object>> docsDto = new ArrayList<>();
            for (DetalleRemito det : r.getDetalleRemito()) {
                Documentacion doc = det.getDocumentacion();
                Map<String, Object> docMap = new LinkedHashMap<>();
                docMap.put("numero", doc.getNumero());
                docMap.put("asunto", doc.getAsunto());
                docMap.put("fechaPase", doc.getFechaPase().toString());
                docMap.put("tipoDocumentacion", doc.getTipoDoc().getNombre());
                docMap.put("areaCMCDestino", det.getAreaCMCDestino());
                docMap.put("estadoActual", doc.getEstadoActual().getNombre());
                docsDto.add(docMap);
            }
            Map<String, Object> remitoMap = new LinkedHashMap<>();
            remitoMap.put("numero", r.getNumero());
            remitoMap.put("fecha", r.getFecha().toString());
            remitoMap.put("estadoActual", r.getEstado().getNombre());
            remitoMap.put("documentaciones", docsDto);
            remitosDto.add(remitoMap);
        }

        List<Map<String, String>> opcionesRecepcion = new ArrayList<>();
        opcionesRecepcion.add(Map.of("codigo", "ACEPTAR", "etiqueta", "Aceptar recepción (Documentación completa y corresponde a CM)"));
        opcionesRecepcion.add(Map.of("codigo", "DENEGAR", "etiqueta", "Denegar recepción (Documentación incompleta → NoRecibida)"));
        opcionesRecepcion.add(Map.of("codigo", "RECHAZAR", "etiqueta", "Rechazar recepción (No corresponde a CM destino → RecibidaYRechazada)"));
        if (cmDestino != null && cmDestino.isEsCMC()) {
            opcionesRecepcion.add(Map.of("codigo", "REDIRIGIR", "etiqueta", "Redirigir documentación (CM destino es CMC → ParaRedirigir)"));
        }

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("bolsin", buscarDatosBolsin(bolsinSeleccionado));
        respuesta.put("remitos", remitosDto);
        respuesta.put("opcionesRecepcion", opcionesRecepcion);
        return respuesta;
    }

    public Map<String, Object> tomarOpcRecepcion(String opcionRecepcion) {
        this.opcionRecepcionSeleccionada = opcionRecepcion;
        return Map.of(
                "bolsinNumero", bolsinSeleccionado.getNumeroBolsin(),
                "precinto", bolsinSeleccionado.getNumeroPrecinto(),
                "opcionSeleccionada", opcionRecepcion,
                "mensajeConfirmacion", "¿Confirma el registro de recepción del bolsín N° "
                        + bolsinSeleccionado.getNumeroBolsin() + " con la acción: " + opcionRecepcion + "?"
        );
    }

    @Transactional
    public Map<String, Object> tomarConfirmacion() {
        if (bolsinSeleccionado == null || opcionRecepcionSeleccionada == null) {
            throw new IllegalStateException("Debe seleccionar un bolsín y una opción de recepción antes de confirmar.");
        }

        Bolsin bolsinManaged = bolsinRepository.findById(bolsinSeleccionado.getId()).orElseThrow();

        obtenerFechaYHoraActual();
        buscarEstados();
        actualizarEstados(bolsinManaged);

        bolsinRepository.save(bolsinManaged);

        boolean cu29Invocado = llamarCU29();
        Map<String, Object> resultado = construirResumenEjecucion(bolsinManaged, cu29Invocado);
        finCU();
        return resultado;
    }

    public void obtenerFechaYHoraActual() {
        this.fechaHoraActual = LocalDateTime.now();
    }

    public void buscarEstados() {
        this.estados = estadoRepository.findAll();
    }

    public void actualizarEstados(Bolsin bolsinManaged) {
        bolsinManaged.recibirYAceptarBolsin(
                this.fechaHoraActual,
                this.empleadoLogueado,
                this.estados,
                this.opcionRecepcionSeleccionada
        );
    }

    public boolean llamarCU29() {
        return true;
    }

    public void finCU() {
        this.bolsinSeleccionado = null;
        this.opcionRecepcionSeleccionada = null;
    }

    private Map<String, Object> construirResumenEjecucion(Bolsin b, boolean cu29Invocado) {
        List<Map<String, Object>> historialBolsin = b.getCambioEstadoBolsin().stream().map(ce -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("estado", ce.getEstado().getNombre());
            m.put("inicio", ce.getFechaHoraInicio().toString());
            m.put("fin", ce.getFechaHoraFin() != null ? ce.getFechaHoraFin().toString() : "ACTUAL");
            return m;
        }).toList();

        List<Map<String, Object>> estadosDocs = new ArrayList<>();
        for (Remito r : b.getRemitos()) {
            for (DetalleRemito det : r.getDetalleRemito()) {
                Documentacion d = det.getDocumentacion();
                estadosDocs.add(Map.of(
                        "numeroDoc", d.getNumero(),
                        "asunto", d.getAsunto(),
                        "nuevoEstado", d.getEstadoActual().getNombre(),
                        "remitoEstado", r.getEstado().getNombre()
                ));
            }
        }

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("mensaje", "Recepción del bolsín N° " + b.getNumeroBolsin() + " registrada y persistida correctamente.");
        res.put("fechaHoraRegistro", fechaHoraActual.toString());
        res.put("nuevoEstadoBolsin", b.getEstadoActual().getNombre());
        res.put("historialEstadosBolsin", historialBolsin);
        res.put("documentacionesActualizadas", estadosDocs);
        res.put("cu29Llamado", cu29Invocado);
        return res;
    }
}
