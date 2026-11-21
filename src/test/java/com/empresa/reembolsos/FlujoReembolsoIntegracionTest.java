package com.empresa.reembolsos;

import com.empresa.reembolsos.aplicacion.SolicitudInvalidaException;
import com.empresa.reembolsos.aplicacion.SolicitudReembolsoService;
import com.empresa.reembolsos.dominio.CategoriaGasto;
import com.empresa.reembolsos.dominio.EstadoSolicitud;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlujoReembolsoIntegracionTest {

    @Autowired
    private SolicitudReembolsoService servicio;

    @Test
    void flujoCompleto_calculaElMontoPorCategoriaYCambiaDeEstado() {
        var fecha = LocalDate.of(2026, 3, 15);
        var viaje = servicio.crear(SolicitudesDePrueba.crear(
                "emp-flujo", CategoriaGasto.VIAJE, "120000", fecha, 2, true));
        var representacion = servicio.crear(SolicitudesDePrueba.crear(
                "emp-flujo", CategoriaGasto.REPRESENTACION, "500000", fecha, 0, true));
        var capacitacion = servicio.crear(SolicitudesDePrueba.crear(
                "emp-flujo", CategoriaGasto.CAPACITACION, "1000000", fecha, 0, true));

        assertEquals(0, new BigDecimal("120000").compareTo(viaje.getMontoAprobado()));
        assertEquals(0, new BigDecimal("500000").compareTo(representacion.getMontoAprobado()));
        assertEquals(0, new BigDecimal("800000").compareTo(capacitacion.getMontoAprobado()));
        assertEquals(EstadoSolicitud.PENDIENTE, viaje.getEstado());

        var aprobada = servicio.cambiarEstado(viaje.getId(), EstadoSolicitud.APROBADA);
        assertEquals(EstadoSolicitud.APROBADA, aprobada.getEstado());
    }

    @Test
    void solicitudSinDocumentos_seRechazaConMotivoClaro() {
        var solicitud = SolicitudesDePrueba.crear(
                "emp-sin-docs", CategoriaGasto.VIAJE, "100000", LocalDate.of(2026, 3, 15), 1, false);
        var ex = assertThrows(SolicitudInvalidaException.class, () -> servicio.crear(solicitud));
        assertEquals("Faltan documentos soporte", ex.getMessage());
    }

    @Test
    void segundaSolicitudDeLaMismaCategoriaYPeriodo_seRechaza() {
        var fecha = LocalDate.of(2026, 4, 10);
        servicio.crear(SolicitudesDePrueba.crear(
                "emp-duplicado", CategoriaGasto.CAPACITACION, "500000", fecha, 0, true));
        var segunda = SolicitudesDePrueba.crear(
                "emp-duplicado", CategoriaGasto.CAPACITACION, "300000", fecha.plusDays(5), 0, true);
        assertThrows(SolicitudInvalidaException.class, () -> servicio.crear(segunda));
    }
}
