package com.factura.facturacion.utilidades;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UtilKeys {

	// status request
	public final String STATUS_SUCESSFUL = "OK";
	public final String STATUS_ERROR = "ERROR";

	// msg status request
	public final String MSG_SUCESSFUL = "Proceso realizado con éxito";
	public final String MSG_ERROR = "Se ha generado un error";

	// lista vacia para respuesta de errors
	public final List<Object> LIST_EMPTY = new ArrayList<>();

	// ESPECIAL NAME
	public final Long LONG_0 = new Long(0);
	public final Float FLOAT_0 = new Float(0);
	public final Float FLOAT_IVA = new Float(0.19);
	public final double DOUBLE_100 = 100;

	// MSG FACTURA
	public final String MSG_CANTIDAD_MAYOR_CERO = "La cantidad mínima de producto es 1";
	public final String MSG_PRECIOS_NEGATIVOS = "Se han suministrado precios con valores negativos";
	public final String MSG_PRODUCTOS_REQUERIDO = "Factura debe contar con un producto seleccionado como mínimo";
	public final String MSG_DETALLES_REQUERIDO = "Factura debe contar con detalles";
	public final String MSG_FECHA_MENOR_HOY = "Fecha no debe ser menor al día de hoy";
}
