/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.factura.facturacion.utilidades;

import org.springframework.stereotype.Component;

import com.factura.facturacion.modelo.Detalles;
import com.factura.facturacion.modelo.Factura;
import com.factura.facturacion.modelo.Producto;
import com.factura.facturacion.modelo.dto.DetalleDTO;
import com.factura.facturacion.modelo.dto.FacturaDTO;
import com.factura.facturacion.modelo.dto.ProductoDTO;

/**
 *
 * @author cesar
 */
@Component
public class UtilConverter {

	/**
	 * Permite validar si un String no es nulo y no está vacio
	 *
	 * @param s String
	 * @return boolean, true si el String es nulo y está vacio
	 *
	 */
	public boolean isStringNullOrEmpty(String s) {
		if (s == null) {
			return true;
		}
		if (s.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean isLongNullOrCero(Long l) {
		if (l == null) {
			return true;
		}
		if (l.intValue() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * permite convertir una hora String en formato hh:mm:ss en int seguntos.
	 *
	 * @param t String HH:mm:ss
	 * @return int , equivalente en segundos a la hora suministrada, 0 si la hora no
	 *         es correcta
	 *
	 */
	public int toSecs(String t) {
		try {
			String[] li = t.split(":");
			int timeInS = Integer.parseInt(li[0]) * 60 * 60 + Integer.parseInt(li[1]) * 60;
			if (li.length > 2) {
				timeInS += Integer.parseInt(li[2]);
			}
			return timeInS;
		} catch (Exception e) {
			return 0;
		}
	}

	public String stringNullToEmpty(String cadena) {
		if (cadena == null) {
			return "";
		}
		return cadena;
	}

	public FacturaDTO convertirFacturaToFacturaDTO(Factura factura) {
		FacturaDTO dto = new FacturaDTO();
		dto.setIdFactura(factura.getIdFactura());
		dto.setDocumentoCliente(factura.getDocumentoCliente());
		dto.setFecha(factura.getFecha().getTime());
		dto.setNombreCliente(factura.getNombreCliente());
		dto.setNumeroFactura(factura.getNumeroFactura());
		dto.setTipoPago(factura.getTipoPago());
		dto.setDescuento(factura.getDescuento());
		dto.setTotal(factura.getTotal());
		return dto;
	}

	public DetalleDTO convertirDetalleToDetalleDTO(Detalles detalle) {
		DetalleDTO dto = new DetalleDTO();
		dto.setIdDetalle(detalle.getIdDetalle());
		dto.setCantidadProducto(dto.getCantidadProducto());
		dto.setCantidadProducto(detalle.getCantidad());
		dto.setProducto(convertirProductoToProductoDTO(detalle.getIdProducto()));
		dto.setPrecioUnitario(detalle.getPrecioUnitario());
		return dto;
	}

	public ProductoDTO convertirProductoToProductoDTO(Producto producto) {
		return new ProductoDTO(producto.getIdProducto(), producto.getProducto());
	}

}
