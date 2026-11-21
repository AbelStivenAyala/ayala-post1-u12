package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.SolicitudesDePrueba;
import com.empresa.reembolsos.dominio.CategoriaGasto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorMontoMaximoTest {

    private final ValidadorMontoMaximo validador = new ValidadorMontoMaximo();

    @Test
    void viajeQueExcedeElTope_rechaza() {
        var solicitud = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.VIAJE, "200000",
                LocalDate.of(2026, 3, 10), 2, true);
        var resultado = validador.validar(solicitud, List.of());
        assertFalse(resultado.valida());
        assertEquals("Monto excede el tope de la categoria VIAJE", resultado.motivoRechazo());
    }

    @Test
    void representacionDentroDelTope_esValida() {
        var solicitud = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.REPRESENTACION, "1500000",
                LocalDate.of(2026, 3, 10), 0, true);
        assertTrue(validador.validar(solicitud, List.of()).valida());
    }
}
