package com.factura.facturacion.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Detalles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detalles {

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDetalle")
	private Long idDetalle;
	@Column(name = "Cantidad")
	private Integer cantidad;
	@Column(name = "PrecioUnitario")
	private Float precioUnitario;

	@JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
	@ManyToOne(fetch = FetchType.LAZY)
	private Producto idProducto;

	@JoinColumn(name = "idFactura", referencedColumnName = "idFactura")
	@ManyToOne(fetch = FetchType.LAZY)
	private Factura idFactura;

}
