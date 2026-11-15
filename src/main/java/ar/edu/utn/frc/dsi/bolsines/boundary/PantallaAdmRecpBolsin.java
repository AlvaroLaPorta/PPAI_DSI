package ar.edu.utn.frc.dsi.bolsines.boundary;

import ar.edu.utn.frc.dsi.bolsines.config.DataSeeder;
import ar.edu.utn.frc.dsi.bolsines.control.GestorRecpBolsin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cu28")
public class PantallaAdmRecpBolsin {

    private final GestorRecpBolsin gestorRecpBolsin;
    private final DataSeeder dataSeeder;

    public PantallaAdmRecpBolsin(GestorRecpBolsin gestorRecpBolsin, DataSeeder dataSeeder) {
        this.gestorRecpBolsin = gestorRecpBolsin;
        this.dataSeeder = dataSeeder;
    }

    @GetMapping("/iniciar")
    public ResponseEntity<?> selOpcRegRecpBolsin() {
        return ResponseEntity.ok(gestorRecpBolsin.selOpcRegRecpBolsin());
    }

    @PostMapping("/seleccionar-bolsin/{id}")
    public ResponseEntity<?> tomarSeleccionBolsin(@PathVariable Long id) {
        return ResponseEntity.ok(gestorRecpBolsin.tomarSeleccionBolsin(id));
    }

    @PostMapping("/seleccionar-opcion")
    public ResponseEntity<?> tomarOpcRecepcion(@RequestBody Map<String, String> body) {
        String opcion = body.get("opcion");
        return ResponseEntity.ok(gestorRecpBolsin.tomarOpcRecepcion(opcion));
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> tomarConfirmacion() {
        try {
            return ResponseEntity.ok(gestorRecpBolsin.tomarConfirmacion());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reset-demo")
    public ResponseEntity<?> resetDemo() {
        dataSeeder.cargarDatosIniciales();
        return ResponseEntity.ok(Map.of("mensaje", "Base de datos SQLite reiniciada con datos de prueba."));
    }
}
