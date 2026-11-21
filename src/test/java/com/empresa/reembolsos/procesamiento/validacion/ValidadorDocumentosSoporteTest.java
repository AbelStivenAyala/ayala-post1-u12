package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.SolicitudesDePrueba;
import com.empresa.reembolsos.dominio.CategoriaGasto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorDocumentosSoporteTest {

    private final ValidadorDocumentosSoporte validador = new ValidadorDocumentosSoporte();

    @Test
    void conDocumentos_esValida() {
        var solicitud = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.VIAJE, "100000",
                LocalDate.of(2026, 3, 10), 1, true);
        assertTrue(validador.validar(solicitud, List.of()).valida());
    }

    @Test
    void sinDocumentos_rechazaConMensajeClaro() {
        var solicitud = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.VIAJE, "100000",
                LocalDate.of(2026, 3, 10), 1, false);
        var resultado = validador.validar(solicitud, List.of());
        assertFalse(resultado.valida());
        assertEquals("Faltan documentos soporte", resultado.motivoRechazo());
    }
}
