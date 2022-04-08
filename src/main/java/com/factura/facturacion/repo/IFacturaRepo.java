package com.factura.facturacion.repo;

import org.springframework.data.jpa.repository.Query;

import com.factura.facturacion.modelo.Factura;

public interface IFacturaRepo extends IGenericRepo<Factura, Long> {

	@Query(value = "SELECT  "
			+ "    COALESCE(MAX(id_factura), 1) "
			+ "FROM "
			+ "    factura", nativeQuery = true)
	long consultarMaxId();

}
