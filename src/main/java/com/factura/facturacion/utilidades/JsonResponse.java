package com.factura.facturacion.utilidades;

import java.util.Arrays;
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
public class JsonResponse<T> {

	@JsonProperty("status")
	private String estadoProceso;
	@JsonProperty("msg")
	private String mensaje;
	@JsonProperty("data")
	private List<T> data;

	public JsonResponse(String estadoProceso, String mensaje, T data) {
		this.estadoProceso = estadoProceso;
		this.mensaje = mensaje;
		this.data = Arrays.asList(data);
	}

}
