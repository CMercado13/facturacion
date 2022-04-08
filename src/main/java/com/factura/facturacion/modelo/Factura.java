package com.factura.facturacion.modelo;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factura {

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFactura")
	private Long idFactura;
	
	@Column(name = "NumeroFactura")
	private Long numeroFactura;
	
	@Column(name = "Fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Size(min = 3, message = "Tipo pago debe tener mas de 2 caracteres")
	@NotNull(message = "Tipo pago no puede ser null")
	@NotEmpty(message = "Tipo pago no puede ser vacio")
	@NotBlank(message = "Tipo pago no puede contener solo espacios")
	@Column(name = "TipoPago")
	private String tipoPago;
	
	@Size(min = 3, message = "Número de documento debe tener mas de 2 caracteres")
	@NotNull(message = "Número de documento no puede ser null")
	@NotEmpty(message = "Número de documento no puede ser vacio")
	@NotBlank(message = "Número de documento no puede contener solo espacios")
	@Column(name = "DocumentoCliente")
	private String documentoCliente;
	
	@Size(min = 3, message = "Nombre cliente debe tener mas de 2 caracteres")
	@NotNull(message = "Nombre cliente no puede ser null")
	@NotEmpty(message = "Nombre cliente no puede ser vacio")
	@NotBlank(message = "Nombre cliente no puede contener solo espacios")
	@Column(name = "NombreCliente")
	private String nombreCliente;
	
	@Column(name = "SubTotal")
	private Float subTotal;
	
	@Column(name = "Descuento")
	private Integer descuento;
	
	@Column(name = "Iva")
	private Float iva;
	
	@Column(name = "TotalDescuento")
	private Float totalDescuento;
	
	@Column(name = "TotalImpuesto")
	private Float totalImpuesto;
	
	@Column(name = "Total")
	private Float total;

	@OneToMany(mappedBy = "idFactura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Detalles> listDetalles;

}
