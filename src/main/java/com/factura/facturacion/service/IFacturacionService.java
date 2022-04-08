package com.factura.facturacion.service;

import org.springframework.http.ResponseEntity;

import com.factura.facturacion.modelo.dto.DetalleDTO;
import com.factura.facturacion.modelo.dto.FacturaDTO;
import com.factura.facturacion.modelo.dto.ProductoDTO;
import com.factura.facturacion.utilidades.JsonResponse;

public interface IFacturacionService {

	ResponseEntity<JsonResponse<FacturaDTO>> obtenerFacturas();

	ResponseEntity<JsonResponse<FacturaDTO>> obtenerFacturaPorId(Long idFactura);

	ResponseEntity<JsonResponse<DetalleDTO>> obtenerDetallesPorIdFactura(Long idFactura);

	ResponseEntity<JsonResponse<Void>> procesarFactura(FacturaDTO dto);

	ResponseEntity<JsonResponse<Void>> eliminarFactura(Long idFactura);

	ResponseEntity<JsonResponse<Void>> eliminarDetalle(Long idDetalle);

	ResponseEntity<JsonResponse<ProductoDTO>> obtenerProductos();

}
