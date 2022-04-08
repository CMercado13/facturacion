package com.factura.facturacion.modelo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FacturaDTO {

	@JsonProperty(value = "id")
	private Long idFactura;
	@JsonProperty(value = "num_factura")
	private Long numeroFactura;
	// milisegundos
	@JsonProperty(value = "fecha")
	private Long fecha;
	@JsonProperty(value = "tipo_pago")
	private String tipoPago;
	@JsonProperty(value = "dni_cliente")
	private String documentoCliente;
	@JsonProperty(value = "nombre_cliente")
	private String nombreCliente;
	@JsonProperty(value = "descuento")
	private Integer descuento;
	@JsonProperty(value = "total")
	private Float total;
	@JsonProperty(value = "detalles")
	private List<DetalleDTO> detalles;

}
