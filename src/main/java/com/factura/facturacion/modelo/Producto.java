package com.factura.facturacion.modelo;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProducto")
	private Long idProducto;
	@Column(name = "Producto")
	private String producto;

	@OneToMany(mappedBy = "idProducto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Detalles> listDetalles;

	public Producto(Long idProducto) {
		this.idProducto = idProducto;
	}

}
