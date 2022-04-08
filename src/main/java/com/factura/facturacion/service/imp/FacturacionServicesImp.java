package com.factura.facturacion.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.factura.facturacion.modelo.Detalles;
import com.factura.facturacion.modelo.Factura;
import com.factura.facturacion.modelo.dto.DetalleDTO;
import com.factura.facturacion.modelo.dto.FacturaDTO;
import com.factura.facturacion.modelo.dto.ProductoDTO;
import com.factura.facturacion.repo.IDetallesRepo;
import com.factura.facturacion.repo.IFacturaRepo;
import com.factura.facturacion.repo.IProductoRepo;
import com.factura.facturacion.service.IFacturacionService;
import com.factura.facturacion.utilidades.JsonResponse;
import com.factura.facturacion.utilidades.UtilConverter;
import com.factura.facturacion.utilidades.UtilKeys;
import com.factura.facturacion.utilidades.Utilidades;

@Service
public class FacturacionServicesImp implements IFacturacionService {

	@Autowired
	private IFacturaRepo facturaRepo;
	@Autowired
	private IProductoRepo productoRepo;
	@Autowired
	private IDetallesRepo detallesRepo;
	//
	@Autowired
	private UtilKeys uKeys;
	@Autowired
	private UtilConverter uConverter;
	@Autowired
	private Utilidades util;

