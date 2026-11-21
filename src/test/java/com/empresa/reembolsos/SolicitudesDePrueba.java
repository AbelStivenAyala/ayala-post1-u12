package com.empresa.reembolsos;

import com.empresa.reembolsos.dominio.CategoriaGasto;
import com.empresa.reembolsos.dominio.SolicitudReembolso;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class SolicitudesDePrueba {

    private SolicitudesDePrueba() { }

    public static SolicitudReembolso crear(String empleadoId, CategoriaGasto categoria, String monto,
                                           LocalDate fecha, int diasViaje, boolean documentos) {
        SolicitudReembolso s = new SolicitudReembolso();
        s.setEmpleadoId(empleadoId);
        s.setCategoria(categoria);
        s.setMontoSolicitado(new BigDecimal(monto));
        s.setFechaGasto(fecha);
        s.setDiasViaje(diasViaje);
        s.setDocumentosSoporte(documentos);
        return s;
    }
}
