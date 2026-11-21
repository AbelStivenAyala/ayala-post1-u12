package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.SolicitudesDePrueba;
import com.empresa.reembolsos.dominio.CategoriaGasto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorDuplicadosTest {

    private final ValidadorDuplicados validador = new ValidadorDuplicados();

    @Test
    void mismaCategoriaYMismoPeriodo_rechaza() {
        var pendiente = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.CAPACITACION, "500000",
                LocalDate.of(2026, 3, 5), 0, true);
        var nueva = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.CAPACITACION, "300000",
                LocalDate.of(2026, 3, 20), 0, true);
        assertFalse(validador.validar(nueva, List.of(pendiente)).valida());
    }

    @Test
    void otraCategoria_esValida() {
        var pendiente = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.CAPACITACION, "500000",
                LocalDate.of(2026, 3, 5), 0, true);
        var nueva = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.REPRESENTACION, "300000",
                LocalDate.of(2026, 3, 20), 0, true);
        assertTrue(validador.validar(nueva, List.of(pendiente)).valida());
    }

    @Test
    void otroPeriodo_esValida() {
        var pendiente = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.CAPACITACION, "500000",
                LocalDate.of(2026, 2, 5), 0, true);
        var nueva = SolicitudesDePrueba.crear("emp-1", CategoriaGasto.CAPACITACION, "300000",
                LocalDate.of(2026, 3, 20), 0, true);
        assertTrue(validador.validar(nueva, List.of(pendiente)).valida());
    }
}