	@Override
	public ResponseEntity<JsonResponse<FacturaDTO>> obtenerFacturas() {
		List<FacturaDTO> facturasDto = facturaRepo.findAll().stream().map(factura -> {
			return uConverter.convertirFacturaToFacturaDTO(factura);
		}).collect(Collectors.toList());
		return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_SUCESSFUL, uKeys.MSG_SUCESSFUL, facturasDto),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<JsonResponse<FacturaDTO>> obtenerFacturaPorId(Long idFactura) {
		Optional<Factura> obj = facturaRepo.findById(idFactura);
		if (!obj.isPresent()) {
			return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_ERROR, null), HttpStatus.OK);
		}
		FacturaDTO dto = uConverter.convertirFacturaToFacturaDTO(obj.get());
		dto.setDetalles(obj.get().getListDetalles().stream().map(detalle -> {
			return uConverter.convertirDetalleToDetalleDTO(detalle);
		}).collect(Collectors.toList()));
		return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_SUCESSFUL, uKeys.MSG_SUCESSFUL, dto),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<JsonResponse<DetalleDTO>> obtenerDetallesPorIdFactura(Long idFactura) {
		Optional<Factura> obj = facturaRepo.findById(idFactura);
		if (!obj.isPresent()) {
			return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_ERROR, null), HttpStatus.OK);
		}
		List<DetalleDTO> detallesDTO = obj.get().getListDetalles().stream().map(detalle -> {
			return uConverter.convertirDetalleToDetalleDTO(detalle);
		}).collect(Collectors.toList());
		return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_SUCESSFUL, uKeys.MSG_SUCESSFUL, detallesDTO),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<JsonResponse<Void>> procesarFactura(FacturaDTO dto) {
		if (util.dateMayorNow(dto.getFecha())) {
			return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_FECHA_MENOR_HOY, null),
					HttpStatus.OK);
		}
		if (dto.getDetalles() == null) {
			return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_DETALLES_REQUERIDO, null),
					HttpStatus.OK);
		}
		if (dto.getDetalles().isEmpty()) {
			return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_DETALLES_REQUERIDO, null),
					HttpStatus.OK);
		}
		Supplier<Stream<DetalleDTO>> supplier = () -> dto.getDetalles().stream();
		// Validar si hay 1 producto agregado como mínimo
		List<ProductoDTO> productos = supplier.get().map(detalleDto -> {
			return detalleDto.getProducto();
		}).collect(Collectors.toList());
		if (productos.isEmpty()) {
			return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_PRODUCTOS_REQUERIDO, null),
					HttpStatus.OK);
		}
		// validar que todos los precios suministrados sean mayores que 0
		Predicate<DetalleDTO> predicatePrecioMenorCero = det -> det.getPrecioUnitario() != null
				&& det.getPrecioUnitario().doubleValue() < uKeys.FLOAT_0.doubleValue();
		boolean preciosMenorCero = supplier.get().anyMatch(predicatePrecioMenorCero);
		if (preciosMenorCero) {
			return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_PRECIOS_NEGATIVOS, null),
					HttpStatus.OK);
		}
		Predicate<DetalleDTO> predicateCantidadMenorIgualCero = det -> det.getCantidadProducto() <= uKeys.LONG_0
				.intValue();
		boolean cantidadMenorIgualCero = supplier.get().anyMatch(predicateCantidadMenorIgualCero);
		if (cantidadMenorIgualCero) {
			return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_ERROR, uKeys.MSG_CANTIDAD_MAYOR_CERO, null),
					HttpStatus.OK);
		}
		boolean creando = uConverter.isLongNullOrCero(dto.getIdFactura());
		Factura factura = new Factura();
		if (!creando) {
			if (!facturaRepo.existsById(dto.getIdFactura())) {
				factura.setNumeroFactura(((facturaRepo.consultarMaxId() + 1) * 1000));
			}
			factura.setIdFactura(dto.getIdFactura());
		} else {
			factura.setNumeroFactura(((facturaRepo.consultarMaxId() + 1) * 1000));
		}
		factura.setDescuento(dto.getDescuento());
		factura.setDocumentoCliente(dto.getDocumentoCliente());
		factura.setFecha(new Date(dto.getFecha()));
		factura.setIva(uKeys.FLOAT_IVA);
		factura.setNombreCliente(dto.getNombreCliente());
		factura.setTipoPago(dto.getTipoPago());
		double subTotal = supplier.get().mapToDouble(detalle -> {
			return detalle.getPrecioUnitario().doubleValue() * detalle.getCantidadProducto();
		}).sum();
		float totalDescuento = (float) (subTotal * (dto.getDescuento() / uKeys.DOUBLE_100));
		float totalImpuesto = (float) ((subTotal - totalDescuento) * uKeys.FLOAT_IVA.doubleValue());
		float total = (float) (subTotal - totalDescuento + totalImpuesto);
		factura.setSubTotal((float) subTotal);
		factura.setTotalDescuento(totalDescuento);
		factura.setTotalImpuesto(totalImpuesto);
		factura.setTotal(total);
		List<Detalles> detalles = supplier.get().map(detalleDto -> {
			Detalles detalle = new Detalles();
			detalle.setIdDetalle(detalleDto.getIdDetalle());
			detalle.setCantidad(detalleDto.getCantidadProducto());
			detalle.setIdProducto(detalleDto.getProducto().obtenerProducto());
			detalle.setPrecioUnitario(detalleDto.getPrecioUnitario());
			detalle.setIdFactura(factura);
			return detalle;
		}).collect(Collectors.toList());
		factura.setListDetalles(detalles);
		facturaRepo.save(factura);
		return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_SUCESSFUL, uKeys.MSG_SUCESSFUL, null),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<JsonResponse<Void>> eliminarFactura(Long idFactura) {
		facturaRepo.deleteById(idFactura);
		return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_SUCESSFUL, uKeys.MSG_SUCESSFUL, null),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<JsonResponse<ProductoDTO>> obtenerProductos() {
		List<ProductoDTO> listProductos = productoRepo.findAll().stream().map(producto -> {
			return uConverter.convertirProductoToProductoDTO(producto);
		}).collect(Collectors.toList());
		return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_SUCESSFUL, uKeys.MSG_SUCESSFUL, listProductos),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<JsonResponse<Void>> eliminarDetalle(Long idDetalle) {
		detallesRepo.deleteById(idDetalle);
		return new ResponseEntity<>(new JsonResponse<>(uKeys.STATUS_SUCESSFUL, uKeys.MSG_SUCESSFUL, null),
				HttpStatus.OK);
	}

}
