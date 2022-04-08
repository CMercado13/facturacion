package com.factura.facturacion.modelo.dto;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleDTO {

	@JsonProperty(value = "id")
	private Long idDetalle;
	@JsonProperty(value = "producto")
	private ProductoDTO producto;
	@JsonProperty(value = "cantidad_producto")
	@Min(value = 1, message = "La cantidad mínima para un producto es 1")
	private int cantidadProducto;
	@JsonProperty(value = "precio_unitario")
	private Float precioUnitario;

}
