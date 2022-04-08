package com.factura.facturacion.modelo.dto;

import com.factura.facturacion.modelo.Producto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {

	@JsonProperty(value = "id")
	private Long idProducto;
	@JsonProperty(value = "producto")
	private String producto;

	public Producto obtenerProducto() {
		return new Producto(this.idProducto);
	}

}
