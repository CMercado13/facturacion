package com.factura.facturacion.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.factura.facturacion.modelo.dto.DetalleDTO;
import com.factura.facturacion.modelo.dto.FacturaDTO;
import com.factura.facturacion.modelo.dto.ProductoDTO;
import com.factura.facturacion.service.IFacturacionService;
import com.factura.facturacion.utilidades.JsonResponse;
import com.sun.istack.NotNull;

@RestController
@RequestMapping(value = "/facturacion")
public class FacturacionController {

	@Autowired
	private IFacturacionService facturacionService;

	@GetMapping(value = "/obtenerFacturas")
	public ResponseEntity<JsonResponse<FacturaDTO>> obtenerFacturas() {
		return facturacionService.obtenerFacturas();
	}

	@GetMapping(value = "/obtenerProductos")
	public ResponseEntity<JsonResponse<ProductoDTO>> obtenerProductos() {
		return facturacionService.obtenerProductos();
	}

	@GetMapping(value = "/obtenerFactura")
	public ResponseEntity<JsonResponse<FacturaDTO>> obtenerFacturaPorId(
			@NotNull @RequestParam(value = "idFactura", required = true) Long idFactura) {
		return facturacionService.obtenerFacturaPorId(idFactura);
	}

	@GetMapping(value = "/obtenerDetalle")
	public ResponseEntity<JsonResponse<DetalleDTO>> obtenerDetallePorIdFactura(
			@NotNull @RequestParam(value = "idFactura", required = true) Long idFactura) {
		return facturacionService.obtenerDetallesPorIdFactura(idFactura);
	}

	@PostMapping(value = "/procesarFactura")
	public ResponseEntity<JsonResponse<Void>> procesarFactura(@Valid @RequestBody FacturaDTO dto) {
		return facturacionService.procesarFactura(dto);
	}

	@DeleteMapping(value = "/eliminarFactura")
	public ResponseEntity<JsonResponse<Void>> eliminarFactura(
			@NotNull @RequestParam(value = "idFactura", required = true) Long idFactura) {
		return facturacionService.eliminarFactura(idFactura);
	}

	@DeleteMapping(value = "/eliminarDetalle")
	public ResponseEntity<JsonResponse<Void>> eliminarDetalle(
			@NotNull @RequestParam(value = "idDetalle", required = true) Long idDetalle) {
		return facturacionService.eliminarDetalle(idDetalle);
	}

}
